package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.Image;
import fr.bcecb.state.gui.ScreenState;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private Battleship battleship;
    private Boat boat;

    protected FirstPhaseBattleshipScreen(Battleship battleship) {
        super("game-battleship.firstphase");
        this.battleship = battleship;
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();

        setBackgroundTexture(new ResourceHandle<Texture>("textures/background_battleship.jpg") {
        });
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                GuiElement cases = new Button((10 * i) + j, ((float) width - 80 / ((float) 1920 / width) * 10) / 2 + i * 80 / ((float) 1920 / width), ((float) height - 80 / ((float) 1920 / width) * 10) / 8 + j * 80 / ((float) 1920 / width), (80 / ((float) 1920 / width)), (80 / ((float) 1920 / width)),
                        false, "" /*+ j + i*/, new ResourceHandle<Texture>("textures/caseBattleship.png") {
                });
                cases.setClickHandler(e -> clickCases(e, cases.getId()));
                addGuiElement(cases);
            }
        }

        setBackgroundTexture(new ResourceHandle<>("textures/battleshipScreen.png") {});
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                GuiElement cases = new Button((10 * i) + j, i * 80 + (width / 3.43f), j * 80 + 100, 80, 80,
                        false, "" + j + i, new ResourceHandle<>("textures/caseBattleship.png") {
                });
                cases.setClickHandler((id, e) -> clickButton(cases.getId(), e));
                addGuiElement(cases);
            }
        }
        final GuiElement swap = new Button(101, 2.9f * (width / 4.0f), (height - (height / 20f) - (height / 10f)), 500,
                100,
                false, "Changer l'orientation", new ResourceHandle<>("textures/defaultButton.png") {});
        swap.setClickHandler((id, e) -> clickButton(swap.getId(), e));
        addGuiElement(swap);

        final GuiElement backButton = new Button(102, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f),
                (height / 10f), false, "Back", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler((id, e) -> doubleBack());
        addGuiElement(backButton);


        final Button a = new Button(102, (width / 20f), 100, (height / 10f),
                (height / 10f), false, "AIRCRAFT_CARRIER", new ResourceHandle<Texture>("textures/a.png") {
        });
        a.setClickHandler(e -> whichBoat(a.getTitle()));
        addGuiElement(a);
    }

    public void clickCases(MouseEvent.Click event, int id) {}

    public void clickButton(int id, MouseEvent.Click event) {
//        battleship.swapOrientation();

    }

    public void doubleBack() {
        Game.instance().getStateEngine().popState();
        Game.instance().getStateEngine().popState();
    }

    public void whichBoat(String BoatName) {
        boat = new Boat(Boat.Type.AIRCRAFT_CARRIER);
    }
}
