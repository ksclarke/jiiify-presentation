package info.freelibrary.iiif.presentation;

public interface SpatialContentResource<T> extends ContentResource {

    int getWidth();

    int getHeight();

    T setWidthHeight(int aWidth, int aHeight);
}
