package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.IRenderable;

import java.util.function.Consumer;

public abstract class GuiElement implements IRenderable {
    private final int id;
    private boolean hovered;
    private boolean visible;
    private boolean enabled;

    private float x, width;
    private float y, height;

    private Consumer<MouseEvent.Click> clickHandler;
    private Consumer<MouseEvent.Move> hoverHandler;
    private Consumer<MouseEvent.Scroll> scrollHandler;

    public GuiElement(int id, float x, float y, float width, float height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hovered = false;
        this.visible = true;
        this.enabled = true;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    boolean checkBounds(float x, float y) {
        return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Consumer<MouseEvent.Click> getClickHandler() {
        return clickHandler;
    }

    public GuiElement setClickHandler(Consumer<MouseEvent.Click> clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public Consumer<MouseEvent.Move> getHoverHandler() {
        return hoverHandler;
    }

    public GuiElement setHoverHandler(Consumer<MouseEvent.Move> hoverHandler) {
        this.hoverHandler = hoverHandler;
        return this;
    }

    public Consumer<MouseEvent.Scroll> getScrollHandler() {
        return scrollHandler;
    }

    public GuiElement setScrollHandler(Consumer<MouseEvent.Scroll> scrollHandler) {
        this.scrollHandler = scrollHandler;
        return this;
    }

    public abstract void onClick(MouseEvent.Click event);

    public abstract void onHover(MouseEvent.Move event);

    public abstract void onScroll(MouseEvent.Scroll event);
}
