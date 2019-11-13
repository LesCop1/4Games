package fr.bcecb.util;

import com.google.common.base.MoreObjects;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.StringResource;

public class ShaderDescriptor {
    private String vertex = null;
    private String fragment = null;
    private String geometry = null;

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
        return isVertexEnabled() ? new ResourceHandle<>(vertex) {} : null;
    }

    public ResourceHandle<StringResource> getFragmentResource() {
        return isFragmentEnabled() ? new ResourceHandle<>(fragment) {} : null;
    }

    public ResourceHandle<StringResource> getGeometryResource() {
        return isGeometryEnabled() ? new ResourceHandle<>(geometry) {} : null;
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
