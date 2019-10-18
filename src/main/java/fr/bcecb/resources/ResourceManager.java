package fr.bcecb.resources;

import fr.bcecb.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final ResourceHandle<Texture> FALLBACK_TEXTURE_HANDLE = new ResourceHandle<>("textures/fallback.png") {};
    private final Map<ResourceHandle, IResource> LOADED_RESOURCES = new HashMap<>();

    public ResourceManager() {

    }

    public Texture getTexture(ResourceHandle<Texture> handle) {
        return getResource(handle != null && resourceExists(handle) ? handle : FALLBACK_TEXTURE_HANDLE);
    }

    public <R extends IResource> R getResource(ResourceHandle<R> handle) {
        if (handle == null) {
            return null;
        }

        if (!LOADED_RESOURCES.containsKey(handle)) {
            try {
                InputStream inputStream = getInputStream(handle);

                if (inputStream == null) {
                    throw new FileNotFoundException(handle.getHandle());
                }

                R resource = handle.getTypeToken().constructor(handle.getTypeToken().getRawType().getDeclaredConstructor()).invoke(null);
                resource.load(inputStream);
                LOADED_RESOURCES.put(handle, resource);
                return resource;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
                Log.SYSTEM.warning("Couldn't create resource for {0} : {1}", handle.getHandle(), e.getStackTrace());
                return null;
            }
        }
        else {
            //noinspection unchecked
            return (R) LOADED_RESOURCES.get(handle);
        }
    }

    private boolean resourceExists(ResourceHandle resource) {
        return ResourceManager.class.getClassLoader().getResource(resource.getHandle()) != null;
    }

    private InputStream getInputStream(ResourceHandle resource) {
        return ResourceManager.class.getClassLoader().getResourceAsStream(resource.getHandle());
    }

    public void cleanUp() {
        for (IResource resource : LOADED_RESOURCES.values()) {
            resource.dispose();
        }
    }
}
