package fr.bcecb.util;

import fr.bcecb.Game;
import fr.bcecb.resources.IResource;
import fr.bcecb.resources.ResourceHandle;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;

public class Resources {

    public static <R extends IResource> R getResource(ResourceHandle<R> handle) {
        return Game.instance().getResourceManager().getResource(handle);
    }

    public static ByteBuffer readBytes(InputStream inputStream) throws IOException {
        ByteBuffer buffer = BufferUtils.createByteBuffer(inputStream.available());
        buffer.put(inputStream.readAllBytes());
        buffer.flip();

        return buffer;
    }

    public static String readResource(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
