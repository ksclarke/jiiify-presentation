
package info.freelibrary.iiif.presentation.v3.utils.csv;

import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.Collection;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.Resource;
import info.freelibrary.iiif.presentation.v3.ResourceTypes;
import info.freelibrary.iiif.presentation.v3.content.ContentResource;
import info.freelibrary.iiif.presentation.v3.content.ImageContent;
import info.freelibrary.iiif.presentation.v3.content.VideoContent;
import info.freelibrary.iiif.presentation.v3.id.Minter;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.MediaType;
import info.freelibrary.iiif.presentation.v3.properties.ViewingDirection;
import info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior;
import info.freelibrary.iiif.presentation.v3.services.ImageService;
import info.freelibrary.iiif.presentation.v3.services.ImageService2;
import info.freelibrary.iiif.presentation.v3.utils.MessageCodes;
import info.freelibrary.util.Constants;
import info.freelibrary.util.Env;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.ThrowingFunction;
import info.freelibrary.util.warnings.JDK;
import info.freelibrary.util.warnings.PMD;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/** A IIIF builder for CSV-based deserialization. */
public class Builder {

    /**
     * Builds a resource based on the information provided in the given row and minter.
     *
     * @param <R> The type of resource being built, extending Resource&lt;R&gt;
     * @param aRow The row containing data to build the resource
     * @param aMinter The minter used for ID generation or resource creation
     * @return A resource of type R built using the provided row and minter
     * @throws MappingException If an error occurs while mapping row data to a resource
     */
    @SuppressWarnings({ JDK.UNCHECKED, PMD.CYCLOMATIC_COMPLEXITY })
    public <R extends Resource<R>> R build(final Row aRow, final Minter aMinter) throws MappingException {
        final String id = aRow.getItemID().orElseThrow(() -> new MappingException(MessageCodes.JPA_002));
        final Label label = new Label(aRow.getTitle().orElseThrow(() -> new MappingException(MessageCodes.JPA_003)));
        final String objType = aRow.getObjectType().orElseThrow(() -> new MappingException(MessageCodes.JPA_160));

        return switch (objType) {
            case Keys.COLLECTION -> {
                final String validID = checkID(id, ResourceTypes.COLLECTION);
                final Collection collection = new Collection(validID, label);

                aRow.getBehavior().flatMap(ManifestBehavior::fromLabel).ifPresent(collection::setBehaviors);

                aRow.getViewingDirection().flatMap(ViewingDirection::fromLabel)
                        .ifPresent(collection::setViewingDirection);

                yield (R) addProperties(collection, aRow);
            }
            case Keys.WORK -> {
                final String validID = checkID(id, ResourceTypes.MANIFEST);
                final Manifest manifest = new Manifest(validID, label);

                aRow.getBehavior().flatMap(ManifestBehavior::fromLabel).ifPresent(manifest::setBehaviors);

                aRow.getViewingDirection().flatMap(ViewingDirection::fromLabel)
                        .ifPresent(manifest::setViewingDirection);

                yield (R) addProperties(manifest, aRow);
            }
            case Keys.PAGE -> {
                final Canvas canvas = new Canvas(aMinter, label);

                final int width = aRow.getMediaWidth().orElse(0);
                final int height = aRow.getMediaHeight().orElse(0);

                if (width > 0 && height > 0) {
                    canvas.setWidthHeight(width, height);
                }

                yield (R) addProperties(canvas, aRow);
            }
            case Keys.CHOICE, Keys.LAYER -> {
                final ImageContent imageContent = new ImageContent(checkID(id, ResourceTypes.IMAGE));
                final ImageService imageService = new ImageService2(checkID(id, ResourceTypes.IMAGE_SERVICE_2));
                final int width = aRow.getMediaWidth().orElse(0);
                final int height = aRow.getMediaHeight().orElse(0);

                if (width > 0 && height > 0) {
                    imageContent.setWidthHeight(width, height);
                }

                imageContent.setServices(imageService);

                yield (R) addProperties(imageContent, aRow);
            }
            default -> null;
        };
    }

    /**
     * Builds a resource based on the given row.
     *
     * @param <R> The type of resource being built, extending Resource&lt;R&gt;
     * @param aRow The row data used to build the resource
     * @return The resource of type R built using the provided row
     * @throws MappingException if there is an error during the resource mapping
     */
    public <R extends Resource<R>> R build(final Row aRow) throws MappingException {
        return build(aRow, null);
    }

    /**
     * Checks and updates the provided ID string based on specific criteria. If the given ID does not start with
     * "https://", a default host URL is added to the ID. Additionally, if the ID contains a slash, it is URL-encoded
     * before appending it to the host. If the ID already starts with "https://", it is returned as-is.
     *
     * @param aID The ID string to be checked and optionally modified
     * @param aResourceType The type of resource being built
     * @return The modified or original ID string, depending on the logic
     * @throws MappingException If an error occurs during processing
     */
    public String checkID(final String aID, final String aResourceType) throws MappingException {
        // If the ID starts with a "https://", we assume it's already formatted correctly; else, we do it
        if (!aID.startsWith("https://")) {
            final String host = Env.get(Configs.JPV3_HOST, "https://localhost:9999");
            final String urlTemplate;

            switch (aResourceType) {
                case ResourceTypes.COLLECTION:
                    urlTemplate = Env.get(Configs.JPV3_COLLECTION_PATH, "{}/collections/{}");
                    return StringUtils.format(urlTemplate, host, URLEncoder.encode(aID, StandardCharsets.UTF_8));
                case ResourceTypes.MANIFEST:
                    urlTemplate = Env.get(Configs.JPV3_MANIFEST_PATH, "{}/{}/manifest");
                    return StringUtils.format(urlTemplate, host, URLEncoder.encode(aID, StandardCharsets.UTF_8));
                case ResourceTypes.CANVAS, ResourceTypes.IMAGE_SERVICE_2, ResourceTypes.IMAGE_SERVICE_3:
                    return host + Constants.SLASH + URLEncoder.encode(aID, StandardCharsets.UTF_8);
                case ResourceTypes.IMAGE:
                    urlTemplate = Env.get(Configs.JPV3_IMAGE_PATTERN, "{}/{}/full/600,/0/default.jpg");
                    return StringUtils.format(urlTemplate, host, URLEncoder.encode(aID, StandardCharsets.UTF_8));
                default:
                    break;
            }

            if (aID.contains(Constants.SLASH)) {
                return host + URLEncoder.encode(aID, StandardCharsets.UTF_8);
            }

            return host + aID;
        }

        return aID;
    }

    /**
     * Adds resource properties to the supplied resource.
     *
     * @param aResource A resource
     * @param aRow A CSV row with the resource's properties
     * @param <R> The type of resource being built
     * @return The modified resource
     * @throws MappingException If an error occurs while mapping row data to a resource
     */
    private <R extends Resource<R>> R addProperties(final R aResource, final Row aRow) throws MappingException {
        // Add a thumbnail, using ImageContent as the default thumbnail type
        if (aRow.getObjectType().filter(Keys.LAYER::equals).isEmpty()) {
            aResource.getType()
                    .flatMap(type -> aRow.getThumbnail().or(aRow::getItemID)
                            .map(ThrowingFunction.sneaky(id -> checkID(id, type))).map(this::constructThumbnail))
                    .ifPresent(aResource.getThumbnails()::add);
        }

        aRow.getTitle().ifPresent(title -> {
            aResource.setLabel(new Label(title));
        });

        return aResource;
    }

    /**
     * Constructs a thumbnail resource based on the provided thumbnail identifier. Determines the type of media
     * represented by the identifier and creates an appropriate {@link ContentResource} instance, such as
     * {@link ImageContent} or {@link VideoContent}. For unsupported media types, a default placeholder image is used.
     *
     * @param aThumbnail The identifier or URL of the thumbnail to construct
     * @return A {@link ContentResource} representing the constructed thumbnail
     */
    private ContentResource constructThumbnail(final String aThumbnail) {
        return MediaType.fromString(aThumbnail).map(thumbType -> switch (thumbType) {
            case MediaType.IMAGE_GIF, MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG -> new ImageContent(aThumbnail);
            case MediaType.VIDEO_H264, MediaType.VIDEO_MP4, MediaType.VIDEO_MPEG -> new VideoContent(aThumbnail);
            default -> new ImageContent(aThumbnail);
        }).orElseGet(() -> {
            final String tnPattern = Env.get(Configs.JPV3_THUMBNAIL_PATTERN, "{}/full/!200,200/0/default.jpg");
            return new ImageContent(StringUtils.format(tnPattern, aThumbnail));
        });
    }
}
