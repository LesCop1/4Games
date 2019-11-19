package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class Image extends GuiElement {
    private ResourceHandle<Texture> image;
    private boolean keepRatio;
    private float rotation = 0f;

    public Image(int id, ResourceHandle<Texture> image, float x, float y, float width, float height, boolean keepRatio) {
        this(id, image, x, y, width, height, keepRatio, false);
    }

    public Image(int id, ResourceHandle<Texture> image, float x, float y, float width, float height, boolean keepRatio, boolean centered) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
        this.image = image;
        this.keepRatio = keepRatio;
    }

    public ResourceHandle<Texture> getImage() {
        return image;
    }

    public void setImage(ResourceHandle<Texture> image) {
        this.image = image;
    }

    public boolean keepRatio() {
        return keepRatio;
    }

    public void setKeepRatio(boolean keepRatio) {
        this.keepRatio = keepRatio;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void onUpdate() {

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
}
