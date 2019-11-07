package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class ProfileScreen extends ScreenState {
    protected ProfileScreen() {
        super("profile_menu");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<Texture>("textures/mainMenuBG.png") {});

        GuiElement profileButton = new CircleButton(2, width / 2.0f, height / 2.0f, (height / 10f), true,
                new ResourceHandle<Texture>("textures/defaultProfile.png") {
        });

        GuiElement backButton = new Button(5, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f),
                (height / 10f), false, "Back", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton);
        addGuiElement(profileButton);
    }
}