package fr.bcecb.batailleNavale;

import fr.bcecb.state.gui.ScreenState;

import java.util.ArrayList;
import java.util.List;

public class BatailleNavaleState extends ScreenState {
    private FirstPhaseBatailleNavaleState firstPhaseBatailleNavaleState;
    private Battleship battleship;
    private Boat boat;
    List<String> boardJ1 = firstPhaseBatailleNavaleState.getBoardJ1();
    List<String> boardJ2 = firstPhaseBatailleNavaleState.getBoardJ2();
    List<Boat> listBoatJ1 = firstPhaseBatailleNavaleState.getListBoatJ1();
    List<Boat> listBoatJ2 = firstPhaseBatailleNavaleState.getListBoatJ2();

    public BatailleNavaleState() {
        super("BatailleNavale");
        initGame();
        firstPhaseBatailleNavaleState.initFirstPhase();
        play();
    }

    private void initGame() {
        battleship.initBoard(boardJ1);
        battleship.initBoard(boardJ2);
    }

    private int clickGrid() {
        return 0; //Case de la grile
    }

    private void play() {
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

public class FirstPhaseBatailleNavaleState extends ScreenState {
    private Battleship battleship;
    private Boat boat;
    List<String> boardJ1 = new ArrayList<>();
    List<String> boardJ2 = new ArrayList<>();
    List<Boat> listBoatJ1 = new ArrayList<>();
    List<Boat> listBoatJ2 = new ArrayList<>();

    protected FirstPhaseBatailleNavaleState() {
        super("I don't know what to put");
        initFirstPhase();
    }

    protected void initFirstPhase() {
        for (Boat ship : Boat.values()) {
            if (wantChangeOrientation()) battleship.swapOrientation(ship);
            if (battleship.verification(clickGrid(), boardJ1, ship))
                battleship.putBoat(ship, boardJ1, listBoatJ1, clickGrid());
        }

        for (Boat ship : Boat.values()) {
            if (wantChangeOrientation()) battleship.swapOrientation(ship);
            if (battleship.verification(clickGrid(), boardJ2, ship))
                battleship.putBoat(ship, boardJ2, listBoatJ2, clickGrid());
        }
    }

    boolean wantChangeOrientation() {
        return true; //doit récupérer l'event d'un click sur les flèches
    }

    public List<String> getBoardJ1() {
        return boardJ1;
    }

    public List<String> getBoardJ2() {
        return boardJ2;
    }

    public List<Boat> getListBoatJ1() {
        return listBoatJ1;
    }

    public List<Boat> getListBoatJ2() {
        return listBoatJ2;
    }

    @Override
    public void initGui() {

    }
}
