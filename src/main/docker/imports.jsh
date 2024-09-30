import static info.freelibrary.iiif.presentation.v3.properties.MediaType.IMAGE_JPEG;
import static info.freelibrary.iiif.presentation.v3.properties.ViewingDirection.RIGHT_TO_LEFT;
import static info.freelibrary.iiif.presentation.v3.properties.ViewingDirection.TOP_TO_BOTTOM;
import static info.freelibrary.iiif.presentation.v3.properties.behaviors.ManifestBehavior.PAGED;
import static info.freelibrary.iiif.presentation.v3.services.ImageService3.Profile.LEVEL_ONE;

import info.freelibrary.iiif.presentation.v3.AnnotationPage;
import info.freelibrary.iiif.presentation.v3.Canvas;
import info.freelibrary.iiif.presentation.v3.ImageContent;
import info.freelibrary.iiif.presentation.v3.Manifest;
import info.freelibrary.iiif.presentation.v3.PaintingAnnotation;
import info.freelibrary.iiif.presentation.v3.SoundContent;
import info.freelibrary.iiif.presentation.v3.VideoContent;
import info.freelibrary.iiif.presentation.v3.annotations.Target;
import info.freelibrary.iiif.presentation.v3.ids.Minter;
import info.freelibrary.iiif.presentation.v3.ids.MinterFactory;
import info.freelibrary.iiif.presentation.v3.properties.I18n;
import info.freelibrary.iiif.presentation.v3.properties.Label;
import info.freelibrary.iiif.presentation.v3.properties.Metadata;
import info.freelibrary.iiif.presentation.v3.properties.RequiredStatement;
import info.freelibrary.iiif.presentation.v3.properties.Summary;
import info.freelibrary.iiif.presentation.v3.properties.Value;
import info.freelibrary.iiif.presentation.v3.services.ImageService3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
