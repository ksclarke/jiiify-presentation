package info.freelibrary.iiif.presentation;

public interface TemporalContentResource<T> extends ContentResource {

    float getDuration();

    T setDuration(float aDuration);
}
