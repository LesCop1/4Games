package fr.bcecb.state;

import fr.bcecb.state.gui.GuiState;
import fr.bcecb.util.Log;

public class MainMenuState extends GuiState {
    public MainMenuState() {
        super("main_menu");
    }

    @Override
    public void onEnter() {
        super.onEnter();
        Log.info(Log.GAME, "Entered");
    }

    @Override
    public void onExit() {
        super.onExit();
        Log.info(Log.GAME,"Exited");
    }

    @Override
    public void update() {
        super.update();
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
