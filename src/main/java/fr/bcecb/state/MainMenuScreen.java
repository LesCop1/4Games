package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Resources;

public class MainMenuScreen extends ScreenState {
    public MainMenuScreen() {
        super("main_menu");
    }

    @Override
    public void initGui() {
        GuiElement logoImage = new Image(0, Resources.DEFAULT_LOGO_TEXTURE, (width / 2f), (height / 8f), 50, 50, true, true);

        GuiElement playButton = new Button(10, (width / 2f), (height / 2f) - (height / 10f) - (height / 10f), 50, 25, true, "Play", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new GameSelectScreen());
            }
        };

        //TODO
        GuiElement profileButton = new CircleButton(20, width / 2.0f, height / 2.0f, (height / 10f), true, null) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new ProfileScreen());
            }
        };

        final GuiElement achievementButton = new Button(11, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Success", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
            }
        };

        final GuiElement shopButton = new Button(12, (width / 2f) + 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Shop", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
            }
        };

        final GuiElement aboutButton = new Button(13, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "About", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new AboutScreen());
            }
        };

        final GuiElement quitButton = new Button(14, (width / 2f) + 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "Quit", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(logoImage, playButton, profileButton, achievementButton, shopButton, aboutButton, quitButton);
    }
}
