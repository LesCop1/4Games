package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private Battleship battleship;

    private Boat boat;

    protected FirstPhaseBattleshipScreen(Battleship battleship) {
        super("game-battleship.firstphase");
        this.battleship = battleship;

        setBackgroundTexture(new ResourceHandle<>("textures/battleshipScreen.png") {});
    }

    @Override
    public void initGui() {

        int id = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                GuiElement caseButton = new Button(++id, i * 80 + (width / 3.43f), j * 80 + 100, 80, 80, false, String.valueOf(id), new ResourceHandle<>("textures/caseBattleship.png") {}) {
                    @Override
                    public void onClick(MouseEvent.Click event) {
                        clickButton(getId(), event);
                    }
                };
                addGuiElement(caseButton);
            }
        }

        GuiElement backButton = new Button(102, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", new ResourceHandle<>("textures/defaultButton.png") {}) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
                Game.instance().getStateManager().popState();
            }
        };
        addGuiElement(backButton);
    }

    public void clickButton(int id, MouseEvent.Click event) {
//        battleship.swapOrientation();
    }
}
