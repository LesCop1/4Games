package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Constants;

public class ProfileScreen extends ScreenState {
    protected ProfileScreen() {
        super("profile_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(Constants.DEFAULT_BACKGROUND_TEXTURE);

        final GuiElement profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, Game.instance().currentProfile);

        final GuiElement backButton = new Button(10, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Constants.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(profileButton, backButton);
    }
}
