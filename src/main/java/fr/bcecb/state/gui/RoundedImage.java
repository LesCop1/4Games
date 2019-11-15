package fr.bcecb.state.gui;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

public class RoundedImage extends Image {
    private float radius;

    public RoundedImage(int id, ResourceHandle<Texture> image, float x, float y, float width, float height, float radius, boolean keepRatio) {
        super(id, image, x, y, width, height, keepRatio);
        this.radius = radius;
    }

    // TODO
    @Override
    boolean checkBounds(float x, float y) {
        return super.checkBounds(x, y);
    }

    public float getRadius() {
        return radius;
    }
}
