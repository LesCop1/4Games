package fr.bcecb.bingo;

import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;

public class SettingsBingoScreen extends ScreenState {
    private int nbGrids;
    private int difficulty;

    private Button startButton;

    public SettingsBingoScreen(StateManager stateManager) {
        super(stateManager, "settings_bingo");
    }

    @Override
    public void onEnter() {
        super.onEnter();
        this.nbGrids = 0;
        this.difficulty = 0;
    }

    @Override
    public void initGui() {
        setBackgroundTexture(Constants.BINGO_BACKGROUND);

        for (int i = 0; i < 6; i++) {
            Button nbXGridsButton = new Button(i, (width / 10f) + i * (width / 10f), (height / 6f) + 10, (width / 20f), (height / 10f), true, Integer.toString(i), Resources.DEFAULT_BUTTON_TEXTURE);
            addGuiElement(nbXGridsButton);
        }

        for (int i = 6; i < 9; i++) {
            Button difficultyButton = new Button(i, (width / 10f) + i * (width / 10f), 2 * (height / 6f) + 10, (width / 20f), (height / 10f), true, Integer.toString(i - 6), Resources.DEFAULT_BUTTON_TEXTURE);
            addGuiElement(difficultyButton);
        }

        this.startButton = new Button(12, (width / 2f), (height / 4f) + 2 * (height / 4f), (width / 5f), (height / 10f), true, "Start", Resources.DEFAULT_BUTTON_TEXTURE);

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE);

        addGuiElement(startButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        if (id == BACK_BUTTON_ID) {
            stateManager.popState();
            return true;
        } else if (id == this.startButton.getId()) {
            if (this.nbGrids != 0 && this.difficulty != 0) {
                stateManager.pushState(new BingoScreen(stateManager, this.nbGrids, this.difficulty));
            }
            return true;
        } else if (id < 6) {
            this.nbGrids = id;
            return true;
        } else if (id < 9) {
            this.difficulty = id - 6;
            return true;
        }
        return false;
    }
}
