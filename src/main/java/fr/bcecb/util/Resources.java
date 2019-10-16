package fr.bcecb.util;

import fr.bcecb.Game;
import fr.bcecb.resources.IResource;
import fr.bcecb.resources.ResourceHandle;

import java.io.*;
import java.util.stream.Collectors;

public class Resources {

    public static <R extends IResource> R getResource(ResourceHandle<R> handle) {
        //noinspection unchecked
        return (R) Game.instance().getResourceManager().getResource(handle);
    }

    public static String readResource(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
