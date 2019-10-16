package fr.bcecb.resources;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

@SuppressWarnings("UnstableApiUsage")
public abstract class ResourceHandle<R extends IResource> {
    private final String handle;

    private final TypeToken<R> type = new TypeToken<>(getClass()) {};

    public ResourceHandle(String handle) {
        this.handle = handle;
    }

    public TypeToken<R> getTypeToken() {
        return type;
    }

    String getHandle() {
        return handle;
    }
}
