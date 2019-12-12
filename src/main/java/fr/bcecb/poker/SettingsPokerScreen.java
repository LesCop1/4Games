package fr.bcecb.poker;

import fr.bcecb.input.MouseButton;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.RulesPopUpScreen;
import fr.bcecb.state.gui.ScreenState;

public class SettingsPokerScreen extends ScreenState {
    private Button rulesButton;

    public SettingsPokerScreen(StateManager stateManager) {
        super(stateManager, "settings_poker");
    }

    @Override
    public void initGui() {

        for (int i = 0; i < 3; i++) {
            Button xPlayers = new Button(i, (width / 4f) + ((width / 4f) * i), (height / 2f) - (height / 10f), (width / 8f), (height / 10f), true, (i + 2) + "Players");
            addGuiElement(xPlayers);
        }

        this.rulesButton = new Button(10, (width / 2f), (height / 2f) + (height / 3f), (width / 5f), (height / 10f), true, "Règles");

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");
        addGuiElement(rulesButton, backButton);
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
        } else if (id == this.rulesButton.getId()) {
            String rules = "Lorsqu'une partie de poker débute, chaque joueur aura un solde de départ équivalent. A chaque tour, les joueurs pourront miser pour défendre leur main. Pour qu'un joueur gagne un manche, il faut qu'il soit le dernier en jeu ou qu'il est la meilleur main de tous. Lorsqu'un joueur gagne, il remporte la somme des mises. La partie se termine lorsqu'un joueur recupère l'ensemble des soldes de chaque joueur.";
            stateManager.pushState(new RulesPopUpScreen(stateManager, "Poker", rules, 200, 160));
            return true;
        }
        return false;
    }
}
