package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;


public class BattleshipScreen extends ScreenState {
    private Battleship battleship = new Battleship();

    public BattleshipScreen() {
        super("game-battleship");
    }

    @Override
    public void onEnter() {
        super.onEnter();
//        Game.instance().getStateManager().pushState(new FirstPhaseBattleshipScreen(battleship));

        setBackgroundTexture(new ResourceHandle<>("textures/mainMenuBG.png") {});
    }

    @Override
    public void initGui() {
        GuiElement backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", new ResourceHandle<>("textures/defaultButton.png") {}) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(backButton);
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