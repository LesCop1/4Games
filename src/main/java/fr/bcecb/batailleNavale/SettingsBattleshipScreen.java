package fr.bcecb.batailleNavale;

import fr.bcecb.input.MouseButton;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.RulesPopUpScreen;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;

public class SettingsBattleshipScreen extends ScreenState {
    private Button rulesButton;
    private Button playButton;

    public SettingsBattleshipScreen(StateManager stateManager) {
        super(stateManager, "game_settings_battleship");
    }

    @Override
    public void initGui() {
        Text title = new Text(1, (width / 2f), (height / 3f), true, "Bataille Navale", 2f);

        this.rulesButton = new Button(2, (width / 2f) - (width / 5f), (height / 4f) + 2 * (height / 4f), (width / 5f), (height / 10f), true, "Règles");

        this.playButton = new Button(3, (width / 2f) + (width / 5f), (height / 4f) + 2 * (height / 4f), (width / 5f), (height / 10f), true, "Commencer");

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        addGuiElement(title, this.rulesButton, this.playButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == this.rulesButton.getId()) {
            String rules = "Dans une premier phase, les deux joueurs positionent leur bateaux sur la grille. Puis tour, a tour, chaque jour tire sur la grille afin de couler les bateaux adversaire. Le premier joueur ayant coulé tout les bateaux de l'adversaire se verra remporter la partie";
            stateManager.pushState(new RulesPopUpScreen(stateManager, "Bataille navale", rules, 200, 125));
            return true;
        } else if (id == this.playButton.getId()) {
            stateManager.pushState(new BattleshipScreen(stateManager));
            return true;
        }
        return false;
    }
}
