package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.IRenderable;

public abstract class GuiElement implements IRenderable {
    private final int id;
    private boolean hovered;
    private boolean visible;
    private boolean disabled;

    private final float x;
    private final float y;

    private final float xEnd;
    private final float height;

    public GuiElement(int id, float x, float y, float width, float height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.xEnd = width;
        this.height = height;
        this.hovered = false;
        this.visible = true;
        this.disabled = false;
    }

    public final int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return xEnd;
    }

    public float getHeight() {
        return height;
    }

    public boolean checkBounds(float x, float y) {
        return x >= this.x && x <= this.x + xEnd && y >= this.y && y <= this.y + height;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public abstract void onUpdate();

    public abstract void onClick(MouseEvent.Click event);

    public abstract void onDrag(MouseEvent.Click clickEvent, MouseEvent.Move moveEvent);

    public abstract void onHover(MouseEvent.Move event);

    public abstract void onScroll(MouseEvent.Scroll event);
}
