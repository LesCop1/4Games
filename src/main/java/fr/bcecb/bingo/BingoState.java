package fr.bcecb.bingo;

import fr.bcecb.state.gui.ScreenState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BingoState extends ScreenState {
    private int nbGrids;
    private List<Integer> numberList = new ArrayList<>();
    private List<Player> bots = new ArrayList<>();
    private final static int NB_BOTS = 5;
    private Player player;
    private int lastDrop;

    public BingoState(int nbGrids) {
        super("bingo");
        this.nbGrids = nbGrids;
        initGame();
        createBots();
    }

    private void initGame(){
        player = new Player(nbGrids);
        int i = 1;
        while (i < 91) {
            numberList.add(i - 1, i);
            i++;
        }
    }

    private void createBots(){
        Random rand = new Random();
        for (int j = 0; j < NB_BOTS; j++) {
            Player bot = new Player((rand.nextInt(5)+1));
            bots.add(bot);
        }
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
        dropball();
    }

    @Override
    public void initGui() {

    }

    private void dropball(){
        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size() - 1 + 1) + 1;
        numberList.get(randInt);
        numberList.remove(randInt);
        this.lastDrop = randInt;
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
