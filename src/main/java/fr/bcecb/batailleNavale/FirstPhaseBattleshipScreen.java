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

import java.util.ArrayList;
import java.util.List;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private Battleship battleship;
    private Boat boat;

    private List<String> boatPut = new ArrayList<>();

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

                        if(event.getButton() == GLFW.GLFW_KEY_Z || event.getButton() == GLFW.GLFW_KEY_S) boat.setHorizontal(false);
                        if(event.getButton() == GLFW.GLFW_KEY_Q || event.getButton() == GLFW.GLFW_KEY_D) boat.setHorizontal(true);

                        if (selectedBoat != "" && !battleship.cannotPlace(boat, caseX, caseY)
                                && !boatPut.contains(selectedBoat)) {
                            boatPut.add(selectedBoat);
                            setTitle(selectedBoat);
                            battleship.putBoat(boat, caseX, caseY);
                            selectedBoat = "";
                        }
                    }

                    @Override
                    public boolean isDisabled() {
                        if(getTitle()!= ""){
                            return true;
                        }else{
                            return false;
                        }
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {
                        };
                    }
                };
                addGuiElement(caseButton);
            }
        }

        final GuiElement backButton = new Button(999, 0, 0, 50 / ((float) 1920 / width), 50 / ((float) 1920 / width), false, new ResourceHandle<>("textures/back_button.png") {
        }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateEngine().popState();
                Game.instance().getStateEngine().popState();
            }
        };
        addGuiElement(backButton);

        Button a = new Button(102, (width / 20f), 100, (height / 10f), (height / 10f), false, "A",
                new ResourceHandle<Texture>("textures/BatailleNavale/Aircraft-Carrier.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.AIRCRAFT_CARRIER);
            }
        };
        addGuiElement(a);

        Button b = new Button(103, (width / 20f), 200, (height / 10f), (height / 10f), false, "B",
                new ResourceHandle<Texture>("textures/BatailleNavale/Cruiser.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.CRUISER);
            }
        };
        addGuiElement(b);

        Button f = new Button(104, (width / 20f), 300, (height / 10f), (height / 10f), false, "F",
                new ResourceHandle<Texture>("textures/BatailleNavale/Frigate.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.FRIGATE);
            }
        };
        addGuiElement(f);

        Button s = new Button(105, (width / 20f), 400, (height / 10f), (height / 10f), false, "S",
                new ResourceHandle<Texture>("textures/BatailleNavale/Submarine.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.SUBMARINE);
            }
        };
        addGuiElement(s);

        Button t = new Button(106, (width / 20f), 500, (height / 10f), (height / 10f), false, "T",
                new ResourceHandle<Texture>("textures/BatailleNavale/Torpedo.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = getTitle();
                boat = new Boat(Boat.Type.TORPEDO);
            }
        };
        addGuiElement(t);
    }

    public void clickButton(int id, MouseEvent.Click event) {

    }
}
