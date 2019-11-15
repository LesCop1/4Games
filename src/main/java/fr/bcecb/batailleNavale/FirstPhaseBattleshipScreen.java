package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import org.lwjgl.glfw.GLFW;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;
import org.joml.Vector4f;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private Battleship battleship;
    private Boat boat;
    private String name;

    private int selectedX = -1;
    private int selectedY = -1;

    private String selectedBoat = "";

    protected FirstPhaseBattleshipScreen(Battleship battleship) {
        super("game-battleship.firstphase");
        this.battleship = battleship;
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();

        setBackgroundTexture(new ResourceHandle<>("textures/BatailleNavale/background_battleship.jpg") {
        });

        Button caseButton;
        int id = 1;
        int btnSize = 42;
        float x = (width / 2) - (9 * btnSize / 2);
        for (int i = 0; i < 10; ++i, x += btnSize) {
            float y = (height / 2) - (9 * btnSize / 2);
            for (int j = 0; j < 10; ++j, ++id, y += btnSize) {
                int caseX = i;
                int caseY = j;
                caseButton = new Button(id, x, y, btnSize, btnSize, false, "") {
                    @Override
                    public void onClick(MouseEvent.Click event) {
                        super.onClick(event);

                        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                            battleship.getCurrentPlayerBoard()[caseX][caseY] = boat;
                        }

                        if (selectedBoat != "" && !battleship.cannotPlace(boat,caseX,caseY)) {
                            setTitle(selectedBoat);
                            battleship.putBoat(boat,caseX,caseY);
                            selectedBoat = "";
                        }
                    }

                    @Override
                    public boolean isDisabled() {
                        return getTitle() != "";
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {};
                    }
                };
                addGuiElement(caseButton);
            }
        }

        final GuiElement backButton = new Button(999, 0, 0, 50 / ((float) 1920 / width), 50 / ((float) 1920 / width), false, new ResourceHandle<>("textures/back_button.png") {
        });
        backButton.setClickHandler((id2, e) -> doubleBack());
        addGuiElement(backButton);

        Button a = new Button(102, (width / 20f), 100, (height / 10f), (height / 10f), false, "a",
                new ResourceHandle<Texture>("textures/BatailleNavale/a.png") {}) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.AIRCRAFT_CARRIER);
            }
        };
        addGuiElement(a);

        Button b = new Button(103, (width / 20f), 200, (height / 10f), (height / 10f), false, "b",
                new ResourceHandle<Texture>("textures/BatailleNavale/b.png") {}) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.CRUISER);
            }
        };
        addGuiElement(b);

        Button c = new Button(104, (width / 20f), 300, (height / 10f), (height / 10f), false, "c",
                new ResourceHandle<Texture>("textures/BatailleNavale/c.png") {}) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
            }
        };
        addGuiElement(c);

        Button d = new Button(105, (width / 20f), 400, (height / 10f), (height / 10f), false, "d",
                new ResourceHandle<Texture>("textures/BatailleNavale/d.png") {}) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
            }
        };
        addGuiElement(d);

        Button f = new Button(106, (width / 20f), 500, (height / 10f), (height / 10f), false, "f",
                new ResourceHandle<Texture>("textures/BatailleNavale/e.png") {}) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.TORPEDO);
            }
        };
        addGuiElement(f);
    }

    public void clickButton(int id, MouseEvent.Click event) {

    }

    public void doubleBack() {
        Game.instance().getStateEngine().popState();
        Game.instance().getStateEngine().popState();
    }
}
