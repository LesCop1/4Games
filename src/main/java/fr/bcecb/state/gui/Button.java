package fr.bcecb.state.gui;

import fr.bcecb.render.animation.Animation;
import fr.bcecb.render.animation.BounceAnimation;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;
import org.joml.Vector4f;

public class Button extends GuiElement {
    private final Animation<Float> hoverAnimation = new BounceAnimation(1.0f, 0.1f, 3.0f);

    private final String title;

    public Button(int id, float x, float y, float width, float height, boolean centered) {
        this(id, x, y, width, height, centered, null);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
        this.title = title;
    }

    public Animation<Float> getHoverAnimation() {
        return hoverAnimation;
    }

    @Override
    public void onUpdate() {
        if (isVisible() && !isDisabled() && isHovered()) {
            hoverAnimation.update();
        } else hoverAnimation.reset();
    }

    public String getTitle() {
        return title;
    }

    public float getTitleScale() {
        return 0.0f;
    }

    public Vector4f getTitleColor() {
        return Constants.COLOR_BLACK;
    }

    public ResourceHandle<Texture> getTexture() {
        return Resources.DEFAULT_BUTTON_TEXTURE;
    }

    public ResourceHandle<Texture> getHoverTexture() {
        return Resources.DEFAULT_BUTTON_HOVER_TEXTURE;
    }

    public ResourceHandle<Texture> getDisabledTexture() {
        return Resources.DEFAULT_BUTTON_DISABLED_TEXTURE;
    }
}