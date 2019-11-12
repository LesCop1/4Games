package fr.bcecb.util;

import fr.bcecb.resources.Font;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Shader;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

public class Constants {
    /* COLORS */
    public static final Vector4f COLOR_WHITE = new Vector4f(1.0f);
    public static final Vector4f COLOR_BLACK = new Vector4f();

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

    private Constants() {}
}
