package fr.bcecb.poker;

import fr.bcecb.input.MouseButton;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;

public class PokerSettingsScreen extends ScreenState {
    public PokerSettingsScreen(StateManager stateManager) {
        super(stateManager, "settings_poker");
    }

    @Override
    public void initGui() {

        float x = (width / 4f);
        float y = (height / 2f) - (height / 10f);
        float btnWidth = (width / 8f);
        float btnHeight = (height / 10f);

        for (int i = 0; i < 3; i++, x += (btnWidth + 5f)) {
            Button xPlayers = new Button(i, x, y, btnWidth, btnHeight, true, (i + 2) + "Players");
            addGuiElement(xPlayers);
        }

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");
        addGuiElement(backButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if ((id + 2) == 2) {
            stateManager.pushState(new PokerScreen(stateManager, 2));
            return true;
        } else if (id + 2 == 3) {
            stateManager.pushState(new PokerScreen(stateManager, 3));
            return true;
        } else if (id + 2 == 4) {
            stateManager.pushState(new PokerScreen(stateManager, 4));
            return true;
        }
        return false;
    }
}
