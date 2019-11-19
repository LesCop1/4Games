package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BattleshipScreen extends ScreenState {
    private static final ResourceHandle<Texture> defaultTexture = new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {
    };
    private static final ResourceHandle<Texture> sink = new ResourceHandle<>("textures/BatailleNavale/sink.png") {
    };
    private static final ResourceHandle<Texture> touch = new ResourceHandle<>("textures/BatailleNavale/touch.png") {
    };
    private Battleship battleship = new Battleship();
    private Boat boat;
    private int[][] gridPlayer1;
    private int[][] gridPlayer2;
    private int[][] gridTemp;
    private int whichPlayer = 1;
    private boolean shoot = false;
    
    private Map<Integer, Boat> hm = new HashMap<Integer, Boat>();

    public BattleshipScreen() {
        super("game-battleship");
    }

    public BattleshipScreen(int[][] grid1, int[][] grid2, int whichPlayer) {
        super("game-battleship");
        gridPlayer1 = grid1;
        gridPlayer2 = grid2;
        this.whichPlayer = whichPlayer;
    }

    @Override
    public void onEnter() {
        super.onEnter();
        if (whichPlayer < 3)
            Game.instance().getStateManager().pushState(new FirstPhaseBattleshipScreen(battleship, whichPlayer, gridPlayer1, gridPlayer2));
        else {
            whichPlayer-=2; //On repasse au joueur 1 quand tous les bateaux sont placés
            gridTemp=gridPlayer2;
        }
    }

    @Override
    public void initGui() {
        hm.put(5, new Boat(Boat.Type.AIRCRAFT_CARRIER));
        hm.put(4, new Boat(Boat.Type.CRUISER));
        hm.put(3, new Boat(Boat.Type.FRIGATE));
        hm.put(2, new Boat(Boat.Type.SUBMARINE));
        hm.put(1, new Boat(Boat.Type.TORPEDO));
        setBackgroundTexture(new ResourceHandle<>("textures/BatailleNavale/background_battleship.jpg") {
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

                        if (!shoot && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                            boat = hm.get(gridTemp[caseX][caseY]);
                            if (battleship.shoot(boat, gridTemp, caseX, caseY))
                                gridTemp[caseX][caseY] = 100; //Touché
                            else gridTemp[caseX][caseY] = 200; //Coulé
                            if(battleship.checkWinCondition(gridTemp)) System.out.println("ggwp"); //TODO Jolie fenêtre pour dire kiki gagne et bloquer tout le reste
                            shoot = true;
                        }
                    }

                    @Override
                    public boolean isDisabled() {
                        if(shoot) return true;
                        return gridTemp[caseX][caseY] > 5;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return gridTemp[caseX][caseY] > 5 ? changeTexture() : defaultTexture;
                    }

                    public ResourceHandle<Texture> changeTexture() {
                        return gridTemp[caseX][caseY] == 200 ? sink : touch;
                    }
                };
                addGuiElement(caseButton);
            }
        }

        Button changePlayer = new Button(102, (width / 20f), 50, (height / 10f), (height / 10f), false, "Joueur Suivant",
                new ResourceHandle<Texture>("textures/defaultButton.png") {
                }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                super.onClick(event);
                if(whichPlayer==1){
                    gridPlayer2=gridTemp;
                    gridTemp=gridPlayer1;
                    whichPlayer++;
                }else{
                    gridPlayer1=gridTemp;
                    gridTemp=gridPlayer2;
                    whichPlayer--;
                }
                shoot = false;
            }

            @Override
            public boolean isVisible() {
                if (shoot==true) return true;
                else return false;
            }
        };
        addGuiElement(changePlayer);

        final GuiElement backButton = new Button(999, 0, 0, 50 / ((float) 1920 / width), 50 / ((float) 1920 / width), false, new ResourceHandle<>("textures/back_button.png") {
        }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                //TODO This shit doesn't work very well
                Game.instance().getStateManager().popState();
            }
        };
        addGuiElement(backButton);
    }
}