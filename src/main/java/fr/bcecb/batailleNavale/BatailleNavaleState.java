package fr.bcecb.batailleNavale;

import fr.bcecb.state.gui.ScreenState;

import java.util.ArrayList;
import java.util.List;


public class BatailleNavaleState extends ScreenState {
    private Battleship battleship;
    private Boat boat;
    List<String> boardJ1 = new ArrayList<>();
    List<String> boardJ2 = new ArrayList<>();
    List<Boat> listBoatJ1 = new ArrayList<>();
    List<Boat> listBoatJ2 = new ArrayList<>();

    public BatailleNavaleState() {
        super("BatailleNavale");
        initGame();
        play();
    }

    private void initGame() {
        battleship.initBoard(boardJ1);
        for (Boat ship : Boat.values()) {
            if(wantChangeOrientation()) battleship.swapOrientation(ship);
            if(battleship.verification(clickGrid(),boardJ1,ship)) battleship.putBoat(ship, boardJ1, listBoatJ1, clickGrid());
        }
        battleship.initBoard(boardJ2);
        for (Boat ship : Boat.values()) {
            if(wantChangeOrientation()) battleship.swapOrientation(ship);
            if(battleship.verification(clickGrid(),boardJ2,ship)) battleship.putBoat(ship, boardJ2, listBoatJ2, clickGrid());
        }
    }

    private int clickGrid() {
        return 0; //Case de la grile
    }

    private boolean wantChangeOrientation(){
        return true; //Récupère l'event d'un click sur les flèches
    }

    private void play(){
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void initGui() {
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldUpdateBelow() {
        return false;
    }
}
