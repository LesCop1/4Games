package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.KeyboardEvent;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.input.Key;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import org.lwjgl.glfw.GLFW;
import fr.bcecb.resources.Texture;

import java.util.ArrayList;
import java.util.List;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private static final ResourceHandle<Texture> defaultTexture = new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {
    };

    private Battleship battleship;
    private Boat boat;

    private List<String> boatPut = new ArrayList<>();

    private int whichPlayer;
    private int[][] saveGridPlayer1;
    private int[][] saveGridPlayer2;


    private int selectedX = -1;
    private int selectedY = -1;

    private String selectedBoat = "";

    private String changeOrientation = "Horizontale";

    public FirstPhaseBattleshipScreen(Battleship battleship, int whichPlayer, int[][] gridPlayer1, int[][] gridPlayer2) {
        super("game-battleship.firstphase");
        this.battleship = battleship;
        this.whichPlayer = whichPlayer;
        saveGridPlayer1 = gridPlayer1;
        saveGridPlayer2 = gridPlayer2;
        setBackgroundTexture(new ResourceHandle<>("textures/battleshipScreen.png") {
        });
    }

    @Override
    public void initGui() {
        battleship.initGrid();
        setBackgroundTexture(new ResourceHandle<>("textures/BatailleNavale/battleshipScreen.png") {
        });
        Button caseButton;
        int id = 1;
        float btnSize = 14.99f;
        float x = (width / 2f) - (9 * btnSize / 2) - 4;
        for (int i = 0; i < 10; ++i, x += btnSize) {
            float y = (height / 2f) - (9 * btnSize / 2) - 4;
            for (int j = 0; j < 10; ++j, ++id, y += btnSize) {
                int caseX = i;
                int caseY = j;
                caseButton = new Button(id, x, y, btnSize, btnSize, false) {
                    @Override
                    public void onClick(MouseEvent.Click event) {
                        super.onClick(event);

                        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                            battleship.swapOrientation(boat);
                        }

                        if (selectedBoat != "" && !battleship.cannotPlace(boat, caseX, caseY)
                                && !boatPut.contains(selectedBoat)) {
                            boatPut.add(selectedBoat);
                            battleship.putBoat(boat, caseX, caseY);
                            selectedBoat = "";
                        }
                    }

                    @Override
                    public boolean isDisabled() {
                        if (boatPut.size() == 5) return true;
                        return battleship.getCurrentPlayerBoard()[caseX][caseY] != 0;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return battleship.getCurrentPlayerBoard()[caseX][caseY] != 0 ? whichTexture(battleship.getCurrentPlayerBoard()[caseX][caseY]) : defaultTexture;
                    }

                    public ResourceHandle<Texture> whichTexture(int num) {
                        if (num == 5) return new ResourceHandle<>("textures/BatailleNavale/A.png") {
                        };
                        if (num == 4) return new ResourceHandle<>("textures/BatailleNavale/C.png") {
                        };
                        if (num == 3) return new ResourceHandle<>("textures/BatailleNavale/F.png") {
                        };
                        if (num == 2) return new ResourceHandle<>("textures/BatailleNavale/S.png") {
                        };
                        if (num == 1) return new ResourceHandle<>("textures/BatailleNavale/T.png") {
                        };
                        return defaultTexture;
                    }
                };
                addGuiElement(caseButton);
            }
        }

        final GuiElement backButton = new Button(999, 0, 0, 50 / ((float) 1920 / width), 50 / ((float) 1920 / width), false, new ResourceHandle<>("textures/back_button.png") {
        }) {
            @Override
            //TODO I'm not sure too
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };
        addGuiElement(backButton);

        Button putFinish = new Button(107, 4.5f * (width / 5f), 50, (height / 10f), (height / 10f), false, "Joueur Suivant",
                new ResourceHandle<Texture>("textures/defaultButton.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                onExit();
            }

            @Override
            public boolean isVisible() {
                if (boatPut.size() == 5) return true;
                else return false;
            }
        };
        addGuiElement(putFinish);

//TODO Pixel Bauduin strat
        Button a = new Button(102, (width / 20f), 50, btnSize*Boat.Type.AIRCRAFT_CARRIER.getSize(), btnSize, false, "",
                new ResourceHandle<Texture>("textures/BatailleNavale/Aircraft_Carrier.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = "A";
                boat = new Boat(Boat.Type.AIRCRAFT_CARRIER);
            }

            @Override
            public boolean isVisible() {
                for (String val : boatPut){
                    if(val=="A") return false;
                }
                return true;
            }
        };
        addGuiElement(a);

        Button c = new Button(103, (width / 20f), 80, btnSize*Boat.Type.CRUISER.getSize(), btnSize, false, "",
                new ResourceHandle<Texture>("textures/BatailleNavale/Cruiser.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = "C";
                boat = new Boat(Boat.Type.CRUISER);
            }

            @Override
            public boolean isVisible() {
                for (String val : boatPut){
                    if(val=="C") return false;
                }
                return true;
            }
        };
        addGuiElement(c);

        Button f = new Button(104, (width / 20f), 110, btnSize*Boat.Type.FRIGATE.getSize(), btnSize, false, "",
                new ResourceHandle<Texture>("textures/BatailleNavale/Frigate.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = "F";
                boat = new Boat(Boat.Type.FRIGATE);
            }

            @Override
            public boolean isVisible() {
                for (String val : boatPut){
                    if(val=="F") return false;
                }
                return true;
            }
        };
        addGuiElement(f);

        Button s = new Button(105, (width / 20f), 140, btnSize*Boat.Type.SUBMARINE.getSize(), btnSize, false, "",
                new ResourceHandle<Texture>("textures/BatailleNavale/Submarine.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = "S";
                boat = new Boat(Boat.Type.SUBMARINE);
            }

            @Override
            public boolean isVisible() {
                for (String val : boatPut){
                    if(val=="S") return false;
                }
                return true;
            }
        };
        addGuiElement(s);

        Button t = new Button(106, (width / 20f), 170, btnSize*Boat.Type.TORPEDO.getSize(), btnSize, false, "",
                new ResourceHandle<Texture>("textures/BatailleNavale/Torpedo.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                selectedBoat = "T";
                boat = new Boat(Boat.Type.TORPEDO);
            }

            @Override
            public boolean isVisible() {
                for (String val : boatPut){
                    if(val=="T") return false;
                }
                return true;
            }
        };
        addGuiElement(t);
    }

    @Override
    public void onExit() {
        super.onExit();
        if (whichPlayer == 1) {
            Game.instance().getStateManager().pushState(new BattleshipScreen(battleship.getCurrentPlayerBoard(), saveGridPlayer2, 2));
        } else {
            Game.instance().getStateManager().pushState(new BattleshipScreen(saveGridPlayer1, battleship.getCurrentPlayerBoard(), 3));
        }
    }
}
