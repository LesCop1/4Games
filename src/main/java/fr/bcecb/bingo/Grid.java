package fr.bcecb.bingo;

import java.util.Random;

public class Grid {
    private static final Random RANDOM = new Random();

    private static final int NB_ROWS = 3;
    private static final int NB_COLS = 9;
    private int[][] grid;

    public Grid() {
        this.grid = new int[NB_ROWS][NB_COLS];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int value;

                do {
                    value = randGen(j);
                } while (isInGrid(value));

                grid[i][j] = value;
            }
        }

        holeInGrid();
    }

    public boolean isComplete() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] > 0) return false;
            }
        }

        return true;
    }

    private int randGen(int x) {
        int randInt = RANDOM.nextInt(10);
        if (x == 0) {
            randInt = RANDOM.nextInt(8) + 1;
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

    private void holeInGrid() {
        int compteur = 0;
        for (int i = 0; i < 3; i++) {
            while (compteur < 4) {
                int randCol = RANDOM.nextInt(8);
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


    public boolean isInGrid(int n) {
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

    public int[][] getGrid() {
        return grid;
    }

    public void setValue(int x, int y, int value) {
        this.grid[x][y] = value;
    }

    public int getValue(int x, int y) {
        return this.grid[x][y];
    }
}

