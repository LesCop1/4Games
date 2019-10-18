package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.input.MouseEvent;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Log;

public class MainMenuScreen extends ScreenState {
    public MainMenuScreen() {
        super("main_menu");

        addGuiElement(new Button(0, 0, 0, 100, 100) {
            @Override
            protected void onClick(MouseEvent.Click event) {
                Game.instance().getStateEngine().popState();
            }
        });

        addGuiElement(new Button(1, 150, 150, 500, 100) {
            @Override
            protected void onClick(MouseEvent.Click event) {
                Game.instance().getStateEngine().pushState(new GameSelectScreen());
            }
        });

        addGuiElement(new Button(2, 150, 300, 500, 100) {
            @Override
            protected void onClick(MouseEvent.Click event) {
                Game.instance().getStateEngine().pushState(new AboutScreen());
            }
        });
    }

    @Override
    public void onEnter() {
        super.onEnter();
        Log.GAME.info("Entered");
    }

    @Override
    public void onExit() {
        super.onExit();
        Log.GAME.info("Exited");
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean shouldRenderBelow() {
        return super.shouldRenderBelow();
    }

    @Override
    public boolean shouldUpdateBelow() {
        return super.shouldUpdateBelow();
    }
}
