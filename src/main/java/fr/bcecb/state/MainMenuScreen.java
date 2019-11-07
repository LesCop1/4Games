package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Log;

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

        GuiElement playButton = new Button(0, width / 2f, (height / 2f) - (height / 4f), (width / 4f), (height / 5f), true,
                "Play", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new GameSelectScreen()));

        GuiElement profileButton = new CircleButton(1, width / 2.0f, height / 2.0f, (height / 10f), true,
                new ResourceHandle<Texture>("textures/defaultProfile.png") {
                }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new ProfileScreen()));

        GuiElement achievementButton = new Button(2, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 7f),
                (width / 5f), (height / 10f), "Success", new ResourceHandle<Texture>("textures/defaultButton.png") {
        });

        GuiElement shopButton = new Button(3, (width / 2f) + 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f),
                "Shop", new ResourceHandle<Texture>("textures/defaultButton.png") {
        });

        GuiElement aboutButton = new Button(4, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f),
                "About", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().pushState(new AboutScreen()));

        GuiElement quitButton = new Button(5, (width / 2f) + 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f),
                "Quit", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(playButton);
        addGuiElement(profileButton);
        addGuiElement(achievementButton);
        addGuiElement(shopButton);
        addGuiElement(aboutButton);
        addGuiElement(quitButton);
    }

    @Override
    public void onEnter() {
        Log.GAME.debug("Entered");
    }

    @Override
    public void onExit() {
        Log.GAME.debug("Exited");
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
