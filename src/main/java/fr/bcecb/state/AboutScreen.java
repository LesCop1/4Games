package fr.bcecb.state;

import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class AboutScreen extends ScreenState {
    public AboutScreen(StateManager stateManager) {
        super(stateManager, "about_menu");
    }

    @Override
    public void initGui() {
        GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        addGuiElement(backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        if (id == BACK_BUTTON_ID) {
            stateManager.popState();
            return true;
        }
        return false;
    }
}
