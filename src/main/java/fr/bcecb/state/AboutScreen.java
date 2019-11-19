package fr.bcecb.state;

import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Resources;

public class AboutScreen extends ScreenState {
    public AboutScreen(StateManager stateManager) {
        super(stateManager, "about_menu");
    }

    @Override
    public void initGui() {
        GuiElement backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE);

        addGuiElement(backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        return false;
    }
}
