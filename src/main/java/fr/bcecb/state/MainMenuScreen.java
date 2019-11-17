package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;

public class MainMenuScreen extends ScreenState {
    public MainMenuScreen() {
        super("main_menu");
    }

    @Override
    public void initGui() {
        //Image logoImage = new Image(100, Resources.DEFAULT_LOGO_TEXTURE, (width / 2f), (height / 8f), 50, 50, true, true);

        Button playButton = new Button(1, (width / 2f), (height / 2f) - (height / 10f) - (height / 10f), 50, 25, true, "Play", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new GameSelectScreen());
            }
        };

        CircleButton profileButton = new CircleButton(2, width / 2.0f, height / 2.0f, (height / 10f), true, Constants.CURRENT_PROFILE_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new ProfileScreen());
            }
        };

        Button achievementButton = new Button(3, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Success", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
            }
        };

        Button shopButton = new Button(4, (width / 2f) + 25, (height / 2f) + (height / 7f), (width / 5f), (height / 10f), false, "Shop", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
            }
        };

        Button aboutButton = new Button(5, (width / 2f) - (width / 5f) - 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "About", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().pushState(new AboutScreen());
            }
        };

        Button quitButton = new Button(-1, (width / 2f) + 25, (height / 2f) + (height / 3.5f), (width / 5f), (height / 10f), false, "Quit", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        RoundedButton te = new RoundedButton(12, 10, 10, 100, 100, 10, null);
        RoundedImage ae = new RoundedImage(524, new ResourceHandle<>("textures/defaultBackground.png") {
        }, 100, 100, 10, 100, true, false, 5f);

        addGuiElement(te, ae, playButton, profileButton, achievementButton, shopButton, aboutButton, quitButton);
    }
}
