package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;


public class BattleshipScreen extends ScreenState {
    private Battleship battleship = new Battleship();

    public BattleshipScreen(StateManager stateManager) {
        super(stateManager, "game-battleship");
    }

    @Override
    public void onEnter() {
        super.onEnter();
        Game.instance().getStateManager().pushState(new FirstPhaseBattleshipScreen(battleship));

        setBackgroundTexture(new ResourceHandle<>("textures/mainMenuBG.png") {});
    }

    @Override
    public void initGui() {
        GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", new ResourceHandle<>("textures/default/defaultButton.png") {
        });
        addGuiElement(backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        return false;
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldPauseBelow() {
        return true;
    }
}