package fr.bcecb.poker;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.Sudoku;
import fr.bcecb.util.Resources;

public class PokerScreenState extends ScreenState {
    public PokerScreenState(StateManager stateManager) {
        super(stateManager, "poker_selection");
    }

    @Override
    public void initGui() {

        GuiElement twoPlayers = new Button(2, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "2 Players", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new PokerState(stateManager, 2));
            }
        };

        GuiElement threePlayers = new Button(3, (width / 4f) * 2, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "3 Players", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new PokerState(stateManager, 3));
            }
        };

        final GuiElement fourPlayers = new Button(4, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "4 Players", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new PokerState(stateManager, 4));
            }
        };

        final GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE);
        addGuiElement(twoPlayers, threePlayers, fourPlayers, backButton);

    }

    @Override
    public boolean mouseClicked(int id) {
        if (id == 2) {
            System.out.println("id = " + id);
            stateManager.pushState(new PokerState(stateManager, 2));
            return true;
        } else if (id == 3) {
            stateManager.pushState(new PokerState(stateManager, 3));
            return true;
        } else if (id == 4) {
            stateManager.pushState(new PokerState(stateManager, 4));
            return true;
        }
        return false;
    }
}
