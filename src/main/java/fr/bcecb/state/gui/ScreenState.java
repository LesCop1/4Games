package fr.bcecb.state.gui;

import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import fr.bcecb.Game;
import fr.bcecb.input.MouseEvent;
import fr.bcecb.state.State;

import java.util.Collection;
import java.util.Set;

import static fr.bcecb.input.MouseEvent.Click.Type.PRESSED;

public class ScreenState extends State {
    private final Set<GuiElement> guiElements = Sets.newHashSet();

    protected ScreenState(String name) {
        super(name);
        Game.getEventBus().register(this);
    }

    protected final void addGuiElement(GuiElement element) {
        guiElements.add(element);
    }

    protected final void removeGuiElement(GuiElement element) {
        guiElements.remove(element);
    }

    public final Collection<GuiElement> getGuiElements() {
        return guiElements;
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void update() {

    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldUpdateBelow() {
        return false;
    }

    @Subscribe
    private void handleClickEvent(MouseEvent.Click event) {
        if (!Game.instance().getStateEngine().isCurrentState(this)) return;

        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible()) continue;

            if (!event.isCancelled() && event.getType() == PRESSED && element.getBoundingBox().checkCoordinates(event.getX(), event.getY())) {
                element.onClick(event);

                event.setCancelled(true);
            }
        }
    }

    @Subscribe
    private void handleHoverEvent(MouseEvent.Move event) {
        if (!Game.instance().getStateEngine().isCurrentState(this)) return;


        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible()) continue;

            element.setHovered(element.getBoundingBox().checkCoordinates(event.getX(), event.getY()));

            if (element.isHovered()) {
                element.onHover(event);
            }
        }
    }

    @Subscribe
    private void handleScrollEvent(MouseEvent.Scroll event) {
        if (!Game.instance().getStateEngine().isCurrentState(this)) return;

        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible()) continue;

            if (element.isHovered()) {
                element.onScroll(event);
            }
        }
    }
}
