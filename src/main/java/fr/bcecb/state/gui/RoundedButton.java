package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Resources;

public class RoundedButton extends Button {
    private float radius;
    private ResourceHandle<Texture> texture;
    private ResourceHandle<Texture> onHoverTexture;

    public RoundedButton(int id, float x, float y, float width, float height, float radius, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, radius, false, null, texture, null);
    }

    public RoundedButton(int id, float x, float y, float width, float height, float radius, boolean centered, String title, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, radius, centered, title, texture, null);
    }

    public RoundedButton(int id, float x, float y, float width, float height, float radius, boolean centered, String title, ResourceHandle<Texture> texture, ResourceHandle<Texture> onHoverTexture) {
        super(id, x, y, width, height, centered, title);
        this.radius = radius;
        this.texture =  MoreObjects.firstNonNull(texture, Resources.DEFAULT_BUTTON_TEXTURE);
        this.onHoverTexture = MoreObjects.firstNonNull(onHoverTexture, this.texture);
    }

    @Override
    public void onUpdate() {

    }

    // TODO
    @Override
    public boolean checkBounds(float x, float y) {
        return super.checkBounds(x, y);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setTexture(ResourceHandle<Texture> texture) {
        this.texture = texture;
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

    public void setOnHoverTexture(ResourceHandle<Texture> onHoverTexture) {
        this.onHoverTexture = onHoverTexture;
    }
}
