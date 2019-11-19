package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Resources;

public class ProfileScreen extends ScreenState {
    public ProfileScreen() {
        super("profile_menu");
    }

    @Override
    public void initGui() {
        GuiElement profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Game.instance().currentProfile);

        GuiElement backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(profileButton, backButton);
    }
}
