package fr.bcecb.state;

import fr.bcecb.state.gui.*;
import fr.bcecb.util.Resources;

public class ProfileScreen extends ScreenState {
    public ProfileScreen(StateManager stateManager) {
        super(stateManager, "profile_menu");
    }

    @Override
    public void initGui() {
        CircleButton profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Resources.CURRENT_PROFILE_TEXTURE);

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE);

        for (int i = 0; i < 4; i++) {
            float centerX, centerY, radius;
            radius = 40;
            centerX = i % 4 * (width / 4f);
            centerY = (height / 2f);

            drawCircle(centerX, centerY, radius);
        }

        addGuiElement(profileButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        if (id == BACK_BUTTON_ID) {
            stateManager.popState();
            return true;
        }
        return false;
    }

    public void drawCircle(float centerX, float centerY, float radius) {
        GuiElement score = new CircleImage(300, Resources.DEFAULT_BACKGROUND_TEXTURE, centerX - radius, centerY - radius, radius);
        addGuiElement(score);

    }
}
