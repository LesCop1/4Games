package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Constants;

public class AboutScreen extends ScreenState {
    protected AboutScreen() {
        super("about_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(Constants.DEFAULT_BACKGROUND_TEXTURE);

        final GuiElement backButton = new Button(10, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Constants.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton);
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldUpdateBelow() {
        return false;
    }
}
