package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.animation.Animation;
import fr.bcecb.render.animation.BounceAnimation;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

public class Button extends GuiElement {
    private final Animation<Float> hoverAnimation = new BounceAnimation(1.0f, 0.1f, 3.0f);

    private final String title;
    private ResourceHandle<Texture> texture;

    public Button(int id, float x, float y, float width, float height, boolean centered) {
        this(id, x, y, width, height, centered, (String) null);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title) {
        this(id, x, y, width, height, centered, title, null);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, centered, null, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, ResourceHandle<Texture> texture) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
        this.title = title;
        this.texture = texture;
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

    @Override
    public void onClick(MouseEvent.Click event) {
    }

    @Override
    public void onDrag(MouseEvent.Click clickEvent, MouseEvent.Move moveEvent) {

    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

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
        return texture;
    }

    public ResourceHandle<Texture> getHoverTexture() {
        return getTexture();
    }
}