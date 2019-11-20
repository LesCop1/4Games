package fr.bcecb.bingo;

import java.util.ArrayList;
import java.util.Random;

public class Bingo {
    private Random random = new Random();

    private int nbGrids;

    private Player player;
    private ArrayList<Integer> numberList = new ArrayList();

    public Bingo(int nbGrids) {
        this.nbGrids = nbGrids;
    }

    public void init() {
        this.player = new Player(this.nbGrids);
        for (int i = 1; i < 91; i++) {
            this.numberList.add(i);
        }
    }

    public int dropball() {
        int randInt = this.random.nextInt(numberList.size() - 1);

        int droppedBall = this.numberList.get(randInt);
        this.numberList.remove(randInt);
        return droppedBall;
    }

    public int getNbGrids() {
        return nbGrids;
    }

    public Player getPlayer() {
        return player;
    }
}
