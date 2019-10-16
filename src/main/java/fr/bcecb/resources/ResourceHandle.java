package fr.bcecb.resources;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("UnstableApiUsage")
public class ResourceHandle<R extends IResource> {
    private final String handle;

    private final TypeToken<R> typeToken = new TypeToken<>(getClass()) {};

    public ResourceHandle(String handle) {
        this.handle = handle;
    }

    TypeToken<R> getTypeToken() {
        return typeToken;
    }

    String getHandle() {
        return handle;
    }
}
