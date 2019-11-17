package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Resources;

public class AboutScreen extends ScreenState {
    public AboutScreen() {
        super("about_menu");
    }

    @Override
    public void initGui() {
        Button backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(backButton);
    }
}
