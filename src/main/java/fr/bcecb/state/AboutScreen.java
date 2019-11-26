package fr.bcecb.state;

import fr.bcecb.input.MouseButton;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;

public class AboutScreen extends ScreenState {
    public AboutScreen(StateManager stateManager) {
        super(stateManager, "about_menu");
    }

    @Override
    public void initGui() {
        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        Text title = new Text(1, width / 2f, height / 5f, true, "4Games", 2f);
        Text text1 = new Text(2, (width / 7f), (height / 4f) + 10f, false, "4Games est un jeu développé dans un but éducatif, possédant 4 jeux.", 0.75f);
        Text text2 = new Text(3, (width / 7f), (height / 4f) + 20f, false, "Il y a 4 jeux : Poker, Sudoku, Bingo et Bataille Navale.", 0.75f);
        Text text3 = new Text(4, (width / 7f), (height / 4f) + 30f, false, "4Games a été conçu en 11 semaines. De l'idée au produit final.", 0.75f);

        Text text4 = new Text(5, (width / 2f), (height / 4f) + 50f, true, "Développeurs :", 1.25f);
        Text text5 = new Text(6, (width / 2f), (height / 4f) + 70f, true, "Mathis ENGELS", 0.75f);
        Text text6 = new Text(7, (width / 2f), (height / 4f) + 80f, true, "Antoine BALIEU", 0.75f);
        Text text7 = new Text(8, (width / 2f), (height / 4f) + 90f, true, "Thomas BAUDUIN", 0.75f);
        Text text8 = new Text(9, (width / 2f), (height / 4f) + 100f, true, "Flavien CARPENTIER", 0.75f);
        Text text9 = new Text(10, (width / 2f), (height / 4f) + 110f, true, "Quentin CARRY", 0.75f);
        addGuiElement(backButton, title, text1, text2, text3, text4, text5, text6, text7, text8, text9);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        return false;
    }
}
