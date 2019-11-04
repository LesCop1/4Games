package fr.bcecb.state.gui;

import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.event.WindowEvent;
import fr.bcecb.state.State;

import java.util.Collection;
import java.util.Set;

import static fr.bcecb.event.MouseEvent.Click.Type.RELEASED;

public abstract class ScreenState extends State {
    private final Set<GuiElement> guiElements = Sets.newHashSet();

    protected ScreenState(String name) {
        super(name);
        initGui();
        Game.getEventBus().register(this);
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

    public abstract void initGui();

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

            if (!event.isCancelled() && event.getType() == RELEASED && element.checkBounds(event.getX(), event.getY())) {
                element.getClickHandler().accept(event);

                event.setCancelled(true);
            }
        }
    }

    @Subscribe
    private void handleHoverEvent(MouseEvent.Move event) {
        if (!Game.instance().getStateEngine().isCurrentState(this)) return;


        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible()) continue;

            element.setHovered(element.checkBounds(event.getX(), event.getY()));

            if (element.isHovered()) {
                element.getHoverHandler().accept(event);
            }
        }
    }

    @Subscribe
    private void handleScrollEvent(MouseEvent.Scroll event) {
        if (!Game.instance().getStateEngine().isCurrentState(this)) return;

        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible()) continue;

            if (element.isHovered()) {
                element.getScrollHandler().accept(event);
            }
        }
    }

    @Subscribe
    private void handleWindowResizeEvent(WindowEvent.Size event) {
        this.guiElements.clear();
        initGui();
    }
}