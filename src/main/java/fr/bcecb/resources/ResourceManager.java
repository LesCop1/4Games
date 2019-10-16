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
        if (!LOADED_RESOURCES.containsKey(handle)) {
            try {
                InputStream inputStream = getInputStream(handle);

                if (inputStream == null) {
                    throw new FileNotFoundException(handle.getHandle());
                }

                IResource resource = handle.getTypeToken().constructor(handle.getTypeToken().getRawType().getDeclaredConstructor()).invoke(null);
                resource.load(inputStream);
                LOADED_RESOURCES.put(handle, resource);
                return resource;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IOException e) {
                Log.warning("Couldn't create resource for " + handle.getHandle() + " : ");
                e.printStackTrace();
                return null;
            }
        }
        else {
            return LOADED_RESOURCES.get(handle);
        }
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
