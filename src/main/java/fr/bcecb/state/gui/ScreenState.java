package fr.bcecb.state.gui;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.State;
import fr.bcecb.util.Resources;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static fr.bcecb.event.MouseEvent.Click.Type.RELEASED;

public abstract class ScreenState extends State {
    protected int width;
    protected int height;

    private final Map<Integer, GuiElement> guiElements = new TreeMap<>(Comparator.naturalOrder());
    private ResourceHandle<Texture> backgroundTexture = Resources.DEFAULT_BACKGROUND_TEXTURE;
    private boolean hasBackground = true;

    public ScreenState(String name) {
        super(name);
    }

    public ScreenState(String name, boolean hasBackground) {
        super(name);
        this.hasBackground = hasBackground;
    }

    public final void addGuiElement(GuiElement... elements) {
        for (GuiElement element : elements) {
            guiElements.put(element.getId(), element);
        }
    }

    public boolean hasBackground() {
        return hasBackground;
    }

    public final void clearGuiElements() {
        guiElements.clear();
    }

    public final GuiElement getGuiElementById(int id) { return guiElements.get(id); }

    public final Collection<GuiElement> getGuiElements() {
        return guiElements.values();
    }

    public void setBackgroundTexture(ResourceHandle<Texture> backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public ResourceHandle<Texture> getBackgroundTexture() {
        return backgroundTexture;
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldPauseBelow() {
        return true;
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
    public void onUpdate() {
        for (GuiElement element : getGuiElements()) {
            element.onUpdate();
        }
    }

    public void onClick(MouseEvent.Click event) {
        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible() || element.isDisabled()) continue;

            if (!event.isCancelled() && event.getType() == RELEASED && element.checkBounds(event.getX(), event.getY())) {
                element.onClick(event);

                event.setCancelled(true);
            }
        }
    }

    public void onHover(MouseEvent.Move event) {
        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible()) continue;

            element.setHovered(element.checkBounds(event.getX(), event.getY()));

            if (element.isHovered()) {
                element.onHover(event);

                event.setCancelled(true);
            }
        }
    }

    public void onScroll(MouseEvent.Scroll event) {
        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible() || element.isDisabled()) continue;

            if (element.isHovered()) {
                element.onScroll(event);

                event.setCancelled(true);
            }
        }
    }

    public void initGui(int width, int height) {
        this.width = width;
        this.height = height;

        this.initGui();
    }

    public abstract void initGui();
}