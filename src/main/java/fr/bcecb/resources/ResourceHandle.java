package fr.bcecb.resources;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.reflect.TypeToken;

@SuppressWarnings("UnstableApiUsage")
public abstract class ResourceHandle<R extends IResource> {
    private final String handle;

    private final TypeToken<R> type = new TypeToken<>(getClass()) {};

    public ResourceHandle(String handle) {
        this.handle = handle;
    }

    final TypeToken<R> getTypeToken() {
        return type;
    }

    final String getHandle() {
        return handle;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("handle", handle)
                .add("type", type.getRawType().getSimpleName())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceHandle<?> that = (ResourceHandle<?>) o;
        return Objects.equal(getHandle(), that.getHandle()) &&
                Objects.equal(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHandle(), type);
    }
}
