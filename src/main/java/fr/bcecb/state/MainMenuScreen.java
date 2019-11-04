package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Log;

public class MainMenuScreen extends ScreenState {
    public MainMenuScreen() {
        super("main_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();

        GuiElement playButton = new CircleButton(0, width / 2.0f, height / 2.0f, 200.0f, true)
                .setClickHandler(e -> Game.instance().getStateEngine().popState());
        addGuiElement(playButton);
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
