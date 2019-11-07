package fr.bcecb.resources;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.reflect.TypeToken;

public class ResourceHandle<R extends IResource> {
    private final String handle;

    public ResourceHandle(String handle) {
        this.handle = handle;
    }

    final TypeToken<R> getTypeToken() {
        return new TypeToken<>(getClass()) {
        };
    }

    final String getHandle() {
        return handle;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("handle", handle)
                .add("type", getTypeToken().getRawType().getSimpleName())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceHandle<?> that = (ResourceHandle<?>) o;
        return Objects.equal(getHandle(), that.getHandle());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHandle());
    }
}
