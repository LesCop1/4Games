package fr.bcecb.resources;

import fr.bcecb.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private final Map<ResourceHandle, IResource> LOADED_RESOURCES = new HashMap<>();

    public ResourceManager() {

    }

    public <R extends IResource> IResource getResource(ResourceHandle<R> handle) {
        return LOADED_RESOURCES.computeIfAbsent(handle, (n) -> {
            try {
                InputStream inputStream = getInputStream(handle);

                if (inputStream == null) {
                    throw new FileNotFoundException(handle.getHandle());
                }

                R resource = handle.getTypeToken().constructor(handle.getTypeToken().getRawType().getConstructor()).invoke(null);
                resource.load(inputStream);

                return resource;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
                Log.warning("Couldn't create resource for " + handle.getHandle() + " : " + e.getMessage());
            }

            return null;
        });
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
