package fr.bcecb.state;

import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Sound;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Resources;

public class MainMenuScreen extends ScreenState {
    private GuiElement playButton;
    private GuiElement profileButton;
    private GuiElement achievementButton;
    private GuiElement shopButton;
    private GuiElement aboutButton;

    public MainMenuScreen(StateManager stateManager) {
        super(stateManager, "main_menu");
    }

    @Override
    public void onEnter() {
    }

    @Override
    public boolean hasBackgroundMusic() {
        return true;
    }

    @Override
    public ResourceHandle<Sound> getBackgroundMusic() {
        return new ResourceHandle<>("sounds/title.ogg") {};
    }

    @Override
    public void initGui() {
        setBackgroundTexture(Resources.DEFAULT_START_BACKGROUND_TEXTURE);
        this.playButton = new Button(1, (width / 2f), (height / 2f) - (height / 10f) - (height / 10f), 50, 25, true, "Play");

        this.profileButton = new CircleButton(2, width / 2.0f, height / 2.0f, (height / 10f), true) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return Resources.CURRENT_PROFILE_TEXTURE;
            }
        };

        this.achievementButton = new Button(3, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Success");

        this.shopButton = new Button(4, (width / 2f) + 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Shop");

        this.aboutButton = new Button(5, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "About");

        GuiElement quitButton = new Button(BACK_BUTTON_ID, (width / 2f) + 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "Quit");

        addGuiElement(playButton, profileButton, achievementButton, shopButton, aboutButton, quitButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == playButton.getId()) {
            stateManager.pushState(new GameSelectScreen(stateManager));
            return true;
        } else if (id == profileButton.getId()) {
            stateManager.pushState(new ProfileScreen(stateManager));
            return true;
        } else if (id == achievementButton.getId()) {
            stateManager.pushState(new AchievementScreen(stateManager));
            return true;
        } else if (id == shopButton.getId()) {
            stateManager.pushState(new ShopScreen(stateManager));
            return true;
        } else if (id == aboutButton.getId()) {
            stateManager.pushState(new AboutScreen(stateManager));
            return true;
        }
        return false;
    }
}
