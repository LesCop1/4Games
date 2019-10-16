package fr.bcecb.state.gui;

import fr.bcecb.event.EventManager;
import fr.bcecb.event.Handle;
import fr.bcecb.input.MouseEvent;
import fr.bcecb.input.MouseManager;
import fr.bcecb.render.IRenderable;
import fr.bcecb.util.BoundingBox;

import static fr.bcecb.input.MouseEvent.Click.Type.PRESSED;

public abstract class GuiElement implements IRenderable {
    private boolean visible;
    private final BoundingBox boundingBox;

    public GuiElement() {
        this.visible = true;
        this.boundingBox = null;
        EventManager.register(this);
    }

    public GuiElement(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    @Handle
    private void handleClickEvent(MouseEvent.Click event) {
        if (!isVisible()) return;

        if (!event.isCancelled()) {
            if (event.getType() == PRESSED && boundingBox.checkCoordinates(event.getX(), event.getY())) {
                onClick(event);
            }

            event.setCancelled(true);
        }
    }

    @Handle
    private void handleHoverEvent(MouseEvent.Move event) {
        if (!isVisible()) return;

        if (boundingBox.checkCoordinates(event.getX(), event.getY())) {
            onHover(event);
        }
    }

    @Handle
    private void handleScrollEvent(MouseEvent.Scroll event) {
        if (!isVisible()) return;

        if (boundingBox.checkCoordinates(MouseManager.getPositionX(), MouseManager.getPositionY())) {
            onScroll(event);
        }
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
