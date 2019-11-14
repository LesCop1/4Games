package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class Image extends GuiElement {
    private ResourceHandle<Texture> image;
    private boolean keepRatio;

    public Image(int id, ResourceHandle<Texture> image, float x, float y, float maxX, float maxY, boolean keepRatio) {
        this(id, image, x, y, maxX, maxY, keepRatio, false);
    }

    public Image(int id, ResourceHandle<Texture> image, float x, float y, float maxX, float maxY, boolean keepRatio, boolean centered) {
        super(id, x - (centered ? (maxX / 2) : 0), y - (centered ? (maxY / 2) : 0), maxX, maxY);
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

    @Override
    public void onUpdate() {

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
}
