package fr.bcecb.bingo;




import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bingo {
    private List<Integer> numberList = new ArrayList<>();
    private List<Player> bots = new ArrayList<>();
    private Player p = new Player();
    private final static int NB_BOTS = 5;

    public void init(int nbGrids) {
        p.init(nbGrids);
        int i = 1;
        while (i < 91) {
            numberList.add(i - 1, i);
            i++;
        }
        initBots(NB_BOTS);
    }

    public void initBots(int nbBots){
        Random rand = new Random();
        for (int i = 0; i < nbBots; i++) {
            Player bot = new Player();
            bot.init((rand.nextInt(5)+1));
            bots.add(bot);
        }
    }

    public int dropNumber() {
        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size() - 1 + 1) + 1;
        int number;
        number = numberList.get(randInt);
        numberList.remove(randInt);
        return number;
    }

    private class Player {

        private List<Grid> grids = new ArrayList<>();

        public void init(int nbGrid){
            for (int i = 0; i < nbGrid; i++) {
                addGrid();
            }
        }

        private void addGrid(){
            Grid g = new Grid();
            g.init();
            this.grids.add(g);
        }

        public void dispGrid(int index){
            this.grids.get(index).dispGrid();
        }
    }

    private class Grid {

        private final static int NB_ROWS = 3;
        private final static int NB_COLS = 9;
        private int[][] grid;

        public void init() {
            this.grid = new int[NB_ROWS][NB_COLS];
            fillGrid();
            holeInGrid();
        }

        private int randGen(int x) {
            Random rand = new Random();
            int randInt = rand.nextInt(10);
            if (x == 0) {
                randInt = rand.nextInt(8) + 1;
                return randInt;
            }
            if (x == 8) {
                randInt += (x * 10) + 1;
                return randInt;
            } else {
                randInt += (x * 10);
                return randInt;
            }
        }

        private boolean isInGrid(int n) {
            if (n == 0) {
                return true;
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    if (this.grid[i][j] == n) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void fillGrid() {
            int value = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    while (isInGrid(value)) {
                        value = randGen(j);
                    }
                    this.grid[i][j] = value;
                }
            }
        }

        private void holeInGrid(){
            Random rand = new Random();
            int compteur = 0;
            for (int i = 0; i < 3; i++) {
                while(compteur < 4) {
                    int randCol = rand.nextInt(8);
                    if(i == 0){
                        if(this.grid[i][randCol] != 0 && (this.grid[1][randCol] != 0 || this.grid[2][randCol] != 0)){
                            this.grid[i][randCol] = 0;
                            compteur ++;
                        }
                    }
                    if(i == 1){
                        if(this.grid[i][randCol] != 0 && (this.grid[0][randCol] != 0 || this.grid[2][randCol] != 0)){
                            this.grid[i][randCol] = 0;
                            compteur ++;
                        }
                    }
                    if(i == 2){
                        if(this.grid[i][randCol] != 0 && (this.grid[0][randCol] != 0 || this.grid[1][randCol] != 0)){
                            this.grid[i][randCol] = 0;
                            compteur ++;
                        }
                    }

                }
                compteur = 0;
            }


        }

        public void dispGrid() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    if (this.grid[i][j] == 0) {
                        System.out.print("X | ");
                    } else {
                        System.out.print(this.grid[i][j] + " | ");
                    }
                    if (j == 8){
                        System.out.println();
                    }
                }
            }
        }

    }

    public void dispList() {
        int i = 1;
        while (i < numberList.size()) {
            System.out.print(numberList.get(i - 1) + " ;");
            i++;
        }
        System.out.println("\n");
    }

    public void dispPlayerGrid(int index){
        p.dispGrid(index);
    }
}

