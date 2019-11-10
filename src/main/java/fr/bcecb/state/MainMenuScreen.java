package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.*;

public class MainMenuScreen extends ScreenState {
    public MainMenuScreen() {
        super("main_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<>("textures/mainMenuBG.png") {
        });

        final GuiElement logoImage = new Image(10, new ResourceHandle<>("textures/4GamesTitle.png") {}, (width / 2f), (height / 8f),
                (height / 6f), (height / 6f), true, true);

        final GuiElement playButton = new Button(0, (width / 2f), (height / 2f) - (height / 10f) - (height / 10f), (width / 4f), (height / 10f),
                true, "Play").setClickHandler(e -> Game.instance().getStateEngine().pushState(new GameSelectScreen()));

        final GuiElement profileButton = new CircleButton(1, width / 2.0f, height / 2.0f, (height / 10f),
                true, new ResourceHandle<>("textures/defaultProfile.png"){})
                .setClickHandler(e -> Game.instance().getStateEngine().pushState(new ProfileScreen()));

        final GuiElement achievementButton = new Button(2, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 7f),
                (width / 5f), (height / 10f), "Success");

        final GuiElement shopButton = new Button(3, (width / 2f) + 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f),
                "Shop");

        final GuiElement aboutButton = new Button(4, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f),
                "About").setClickHandler(e -> Game.instance().getStateEngine().pushState(new AboutScreen()));

        final GuiElement quitButton = new Button(5, (width / 2f) + 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f),
                "Quit").setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(logoImage, playButton, profileButton, achievementButton, shopButton, aboutButton, quitButton);
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    @Override
    public void update() {
    }

    @Override
    public boolean shouldRenderBelow() {
        return super.shouldRenderBelow();
    }

    @Override
    public boolean shouldUpdateBelow() {
        return super.shouldUpdateBelow();
    }
}
