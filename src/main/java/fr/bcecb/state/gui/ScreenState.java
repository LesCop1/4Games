package fr.bcecb.state.gui;

import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.State;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import static fr.bcecb.event.MouseEvent.Click.Type.RELEASED;

public abstract class ScreenState extends State {
    private final Set<GuiElement> guiElements = Sets.newTreeSet(Comparator.comparingInt(GuiElement::getId));
    private ResourceHandle<Texture> backgroundTexture = ResourceManager.DEFAULT_TEXTURE;

    protected ScreenState(String name) {
        super(name);
    }

    @Override
    public void onEnter() {
        Game.EVENT_BUS.register(this);
    }

    @Override
    public void onExit() {
        Game.EVENT_BUS.unregister(this);
    }

    @Override
    public void update() {
    }

    public abstract void initGui();

    protected final void addGuiElement(GuiElement element) {
        guiElements.add(element);
    }

    protected final void addGuiElements(GuiElement... elements) {
        guiElements.addAll(Arrays.asList(elements));
    }

    protected final void removeGuiElement(GuiElement element) {
        guiElements.remove(element);
    }

    public final void clearGuiElements() {
        guiElements.clear();
    }

    public void setBackgroundTexture(ResourceHandle<Texture> backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public ResourceHandle<Texture> getBackgroundTexture() {
        return backgroundTexture;
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
            if (!element.isVisible() || !element.isEnabled()) continue;

            if (!event.isCancelled() && event.getType() == RELEASED && element.checkBounds(event.getX(), event.getY())) {
                element.onClick(event);

                if (element.getClickHandler() != null) {
                    element.getClickHandler().accept(event);
                }

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
                element.onHover(event);

                if (element.getHoverHandler() != null) {
                    element.getHoverHandler().accept(event);
                }
            }
        }
    }

    @Subscribe
    private void handleScrollEvent(MouseEvent.Scroll event) {
        if (!Game.instance().getStateEngine().isCurrentState(this)) return;

        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible() || !element.isEnabled()) continue;

            if (element.isHovered()) {
                element.onScroll(event);

                if (element.getScrollHandler() != null) {
                    element.getScrollHandler().accept(event);
                }
            }
        }
    }
}
