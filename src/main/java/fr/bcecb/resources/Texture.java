package fr.bcecb.resources;

import fr.bcecb.util.Log;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL33.*;

public class Texture extends GLResource {
    public Texture() {
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, getGLId());
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, getGLId());
    }

    @Override
    public int create(InputStream inputStream) throws IOException {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(true);

            ByteBuffer data = ByteBuffer.wrap(inputStream.readAllBytes());

            image = stbi_load_from_memory(data, w, h, channels, 4);

            if (image == null) {
                Log.warning("Couldn't load texture : " + stbi_failure_reason());
                return -1;
            }

            width = w.get();
            height = h.get();
        }

        int texture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        stbi_image_free(image);

        return texture;
    }

    @Override
    public void dispose() {
        glDeleteTextures(getGLId());
    }
}
