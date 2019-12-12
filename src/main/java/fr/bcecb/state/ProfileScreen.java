package fr.bcecb.state;

import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Resources;

public class ProfileScreen extends ScreenState {
    public ProfileScreen(StateManager stateManager) {
        super(stateManager, "profile_menu");
    }

    @Override
    public void initGui() {
        CircleButton profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return Resources.CURRENT_PROFILE_TEXTURE;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }
        };

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        for (int i = 0; i < 2; i++) {
            for (int j = 0;j< 2;j++){
                float centerX, centerY, radius;
                radius = profileButton.getRadius();
                centerX = i * (width / 2f) + (width / 4f);
                centerY = j * (height / 2f) + (height / 4f);

                drawCircle(centerX, centerY, radius,i,j);

            }


        }

        addGuiElement(profileButton, backButton);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == BACK_BUTTON_ID) {
            stateManager.popState();
            return true;
        }
        return false;
    }

    public void drawCircle(float centerX, float centerY, float radius, int iCircle,int jCircle) {
        GuiElement score = new CircleImage(300 * iCircle + jCircle, Resources.DEFAULT_BUTTON_TEXTURE, centerX - radius, centerY - radius, radius);
        addGuiElement(score);

    }
}
