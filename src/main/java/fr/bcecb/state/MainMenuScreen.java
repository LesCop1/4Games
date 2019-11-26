package fr.bcecb.state;

import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Constants;
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
        super.onEnter();
        stateManager.pushState(new EndGameScreen(stateManager, Constants.GameType.BATTLESHIP, 342342, 34));
    }

    @Override
    public void initGui() {
        Image logoImage = new Image(100, Resources.DEFAULT_LOGO_TEXTURE, (width / 2f), (height / 8f), 50, 50, true, true);

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

        addGuiElement(logoImage, playButton, profileButton, achievementButton, shopButton, aboutButton, quitButton);
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
            return true;
        } else if (id == shopButton.getId()) {
            return true;
        } else if (id == aboutButton.getId()) {
            stateManager.pushState(new AboutScreen(stateManager));
            return true;
        }
        return false;
    }
}
