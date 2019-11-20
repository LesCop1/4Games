package fr.bcecb.batailleNavale;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private Battleship battleship;

    private Boat boat;

    protected FirstPhaseBattleshipScreen(Battleship battleship) {
        super(null, "game-battleship.firstphase");
        this.battleship = battleship;

        setBackgroundTexture(new ResourceHandle<>("textures/battleshipScreen.png") {});
    }

    @Override
    public void initGui() {

        GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");
        addGuiElement(backButton);
    }

    @Override
    public boolean mouseClicked(int id) {
        return false;
    }
}
