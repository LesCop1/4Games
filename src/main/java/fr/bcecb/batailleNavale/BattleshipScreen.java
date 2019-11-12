package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;


public class BattleshipScreen extends ScreenState {
    private Battleship battleship = new Battleship();

    public BattleshipScreen() {
        super("game-battleship");
    }

    @Override
    public void onEnter() {
        super.onEnter();
        Game.instance().getStateEngine().pushState(new FirstPhaseBattleshipScreen(battleship));
    }

    @Override
    public void initGui() {
        setBackgroundTexture(new ResourceHandle<>("textures/mainMenuBG.png") {});
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        GuiElement backButton = new Button(5, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f),
                (height / 10f), false, "Back", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler((id, e) -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton);
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldPauseBelow() {
        return true;
    }
}