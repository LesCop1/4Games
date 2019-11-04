package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.IRenderable;

import java.util.function.Consumer;

public abstract class GuiElement implements IRenderable {
    private final int id;
    private boolean hovered;
    private boolean visible;

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
        this.visible = true;
        this.hovered = false;

        this.clickHandler = this::onClick;
        this.hoverHandler = this::onHover;
        this.scrollHandler = this::onScroll;
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Consumer<MouseEvent.Click> getClickHandler() {
        return MoreObjects.firstNonNull(clickHandler, this::onClick);
    }

    public GuiElement setClickHandler(Consumer<MouseEvent.Click> clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public Consumer<MouseEvent.Move> getHoverHandler() {
        return MoreObjects.firstNonNull(hoverHandler, this::onHover);
    }

    public GuiElement setHoverHandler(Consumer<MouseEvent.Move> hoverHandler) {
        this.hoverHandler = hoverHandler;
        return this;
    }

    public Consumer<MouseEvent.Scroll> getScrollHandler() {
        return MoreObjects.firstNonNull(scrollHandler, this::onScroll);
    }

    public GuiElement setScrollHandler(Consumer<MouseEvent.Scroll> scrollHandler) {
        this.scrollHandler = scrollHandler;
        return this;
    }

    protected void onClick(MouseEvent.Click event) {

    }

    protected void onHover(MouseEvent.Move event) {

    }

    protected void onScroll(MouseEvent.Scroll event) {

    }
}
