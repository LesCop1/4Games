package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;

public class GameSelectScreen extends ScreenState {
    protected GameSelectScreen() {
        super("game_select_menu");
    }

    @Override
    public void initGui() {
        addGuiElement(new Button(0, 0, 0, 100, 100) {
            @Override
            protected void onClick(MouseEvent.Click event) {
                Game.instance().getStateEngine().popState();
            }
        });
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldUpdateBelow() {
        return false;
    }
}