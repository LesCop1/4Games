package fr.bcecb.state;

import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Resources;

public class ProfileScreen extends ScreenState {
    public ProfileScreen(StateManager stateManager) {
        super(stateManager, "profile_menu");
    }

    @Override
    public void initGui() {
        CircleButton profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Resources.CURRENT_PROFILE_TEXTURE);

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE);

        addGuiElement(profileButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        return false;
    }
}
