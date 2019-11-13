package fr.bcecb.util;

import fr.bcecb.resources.Font;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Shader;
import fr.bcecb.resources.Texture;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;

public class Resources {

    /* TEXTURES */
    public static final ResourceHandle<Texture> DEFAULT_TEXTURE = new ResourceHandle<>("textures/default.png") {};
    public static final ResourceHandle<Texture> DEFAULT_LOGO_TEXTURE = new ResourceHandle<>("textures/defaultLogo.png") {};
    public static final ResourceHandle<Texture> DEFAULT_BACKGROUND_TEXTURE = new ResourceHandle<>("textures/defaultBackground.png") {};
    public static final ResourceHandle<Texture> DEFAULT_BUTTON_TEXTURE = new ResourceHandle<>("textures/defaultButton.png") {};
    public static final ResourceHandle<Font> DEFAULT_FONT = new ResourceHandle<>("font.ttf") {};
    /* SHADERS */
    public static final ResourceHandle<Shader> DEFAULT_SHADER = new ResourceHandle<>("shaders/base.json") {};
    public static final ResourceHandle<Shader> CIRCLE_SHADER = new ResourceHandle<>("shaders/circle.json") {};
    public static final ResourceHandle<Shader> FONT_SHADER = new ResourceHandle<>("shaders/font.json") {};

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
