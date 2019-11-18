package fr.bcecb.bingo;

import java.util.Random;

public class Grid {

    private final static int NB_ROWS = 3;
    private final static int NB_COLS = 9;
    private int[][] grid;

    public Grid() {
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

    private void holeInGrid() {
        Random rand = new Random();
        int compteur = 0;
        for (int i = 0; i < 3; i++) {
            while (compteur < 4) {
                int randCol = rand.nextInt(8);
                if (i == 0) {
                    if (this.grid[i][randCol] != -1 && (this.grid[1][randCol] != -1 || this.grid[2][randCol] != -1)) {
                        this.grid[i][randCol] = -1;
                        compteur++;
                    }
                }
                if (i == 1) {
                    if (this.grid[i][randCol] != -1 && (this.grid[0][randCol] != -1 || this.grid[2][randCol] != -1)) {
                        this.grid[i][randCol] = -1;
                        compteur++;
                    }
                }
                if (i == 2) {
                    if (this.grid[i][randCol] != -1 && (this.grid[0][randCol] != -1 || this.grid[1][randCol] != -1)) {
                        this.grid[i][randCol] = -1;
                        compteur++;
                    }
                }
            }
            compteur = 0;
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

    public boolean checkWin() {
        int compteur = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.grid[i][j] == 0) {
                    compteur++;
                }
            }
        }
        return compteur == 24;
    }

    public void dispGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.grid[i][j] == 0) {
                    System.out.print("X | ");
                } else {
                    System.out.print(this.grid[i][j] + " | ");
                }
                if (j == 8) {
                    System.out.println();
                }
            }
        }
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getValue(int x, int y) {
        return this.grid[x][y];
    }
}
