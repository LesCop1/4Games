package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.animation.Animation;
import fr.bcecb.render.animation.BounceAnimation;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

public class Button extends GuiElement {
    private String title;
    private float titleScale;
    private Vector4f titleColor;
    private ResourceHandle<Texture> texture;
    private ResourceHandle<Texture> hoverTexture;
    private Animation<Float> hoverAnimation = new BounceAnimation(1.0f, 0.1f, 3.0f);

    public Button(int id, float x, float y, float width, float height) {
        this(id, x, y, width, height, false);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered) {
        this(id, x, y, width, height, centered, (String) null);
    }

    public Button(int id, float x, float y, float width, float height, String title) {
        this(id, x, y, width, height, false, title, 1.0f, null);
    }

    public Button(int id, float x, float y, float width, float height, String title, float titleScale) {
        this(id, x, y, width, height, false, title, titleScale, null);
    }

    public Button(int id, float x, float y, float width, float height, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, false, null, 1.0f, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title) {
        this(id, x, y, width, height, centered, title, 1.0f, null);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, float titleScale) {
        this(id, x, y, width, height, centered, title, titleScale, null);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, centered, null, 1.0f, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, centered, title, 1.0f, texture, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, float titleScale, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, centered, title, titleScale, texture, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, float titleScale, ResourceHandle<Texture> texture, ResourceHandle<Texture> onHoverTexture) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
        this.title = title;
        this.titleScale = Math.min(titleScale, 1.0f);
        this.titleColor = Constants.COLOR_BLACK;
        this.texture = texture;
        this.hoverTexture = onHoverTexture;
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
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getTitleScale() {
        return titleScale;
    }

    public void setTitleScale(float titleScale) {
        this.titleScale = titleScale;
    }

    public Vector4f getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(Vector4f titleColor) {
        this.titleColor = titleColor;
    }

    public ResourceHandle<Texture> getTexture() {
        return texture;
    }

    public void setTexture(ResourceHandle<Texture> texture) {
        this.texture = texture;
    }

    public ResourceHandle<Texture> getHoverTexture() {
        return hoverTexture;
    }

    public void setHoverTexture(ResourceHandle<Texture> hoverTexture) {
        this.hoverTexture = hoverTexture;
    }
}