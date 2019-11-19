package fr.bcecb.poker;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.Sudoku;
import fr.bcecb.util.Resources;

public class PokerScreenState extends ScreenState {
    public PokerScreenState() {
        super("poker_selection");
    }

    @Override
    public void initGui() {

        GuiElement twoPlayers = new Button(1, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "2 Players", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new PokerState(2));
            }
        };

        GuiElement threePlayers = new Button(2, (width / 4f) * 2, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "3 Players", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new PokerState(3));
            }
        };

        final GuiElement fourPlayers = new Button(3, (width / 4f) * 3, (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "4 Players", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new PokerState(4));
            }
        };

        final GuiElement backButton = new Button(0, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };
        addGuiElement(twoPlayers, threePlayers, fourPlayers, backButton);

    }
}
