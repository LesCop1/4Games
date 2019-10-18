package fr.bcecb.state.gui;

import fr.bcecb.input.MouseEvent;
import fr.bcecb.render.IRenderable;
import fr.bcecb.util.BoundingBox;

public abstract class GuiElement implements IRenderable {
    private final int id;
    private final BoundingBox boundingBox;
    private boolean hovered;
    private boolean visible;

    public GuiElement(int id, BoundingBox boundingBox) {
        this.id = id;
        this.boundingBox = boundingBox;
        this.visible = true;
        this.hovered = false;
    }

    public int getId() {
        return id;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isHovered() {
        return hovered;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    protected abstract void onClick(MouseEvent.Click event);

    protected abstract void onHover(MouseEvent.Move event);

    protected abstract void onScroll(MouseEvent.Scroll event);
}
