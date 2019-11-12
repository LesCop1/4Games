package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Constants;

public class MainMenuScreen extends ScreenState {
    public MainMenuScreen() {
        super("main_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(Constants.DEFAULT_BACKGROUND_TEXTURE);

        final GuiElement logoImage = new Image(0, Constants.DEFAULT_LOGO_TEXTURE, (width / 2f), (height / 8f), (height / 6f), (height / 6f), true, true);

        final GuiElement playButton = new Button(10, (width / 2f), (height / 2f) - (height / 10f) - (height / 10f), (width / 4f), (height / 10f), true, "Play", Constants.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler(e -> Game.instance().getStateEngine().pushState(new GameSelectScreen()));

        final GuiElement profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Game.instance().currentProfile)
                .setClickHandler(e -> Game.instance().getStateEngine().pushState(new ProfileScreen()));

        final GuiElement achievementButton = new Button(11, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Success", Constants.DEFAULT_BUTTON_TEXTURE);

        final GuiElement shopButton = new Button(12, (width / 2f) + 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Shop", Constants.DEFAULT_BUTTON_TEXTURE);

        final GuiElement aboutButton = new Button(13, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "About", Constants.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler(e -> Game.instance().getStateEngine().pushState(new AboutScreen()));

        final GuiElement quitButton = new Button(14, (width / 2f) + 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "Quit", Constants.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(logoImage, playButton, profileButton, achievementButton, shopButton, aboutButton, quitButton);
    }
}
