package fr.bcecb.util;

import com.google.common.base.MoreObjects;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.StringResource;

public class ShaderDescriptor {
    private final String vertex;
    private final String fragment;
    private final String geometry;

    public ShaderDescriptor() {
        this.vertex = null;
        this.fragment = null;
        this.geometry = null;
    }

    public ShaderDescriptor(String vertex, String fragment, String geometry) {
        this.vertex = vertex;
        this.fragment = fragment;
        this.geometry = geometry;
    }

    public String getVertex() {
        return vertex;
    }

    public String getFragment() {
        return fragment;
    }

    public String getGeometry() {
        return geometry;
    }

    public boolean isVertexEnabled() {
        return vertex != null;
    }

    public boolean isFragmentEnabled() {
        return fragment != null;
    }

    public boolean isGeometryEnabled() {
        return geometry != null;
    }

    public ResourceHandle<StringResource> getVertexResource() {
        return isVertexEnabled() ? new ResourceHandle<>(getVertex()) {} : null;
    }

    public ResourceHandle<StringResource> getFragmentResource() {
        return isFragmentEnabled() ? new ResourceHandle<>(getFragment()) {} : null;
    }

    public ResourceHandle<StringResource> getGeometryResource() {
        return isGeometryEnabled() ? new ResourceHandle<>(getGeometry()) {} : null;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("vertex", vertex)
                .add("fragment", fragment)
                .add("geometry", geometry)
                .add("vertexResource", getVertexResource())
                .add("fragmentResource", getFragmentResource())
                .add("geometryResource", getGeometryResource())
                .toString();
    }
}
