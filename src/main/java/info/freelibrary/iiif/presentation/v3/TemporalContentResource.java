package info.freelibrary.iiif.presentation.v3;

public interface TemporalContentResource<T> extends ContentResource {

    float getDuration();

    T setDuration(Number aDuration);
}
