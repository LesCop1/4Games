package fr.bcecb.poker;

import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.sudoku.Sudoku;
import fr.bcecb.util.Resources;

public class PokerScreenState extends ScreenState {
    public PokerScreenState(String name) {
        super(name);
    }

    @Override
    public void initGui() {
        float width = Window.getCurrentWindow().getWidth();
        float height = Window.getCurrentWindow().getHeight();
        //GuiElement twoPlayers=new Button(10, (width / 4f), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, "2 Players", Resources.DEFAULT_BUTTON_TEXTURE)
        //       .setClickHandler((id, event) -> new Poker(2));

    }
}
