package fr.bcecb.util;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

public class Constants {
    /* COLORS */
    public static final Vector4f COLOR_WHITE = new Vector4f(1.0f);
    public static final Vector4f COLOR_BLACK = new Vector4f();
    public static final Vector4f COLOR_GREY = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
    public static final Vector4f COLOR_GREEN = new Vector4f(0f, 1f, 0f, 1f);

    public static final ResourceHandle<Texture> CURRENT_PROFILE_TEXTURE = new ResourceHandle<>("textures/defaultProfile.jpg") {
    };

    private Constants() {}
}
