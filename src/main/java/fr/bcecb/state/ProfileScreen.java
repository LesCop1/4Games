package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.CircleButton;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Resources;

public class ProfileScreen extends ScreenState {
    private static final ResourceHandle<Texture> CIRCLE_TEXTURE = new ResourceHandle<>("textures/defaultBackground.png") {};


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

        for (int i = 0; i < 4; i++) {
            float centerX,centerY,radius;
            radius = 40;
            centerX = i%4 * (width/4f) ;
            centerY = (height/2f) ;

            drawCircle(centerX,centerY,radius);

        }

        addGuiElement(profileButton, backButton);
    }

    public void drawCircle(float centerX, float centerY,float radius ){

        GuiElement score = new CircleImage(300,CIRCLE_TEXTURE,centerX-radius,centerY-radius,radius);
        addGuiElement(score);

    }
}
