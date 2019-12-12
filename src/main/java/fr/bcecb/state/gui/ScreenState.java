package fr.bcecb.state.gui;

import fr.bcecb.Game;
import fr.bcecb.input.Key;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Sound;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.State;
import fr.bcecb.state.StateManager;
import fr.bcecb.util.Resources;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public abstract class ScreenState extends State {
    protected static final int BACK_BUTTON_ID = -1;
    private final Map<Integer, GuiElement> guiElements = new TreeMap<>(Comparator.naturalOrder());
    protected int width;
    protected int height;
    private GuiElement selection;
    private ResourceHandle<Texture> backgroundTexture = Resources.DEFAULT_BACKGROUND_TEXTURE;

    public ScreenState(StateManager stateManager, String name) {
        super(stateManager, name);
    }

    public final void addGuiElement(GuiElement... elements) {
        for (GuiElement element : elements) {
            guiElements.put(element.getId(), element);
        }
    }

    public void disableBackground() {
        this.backgroundTexture = null;
    }

    public final void clearGuiElements() {
        guiElements.clear();
    }

    public final GuiElement getGuiElementById(int id) {
        return guiElements.get(id);
    }

    public final Collection<GuiElement> getGuiElements() {
        return guiElements.values();
    }

    public ResourceHandle<Texture> getBackgroundTexture() {
        return backgroundTexture;
    }

    public boolean hasBackgroundMusic() {
        return false;
    }

    public ResourceHandle<Sound> getBackgroundMusic() {
        return null;
    }

    public void setBackgroundTexture(ResourceHandle<Texture> backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
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
    }

    @Override
    public void onExit() {
    }

    @Override
    public void onUpdate() {
        for (GuiElement element : getGuiElements()) {
            element.onUpdate();
        }
    }

    public final boolean mouseClicked(MouseButton mouseButton, float x, float y) {
        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible() || element.isDisabled()) continue;

            if (element.checkBounds(x, y)) {
                if (element.getId() == BACK_BUTTON_ID) {
                    Game.instance().getStateManager().popState();
                    return true;
                }

                if (mouseClicked(element.getId(), mouseButton)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean mouseReleased() {
        return false;
    }

    public final boolean mouseMoved(float x, float y, boolean clicked) {
        if (selection != null && clicked) {
            mouseDragged(selection.getId());
        }

        for (GuiElement element : getGuiElements()) {
            if (!element.isVisible() || element.isDisabled()) continue;

            if (element.checkBounds(x, y)) {
                element.setHovered(true);
            } else {
                element.setHovered(false);
            }
        }

        return false;
    }

    public void initGui(int width, int height) {
        this.width = width;
        this.height = height;

        this.initGui();
    }

    public abstract void initGui();

    public abstract boolean mouseClicked(int id, MouseButton button);

    public void mouseDragged(int id) {

    }

    public boolean keyPressed(Key key) {
        return false;
    }
}