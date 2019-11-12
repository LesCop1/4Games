package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.IRenderable;

import java.util.function.BiConsumer;

public abstract class GuiElement implements IRenderable {
    private final int id;
    private boolean hovered;
    private boolean visible;
    private boolean disabled;

    private float x, width;
    private float y, height;

    private BiConsumer<Integer, MouseEvent.Click> clickHandler;
    private BiConsumer<Integer, MouseEvent.Move> hoverHandler;
    private BiConsumer<Integer, MouseEvent.Scroll> scrollHandler;

    public GuiElement(int id, float x, float y, float width, float height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hovered = false;
        this.visible = true;
        this.disabled = false;
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
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
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

    public BiConsumer<Integer, MouseEvent.Click> getClickHandler() {
        return clickHandler;
    }

    public GuiElement setClickHandler(BiConsumer<Integer, MouseEvent.Click> clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public BiConsumer<Integer, MouseEvent.Move> getHoverHandler() {
        return hoverHandler;
    }

    public GuiElement setHoverHandler(BiConsumer<Integer, MouseEvent.Move> hoverHandler) {
        this.hoverHandler = hoverHandler;
        return this;
    }

    public BiConsumer<Integer, MouseEvent.Scroll> getScrollHandler() {
        return scrollHandler;
    }

    public GuiElement setScrollHandler(BiConsumer<Integer, MouseEvent.Scroll> scrollHandler) {
        this.scrollHandler = scrollHandler;
        return this;
    }

    public abstract void onUpdate();

    public abstract void onClick(MouseEvent.Click event);

    public abstract void onHover(MouseEvent.Move event);

    public abstract void onScroll(MouseEvent.Scroll event);
}
