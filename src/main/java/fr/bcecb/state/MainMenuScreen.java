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
        setBackgroundTexture(new ResourceHandle<Texture>("textures/mainMenuBG.png") {
        });

        final GuiElement logo = new Image(10, new ResourceHandle<>("textures/4GamesTitle.png") {}, (width / 2f), (height / 8f),
                (width / 5f), (width / 5f), true, true);

        final GuiElement playButton = new Button(0, width / 2f, (height / 2f) - (height / 6f), (width / 4f), (height / 6f), true, "Play");

        playButton.setClickHandler(e -> {
            Game.instance().getStateEngine().pushState(new GameSelectScreen());
        });

        GuiElement profileButton = new CircleButton(1, width / 2.0f, height / 2.0f, (height / 10f), true).setClickHandler(e -> Game.instance().getStateEngine().pushState(new ProfileScreen()));

        GuiElement achievementButton = new Button(2, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 7f),
                (width / 5f), (height / 10f), "Success");

        GuiElement shopButton = new Button(3, (width / 2f) + 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f),
                "Shop");

        GuiElement aboutButton = new Button(4, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f),
                "About").setClickHandler(e -> Game.instance().getStateEngine().pushState(new AboutScreen()));

        GuiElement quitButton = new Button(5, (width / 2f) + 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f),
                "Quit").setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(logo);
        addGuiElement(playButton);
        addGuiElement(profileButton);
        addGuiElement(achievementButton);
        addGuiElement(shopButton);
        addGuiElement(aboutButton);
        addGuiElement(quitButton);
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
