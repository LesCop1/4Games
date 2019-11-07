package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private Battleship battleship;
    private Boat boat;

    protected FirstPhaseBattleshipScreen(Battleship battleship) {
        super("game-battleship.firstphase");
        this.battleship = battleship;
    }

    @Override
    public void initGui() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                final GuiElement button = new Button((10 * i) + j, i, j, 10, 10, "" + (10 * i) + j);
                button.setClickHandler(e -> clickButton(e, button.getId()));
                addGuiElement(button);
            }
        }
    }

    public void clickButton(MouseEvent.Click event, int id) {}
}
