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

        for (int i = 0; i < 3; i++) {
            Button xPlayers = new Button(i, (width / 4f) + ((width / 4f) * i), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, (i + 2) + "Players");
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
