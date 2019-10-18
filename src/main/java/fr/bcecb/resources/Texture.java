package fr.bcecb.resources;

import fr.bcecb.util.Log;
import fr.bcecb.util.Resources;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL33.*;

public class Texture extends GLResource {
    private int width;
    private int height;

    public Texture() {
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, getGLId());
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public int create(InputStream inputStream) throws IOException {
        ByteBuffer image;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer imageBuffer = Resources.readBytes(inputStream).flip();
            image = stbi_load_from_memory(imageBuffer, w, h, channels, 4);

            if (image == null) {
                Log.SYSTEM.warning("Couldn't load texture : " + stbi_failure_reason());
                return 0;
            }

            width = w.get();
            height = h.get();
        }

        int texture = create(image);
        stbi_image_free(image);
        return texture;
    }

    public int create(ByteBuffer textureBuffer) {
        int texture = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, textureBuffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        return texture;
    }

    @Override
    public void dispose() {
        glDeleteTextures(getGLId());
    }
}
