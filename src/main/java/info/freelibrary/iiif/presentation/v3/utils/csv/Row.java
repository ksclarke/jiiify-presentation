
package info.freelibrary.iiif.presentation.v3.utils.csv;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import info.freelibrary.iiif.presentation.v3.utils.JSON;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.warnings.PMD;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.OptionalInt;

/** A simple class to hold the CSV row data. */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Keys.FILE_NAME, Keys.OBJECT_TYPE, Keys.RESOURCE_TYPE, Keys.TITLE, Keys.ITEM_SEQUENCE, Keys.ITEM_ID,
    Keys.PARENT_ID, Keys.TARGET, Keys.BEHAVIOR, Keys.VIEWING_DIRECTION, Keys.BUCKETEER_STATE, Keys.THUMBNAIL,
    Keys.MEDIA_HEIGHT, Keys.MEDIA_WIDTH, Keys.ACCESS_URL, Keys.NOTES })
@SuppressWarnings({ PMD.GOD_CLASS, PMD.CYCLOMATIC_COMPLEXITY, PMD.EXCESSIVE_PUBLIC_COUNT, PMD.TOO_MANY_FIELDS,
    PMD.TOO_MANY_METHODS })
public final class Row {

    /** Create a reusable configured mapper. */
    private static final ObjectMapper MAPPER =
            new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).registerModule(new Jdk8Module());

    /** An image file path. */
    private String myFileName;

    /** The item's object type. */
    private String myObjectType;

    /** The item's IIIF resource type. */
    private String myResourceType;

    /** The item's title. */
    private String myTitle;

    /** The item's sequence number. */
    private String myItemSequence;

    /** The item's ID. */
    private String myItemID;

    /** The item's parent ID. */
    private String myParentID;

    /** The item's IIIF target. */
    private String myTarget;

    /** The item's behavior. */
    private String myBehavior;

    /** The item's viewing direction. */
    private String myViewingDirection;

    /** The item's bucketeer state. */
    private String myBucketeerState;

    /** The item's thumbnail. */
    private String myThumbnail;

    /** The item's media height. */
    private int myMediaHeight;

    /** The item's media width. */
    private int myMediaWidth;

    /** The item's IIIF access URL. */
    private URL myAccessURL;

    /** The item's IIIF manifest URL. */
    private URL myManifestURL;

    /** The item's representative image. */
    private String myRepresentativeImage;

    /** The item's masthead image. */
    private String myMasthead;

    /** The item's notes. */
    private String myNotes;

    /** The item's alternative title. */
    private String myAltTitleOther;

    /** The item's visibility. */
    private String myVisibility;

    /** The item's format extent. */
    private String myFormatExtent;

    /** The item's format medium. */
    private String myFormatMedium;

    /** The item's genre. */
    private String myGenre;

    /** The item's subject. */
    private String mySubject;

    /** The item's subject geographic. */
    private String mySubjectGeo;

    /** The item's subject temporal. */
    private String mySubjectTemporal;

    /** The item's subject topic. */
    private String mySubjectTopic;

    /** The item's subject name. */
    private String mySubjectName;

    /** The item's language. */
    private String myLanguage;

    /** The item's place of origin. */
    private String myPlaceOfOrigin;

    /** The item's repository. */
    private String myRepository;

    /** The item's program. */
    private String myProgram;

    /** The item's type of resource. */
    private String myTypeOfResource;

    /** The item's copyright status. */
    private String myCopyrightStatus;

    /** The item's copyright holder. */
    private String myCopyrightHolder;

    /** The item's editor. */
    private String myEditor;

    /** The item's creator. */
    private String myCreator;

    /** The item's publisher. */
    private String myPublisher;

    /** The item's descriptive note. */
    private String myDescriptiveNote;

    /** The item's contents note. */
    private String myContentsNote;

    /** The item's note. */
    private String myNote;

    /** The item's summary note. */
    private String mySummary;

    /** The item's local rights statement. */
    private String myLocalRightsStatement;

    /** The item's rights contact information. */
    private String myRightsContact;

    /**
     * The item's IIIF resource type.
     *
     * @param aID The item's ID
     * @param aObjectType The item's object type
     * @param aResourceType The item's resource type
     */
    public Row(final String aID, final String aObjectType, final String aResourceType) {
        myItemID = aID;
        myObjectType = aObjectType;
        myResourceType = aResourceType;
    }

    /** Creates a new Row. */
    private Row() {
        // This is intentionally left empty
    }

    /**
     * Gets the file name (image file path) for this row, if present.
     *
     * @return An Optional containing the file name if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.FILE_NAME)
    public Optional<String> getFileName() {
        return myFileName == null || myFileName.isBlank() ? Optional.empty() : Optional.of(myFileName);
    }

    /**
     * Sets the file name (image file path) for this row.
     *
     * @param aFileName The file name to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.FILE_NAME)
    @JsonAlias({ "file name", "file-name", "file.name", "file_name" })
    public Row setFileName(final String aFileName) {
        myFileName = aFileName;
        return this;
    }

    /**
     * Gets the object type.
     *
     * @return An Optional containing the object type if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.OBJECT_TYPE)
    public Optional<String> getObjectType() {
        return myObjectType == null || myObjectType.isBlank() ? Optional.empty() : Optional.of(myObjectType);
    }

    /**
     * Sets the object type.
     *
     * @param aObjectType The object type to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.OBJECT_TYPE)
    @JsonAlias({ "object type", "object-type", "object.type", "object_type" })
    public Row setObjectType(final String aObjectType) {
        myObjectType = aObjectType;
        return this;
    }

    /**
     * Gets the optional IIIF resource type.
     *
     * @return An Optional containing the resource type if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.RESOURCE_TYPE)
    public Optional<String> getResourceType() {
        return myResourceType == null || myResourceType.isBlank() ? Optional.empty() : Optional.of(myResourceType);
    }

    /**
     * Sets the optional IIIF resource type.
     *
     * @param aResourceType The resource type to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.RESOURCE_TYPE)
    @JsonAlias({ "iiif object type", "iiif-object-type", "iiif.object.type", "iiif_object_type" })
    public Row setResourceType(final String aResourceType) {
        myResourceType = aResourceType;
        return this;
    }

    /**
     * Gets the optional representative image.
     *
     * @return An Optional containing the representative image if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.REPRESENTATIVE_IMAGE)
    public Optional<String> getRepresentativeImage() {
        return myRepresentativeImage == null || myRepresentativeImage.isBlank() ? Optional.empty()
                : Optional.of(myRepresentativeImage);
    }

    /**
     * Sets the optional representative image.
     *
     * @param aRepresentativeImage The representative image to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.REPRESENTATIVE_IMAGE)
    public Row setRepresentativeImage(final String aRepresentativeImage) {
        myRepresentativeImage = aRepresentativeImage;
        return this;
    }

    /**
     * Gets the optional masthead.
     *
     * @return An Optional containing the masthead if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.MASTHEAD)
    public Optional<String> getMasthead() {
        return myMasthead == null || myMasthead.isBlank() ? Optional.empty() : Optional.of(myMasthead);
    }

    /**
     * Sets the optional representative image.
     *
     * @param aMasthead The masthead to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.MASTHEAD)
    public Row setMasthead(final String aMasthead) {
        myMasthead = aMasthead;
        return this;
    }

    /**
     * Gets the optional descriptive note.
     *
     * @return An Optional containing the descriptive note if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.DESCRIPTIVE_NOTE)
    public Optional<String> getDescriptiveNote() {
        return myDescriptiveNote == null || myDescriptiveNote.isBlank() ? Optional.empty()
                : Optional.of(myDescriptiveNote);
    }

    /**
     * Sets the optional descriptive note.
     *
     * @param aDescriptiveNote The descriptive note to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.DESCRIPTIVE_NOTE)
    public Row setDescriptiveNote(final String aDescriptiveNote) {
        myDescriptiveNote = aDescriptiveNote;
        return this;
    }

    /**
     * Gets the optional contents note.
     *
     * @return An Optional containing the contents note if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.CONTENTS_NOTE)
    public Optional<String> getContentsNote() {
        return myContentsNote == null || myContentsNote.isBlank() ? Optional.empty() : Optional.of(myContentsNote);
    }

    /**
     * Sets the optional contents note.
     *
     * @param aContentsNote The contents note to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.CONTENTS_NOTE)
    public Row setContentsNote(final String aContentsNote) {
        myContentsNote = aContentsNote;
        return this;
    }

    /**
     * Gets the optional alternative title.
     *
     * @return An Optional containing the alternative title if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.ALT_TITLE_OTHER)
    public Optional<String> getAltTitleOther() {
        return myAltTitleOther == null || myAltTitleOther.isBlank() ? Optional.empty() : Optional.of(myAltTitleOther);
    }

    /**
     * Sets the optional alternative title.
     *
     * @param aAltTitleOther The alternative title to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.ALT_TITLE_OTHER)
    @JsonAlias({ "alt-title-other", "alt.title.other", "alt_title_other" })
    public Row setAltTitleOther(final String aAltTitleOther) {
        myAltTitleOther = aAltTitleOther;
        return this;
    }

    /**
     * Gets the optional visibility.
     *
     * @return An Optional containing the visibility if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.VISIBILITY)
    public Optional<String> getVisibility() {
        return myVisibility == null || myVisibility.isBlank() ? Optional.empty() : Optional.of(myVisibility);
    }

    /**
     * Sets the optional visibility.
     *
     * @param aVisibility The visibility to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.VISIBILITY)
    public Row setVisibility(final String aVisibility) {
        myVisibility = aVisibility;
        return this;
    }

    /**
     * Gets the optional copyright status.
     *
     * @return An Optional containing the copyright status if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.COPYRIGHT_STATUS)
    public Optional<String> getCopyrightStatus() {
        return myCopyrightStatus == null || myCopyrightStatus.isBlank() ? Optional.empty()
                : Optional.of(myCopyrightStatus);
    }

    /**
     * Sets the optional copyright status.
     *
     * @param aCopyrightStatus The copyright status to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.COPYRIGHT_STATUS)
    public Row setCopyrightStatus(final String aCopyrightStatus) {
        myCopyrightStatus = aCopyrightStatus;
        return this;
    }

    /**
     * Gets the optional copyright holder.
     *
     * @return An Optional containing the copyright holder if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.COPYRIGHT_HOLDER)
    public Optional<String> getCopyrightHolder() {
        return myCopyrightHolder == null || myCopyrightHolder.isBlank() ? Optional.empty()
                : Optional.of(myCopyrightHolder);
    }

    /**
     * Sets the optional copyright status.
     *
     * @param aCopyrightHolder The copyright holder to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.COPYRIGHT_HOLDER)
    public Row setCopyrightHolder(final String aCopyrightHolder) {
        myCopyrightHolder = aCopyrightHolder;
        return this;
    }

    /**
     * Gets the optional format extent.
     *
     * @return An Optional containing the format extent if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.FORMAT_EXTENT)
    public Optional<String> getFormatExtent() {
        return myFormatExtent == null || myFormatExtent.isBlank() ? Optional.empty() : Optional.of(myFormatExtent);
    }

    /**
     * Sets the optional format extent.
     *
     * @param aFormatExtent The format extent to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.FORMAT_EXTENT)
    public Row setFormatExtent(final String aFormatExtent) {
        myFormatExtent = aFormatExtent;
        return this;
    }

    /**
     * Gets the optional format medium.
     *
     * @return An Optional containing the format medium if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.FORMAT_MEDIUM)
    public Optional<String> getFormatMedium() {
        return myFormatMedium == null || myFormatMedium.isBlank() ? Optional.empty() : Optional.of(myFormatMedium);
    }

    /**
     * Sets the optional format medium.
     *
     * @param aFormatMedium The format medium to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.FORMAT_MEDIUM)
    public Row setFormatMedium(final String aFormatMedium) {
        myFormatMedium = aFormatMedium;
        return this;
    }

    /**
     * Gets the optional repository.
     *
     * @return An Optional containing the repository if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.REPOSITORY)
    public Optional<String> getRepository() {
        return myRepository == null || myRepository.isBlank() ? Optional.empty() : Optional.of(myRepository);
    }

    /**
     * Sets the optional repository.
     *
     * @param aRepository The repository to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.REPOSITORY)
    @JsonAlias({ "Name.repository" })
    public Row setRepository(final String aRepository) {
        myRepository = aRepository;
        return this;
    }

    /**
     * Gets the optional local rights statement.
     *
     * @return An Optional containing local rights statement if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.RIGHTS_STATEMENT_LOCAL)
    public Optional<String> getLocalRights() {
        return myLocalRightsStatement == null || myLocalRightsStatement.isBlank() ? Optional.empty()
                : Optional.of(myLocalRightsStatement);
    }

    /**
     * Sets the optional local rights statement.
     *
     * @param aLocalRightsStatement The local rights statement to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.RIGHTS_STATEMENT_LOCAL)
    public Row setLocalRights(final String aLocalRightsStatement) {
        myLocalRightsStatement = aLocalRightsStatement;
        return this;
    }

    /**
     * Gets the optional rights contact.
     *
     * @return An Optional containing rights contact if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.RIGHTS_CONTACT)
    public Optional<String> getRightsContact() {
        return myRightsContact == null || myRightsContact.isBlank() ? Optional.empty() : Optional.of(myRightsContact);
    }

    /**
     * Sets the optional rights contact.
     *
     * @param aRightsContact The rights contact to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.RIGHTS_CONTACT)
    public Row setRightsContact(final String aRightsContact) {
        myRightsContact = aRightsContact;
        return this;
    }

    /**
     * Gets the optional program.
     *
     * @return An Optional containing the program if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.PROGRAM)
    public Optional<String> getProgram() {
        return myProgram == null || myProgram.isBlank() ? Optional.empty() : Optional.of(myProgram);
    }

    /**
     * Sets the optional program.
     *
     * @param aProgram The program to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.PROGRAM)
    public Row setProgram(final String aProgram) {
        myProgram = aProgram;
        return this;
    }

    /**
     * Gets the optional editor.
     *
     * @return An Optional containing the editor if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.EDITOR)
    public Optional<String> getEditor() {
        return myEditor == null || myEditor.isBlank() ? Optional.empty() : Optional.of(myEditor);
    }

    /**
     * Sets the optional editor.
     *
     * @param aEditor The editor to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.EDITOR)
    public Row setEditor(final String aEditor) {
        myEditor = aEditor;
        return this;
    }

    /**
     * Gets the optional publisher.
     *
     * @return An Optional containing the publisher if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.PUBLISHER)
    public Optional<String> getPublisher() {
        return myPublisher == null || myPublisher.isBlank() ? Optional.empty() : Optional.of(myPublisher);
    }

    /**
     * Sets the optional publisher.
     *
     * @param aPublisher The publisher to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.PUBLISHER)
    public Row setPublisher(final String aPublisher) {
        myPublisher = aPublisher;
        return this;
    }

    /**
     * Gets the optional creator.
     *
     * @return An Optional containing the creator if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.CREATOR)
    public Optional<String> getCreator() {
        return myCreator == null || myCreator.isBlank() ? Optional.empty() : Optional.of(myCreator);
    }

    /**
     * Sets the optional creator.
     *
     * @param aCreator The creator to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.CREATOR)
    @JsonAlias({ "Name.creator" })
    public Row setCreator(final String aCreator) {
        myCreator = aCreator;
        return this;
    }

    /**
     * Gets the optional resource type.
     *
     * @return An Optional containing the resource type if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.TYPE_OF_RESOURCE)
    public Optional<String> getTypeOfResource() {
        return myTypeOfResource == null || myTypeOfResource.isBlank() ? Optional.empty()
                : Optional.of(myTypeOfResource);
    }

    /**
     * Sets the optional resource type.
     *
     * @param aResourceType The resource type to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.TYPE_OF_RESOURCE)
    public Row setTypeOfResource(final String aResourceType) {
        myTypeOfResource = aResourceType;
        return this;
    }

    /**
     * Gets the optional place of origin.
     *
     * @return An Optional containing the place of origin if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.PLACE_OF_ORIGIN)
    public Optional<String> getPlaceOfOrigin() {
        return myPlaceOfOrigin == null || myPlaceOfOrigin.isBlank() ? Optional.empty() : Optional.of(myPlaceOfOrigin);
    }

    /**
     * Sets the optional place of origin.
     *
     * @param aPlaceOfOrigin The place of origin to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.PLACE_OF_ORIGIN)
    public Row setPlaceOfOrigin(final String aPlaceOfOrigin) {
        myPlaceOfOrigin = aPlaceOfOrigin;
        return this;
    }

    /**
     * Gets the optional genre.
     *
     * @return An Optional containing the genre if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.GENRE)
    public Optional<String> getGenre() {
        return myGenre == null || myGenre.isBlank() ? Optional.empty() : Optional.of(myGenre);
    }

    /**
     * Sets the optional genre.
     *
     * @param aGenre The genre to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.GENRE)
    public Row setGenre(final String aGenre) {
        myGenre = aGenre;
        return this;
    }

    /**
     * Gets the optional subject.
     *
     * @return An Optional containing the subject if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.SUBJECT)
    public Optional<String> getSubject() {
        return mySubject == null || mySubject.isBlank() ? Optional.empty() : Optional.of(mySubject);
    }

    /**
     * Sets the optional subject.
     *
     * @param aSubject The subject to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.SUBJECT)
    public Row setSubject(final String aSubject) {
        mySubject = aSubject;
        return this;
    }

    /**
     * Gets the optional subject name.
     *
     * @return An Optional containing the subject name if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.SUBJECT_NAME)
    public Optional<String> getSubjectName() {
        return mySubjectName == null || mySubjectName.isBlank() ? Optional.empty() : Optional.of(mySubjectName);
    }

    /**
     * Sets the optional subject name.
     *
     * @param aSubjectName The subject name to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.SUBJECT_NAME)
    public Row setSubjectName(final String aSubjectName) {
        mySubjectName = aSubjectName;
        return this;
    }

    /**
     * Gets the optional subject geographic.
     *
     * @return An Optional containing the subject geographic if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.SUBJECT_GEO)
    public Optional<String> getSubjectGeo() {
        return mySubjectGeo == null || mySubjectGeo.isBlank() ? Optional.empty() : Optional.of(mySubjectGeo);
    }

    /**
     * Sets the optional subject geographic.
     *
     * @param aSubjectGeo The subject geographic to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.SUBJECT_GEO)
    public Row setSubjectGeo(final String aSubjectGeo) {
        mySubjectGeo = aSubjectGeo;
        return this;
    }

    /**
     * Gets the optional subject temporal.
     *
     * @return An Optional containing the subject temporal if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.SUBJECT_TEMPORAL)
    public Optional<String> getSubjectTemporal() {
        return mySubjectTemporal == null || mySubjectTemporal.isBlank() ? Optional.empty()
                : Optional.of(mySubjectTemporal);
    }

    /**
     * Sets the optional subject temporal.
     *
     * @param aSubjectTemporal The subject temporal to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.SUBJECT_TEMPORAL)
    public Row setSubjectTemporal(final String aSubjectTemporal) {
        mySubjectTemporal = aSubjectTemporal;
        return this;
    }

    /**
     * Gets the optional subject topic.
     *
     * @return An Optional containing the subject topic if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.SUBJECT_TOPIC)
    public Optional<String> getSubjectTopic() {
        return mySubjectTopic == null || mySubjectTopic.isBlank() ? Optional.empty() : Optional.of(mySubjectTopic);
    }

    /**
     * Sets the optional subject topic.
     *
     * @param aSubjectTopic The subject topic to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.SUBJECT_TOPIC)
    public Row setSubjectTopic(final String aSubjectTopic) {
        mySubjectTopic = aSubjectTopic;
        return this;
    }

    /**
     * Gets the optional language.
     *
     * @return An Optional containing the language if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.LANGUAGE)
    public Optional<String> getLanguage() {
        return myLanguage == null || myLanguage.isBlank() ? Optional.empty() : Optional.of(myLanguage);
    }

    /**
     * Sets the optional language.
     *
     * @param aLanguage The language to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.LANGUAGE)
    public Row setLanguage(final String aLanguage) {
        myLanguage = aLanguage;
        return this;
    }

    /**
     * Gets the summary.
     *
     * @return An Optional containing the summary if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.SUMMARY)
    public Optional<String> getSummary() {
        return mySummary == null || mySummary.isBlank() ? Optional.empty() : Optional.of(mySummary);
    }

    /**
     * Sets the summary.
     *
     * @param aSummary The summary to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.SUMMARY)
    public Row setSummary(final String aSummary) {
        mySummary = aSummary;
        return this;
    }

    /**
     * Gets the title.
     *
     * @return An Optional containing the title if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.TITLE)
    public Optional<String> getTitle() {
        return myTitle == null || myTitle.isBlank() ? Optional.empty() : Optional.of(myTitle);
    }

    /**
     * Sets the title.
     *
     * @param aTitle The title to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.TITLE)
    public Row setTitle(final String aTitle) {
        myTitle = aTitle;
        return this;
    }

    /**
     * Gets the item sequence number.
     *
     * @return An Optional containing the item sequence if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.ITEM_SEQUENCE)
    public Optional<String> getItemSequence() {
        return myItemSequence == null || myItemSequence.isBlank() ? Optional.empty() : Optional.of(myItemSequence);
    }

    /**
     * Sets the item sequence number.
     *
     * @param aItemSequence The item sequence to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.ITEM_SEQUENCE)
    @JsonAlias({ "item sequence", "item-sequence", "item.sequence", "item_sequence" })
    public Row setItemSequence(final String aItemSequence) {
        myItemSequence = aItemSequence;
        return this;
    }

    /**
     * Gets the item ID (e.g., ARK or internal identifier).
     *
     * @return An Optional containing the item ID if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.ITEM_ID)
    public Optional<String> getItemID() {
        return myItemID == null || myItemID.isBlank() ? Optional.empty() : Optional.of(myItemID);
    }

    /**
     * Sets the item ID.
     *
     * @param aItemID The item ID to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.ITEM_ID)
    @JsonAlias({ "item ark", "item id", "item-ark", "item-id", "item.ark", "item.id", "item_ark", "item_id" })
    public Row setItemID(final String aItemID) {
        myItemID = aItemID;
        return this;
    }

    /**
     * Gets the parent ID for this item.
     *
     * @return An Optional containing the parent ID if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.PARENT_ID)
    public Optional<String> getParentID() {
        return myParentID == null || myParentID.isBlank() ? Optional.empty() : Optional.of(myParentID);
    }

    /**
     * Sets the parent ID for this item.
     *
     * @param aParentID The parent ID to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.PARENT_ID)
    @JsonAlias({ "parent ark", "parent id", "parent-ark", "parent-id", "parent.ark", "parent.id", "parent_ark",
        "parent_id" })
    public Row setParentID(final String aParentID) {
        myParentID = aParentID;
        return this;
    }

    /**
     * Gets the IIIF target for this item.
     *
     * @return An Optional containing the target if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.TARGET)
    public Optional<String> getTarget() {
        return myTarget == null || myTarget.isBlank() ? Optional.empty() : Optional.of(myTarget);
    }

    /**
     * Sets the IIIF target for this item.
     *
     * @param aTarget The IIIF target to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.TARGET)
    @JsonAlias({ "iiif target", "iiif-target", "iiif.target", "iiif_target" })
    public Row setTarget(final String aTarget) {
        myTarget = aTarget;
        return this;
    }

    /**
     * Gets the behavior associated with this item.
     *
     * @return An Optional containing the behavior; a null underlying value will cause a NullPointerException
     */
    @JsonGetter(Keys.BEHAVIOR)
    public Optional<String> getBehavior() {
        return myBehavior == null || myBehavior.isBlank() ? Optional.empty() : Optional.of(myBehavior);
    }

    /**
     * Sets the behavior associated with this item.
     *
     * @param aBehavior The behavior to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.BEHAVIOR)
    @JsonAlias({ "viewing hint", "viewing-hint", "viewing.hint", "viewing_hint", "viewinghint" })
    public Row setBehavior(final String aBehavior) {
        myBehavior = aBehavior;
        return this;
    }

    /**
     * Gets the viewing direction associated with this item (e.g., ltr, rtl).
     *
     * @return An Optional containing the viewing direction if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.VIEWING_DIRECTION)
    public Optional<String> getViewingDirection() {
        return myViewingDirection == null || myViewingDirection.isBlank() ? Optional.empty()
                : Optional.of(myViewingDirection);
    }

    /**
     * Sets the viewing direction associated with this item.
     *
     * @param aViewingDirection The viewing direction to set (e.g., ltr, rtl)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.VIEWING_DIRECTION)
    @JsonAlias({ "text direction", "text-direction", "text.direction", "text_direction", "viewing direction",
        "viewing-direction", "viewing.direction", "viewing_direction" })
    public Row setViewingDirection(final String aViewingDirection) {
        myViewingDirection = aViewingDirection;
        return this;
    }

    /**
     * Gets the bucketeer state associated with this item.
     *
     * @return An Optional containing the bucketeer state if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.BUCKETEER_STATE)
    public Optional<String> getBucketeerState() {
        return myBucketeerState == null || myBucketeerState.isBlank() ? Optional.empty()
                : Optional.of(myBucketeerState);
    }

    /**
     * Sets the bucketeer state associated with this item.
     *
     * @param aBucketeerState The bucketeer state to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.BUCKETEER_STATE)
    @JsonAlias({ "bucketeer state", "bucketeer-state", "bucketeer.state", "bucketeer_state" })
    public Row setBucketeerState(final String aBucketeerState) {
        myBucketeerState = aBucketeerState;
        return this;
    }

    /**
     * Gets the thumbnail reference for this item.
     *
     * @return An Optional containing the thumbnail (e.g., URI or path) if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.THUMBNAIL)
    public Optional<String> getThumbnail() {
        return myThumbnail == null || myThumbnail.isBlank() ? Optional.empty() : Optional.of(myThumbnail);
    }

    /**
     * Sets the thumbnail reference for this item.
     *
     * @param aThumbnail The thumbnail reference to set (e.g., URI or path)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.THUMBNAIL)
    public Row setThumbnail(final String aThumbnail) {
        myThumbnail = aThumbnail;
        return this;
    }

    /**
     * Gets the media height in pixels, if greater than zero.
     *
     * @return An OptionalInt containing the media height if positive; otherwise, an empty OptionalInt
     */
    @JsonGetter(Keys.MEDIA_HEIGHT)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public OptionalInt getMediaHeight() {
        return myMediaHeight > 0 ? OptionalInt.of(myMediaHeight) : OptionalInt.empty();
    }

    /**
     * Sets the media height in pixels.
     *
     * @param aMediaHeight The media height in pixels (non-negative)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.MEDIA_HEIGHT)
    @JsonAlias({ "media height", "media-height", "media.height", "media_height" })
    public Row setMediaHeight(final int aMediaHeight) {
        myMediaHeight = aMediaHeight;
        return this;
    }

    /**
     * Gets the media width in pixels, if greater than zero.
     *
     * @return An OptionalInt containing the media width if positive; otherwise, an empty OptionalInt
     */
    @JsonGetter(Keys.MEDIA_WIDTH)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public OptionalInt getMediaWidth() {
        return myMediaWidth > 0 ? OptionalInt.of(myMediaWidth) : OptionalInt.empty();
    }

    /**
     * Sets the media width in pixels.
     *
     * @param aMediaWidth The media width in pixels (non-negative)
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.MEDIA_WIDTH)
    @JsonAlias({ "media width", "media-width", "media.width", "media_width" })
    public Row setMediaWidth(final int aMediaWidth) {
        myMediaWidth = aMediaWidth;
        return this;
    }

    /**
     * Gets the IIIF access URL for this item.
     *
     * @return An Optional containing the access URL if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.ACCESS_URL)
    public Optional<URL> getAccessURL() {
        return Optional.ofNullable(myAccessURL);
    }

    /**
     * Sets the IIIF access URL for this item.
     *
     * @param aAccessURL The access URL to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.ACCESS_URL)
    @JsonAlias({ "access url", "access-url", "access.url", "access_url", "iiif access url", "iiif-access-url",
        "iiif.access.url", "iiif_access_url" })
    public Row setAccessURL(final URL aAccessURL) {
        myAccessURL = aAccessURL;
        return this;
    }

    /**
     * Gets the IIIF manifest URL for this item.
     *
     * @return An Optional containing the manifest URL if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.IIIF_MANIFEST_URL)
    public Optional<URL> getManifestURL() {
        return Optional.ofNullable(myManifestURL);
    }

    /**
     * Sets the IIIF manifest URL for this item.
     *
     * @param aManifestURL The manifest URL to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.IIIF_MANIFEST_URL)
    public Row setManifestURL(final URL aManifestURL) {
        myManifestURL = aManifestURL;
        return this;
    }

    /**
     * Gets any notes associated with this item.
     *
     * @return An Optional containing notes if set; otherwise, an empty Optional
     */
    @JsonGetter(Keys.NOTES)
    public Optional<String> getNotes() {
        return myNotes == null || myNotes.isBlank() ? Optional.empty() : Optional.of(myNotes.trim());
    }

    /**
     * Sets notes associated with this item.
     *
     * @param aNotes Notes to set
     * @return This Row instance for fluent chaining
     */
    @JsonSetter(Keys.NOTES)
    public Row setNotes(final String aNotes) {
        myNotes = aNotes;
        return this;
    }

    /**
     * Returns a pretty-printed JSON representation of this row using a configured Jackson ObjectMapper. If
     * serialization fails, falls back to the default Object.toString() implementation.
     *
     * @return A JSON string or the default toString if serialization fails
     */
    @Override
    public String toString() {
        try {
            return JSON.writeValueAsString(this).trim();
        } catch (final IOException details) {
            throw new I18nRuntimeException(details);
        }
    }
}
