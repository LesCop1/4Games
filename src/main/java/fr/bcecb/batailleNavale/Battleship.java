package fr.bcecb.batailleNavale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battleship { //Gère tous les aspects d'une partie, création de la grille, changer l'orientation d'un bateau, le placer, touché/coulé, win condition
    public static final int GRID_SIZE = 10;
    public static final int DEFAULT_VALUE = -1;
    public static final int SUCCESS_HIT = 100;
    public static final int FAILED_HIT = 200;
    private final int[][][] boards = new int[2][GRID_SIZE][GRID_SIZE];
    protected List<Boolean> hitGridJ1 = new ArrayList(Arrays.asList(false, false, false, false, false));
    protected List<Boolean> hitGridJ2 = new ArrayList(Arrays.asList(false, false, false, false, false));

    public int hit(int currentPlayer) {
        int count = 0;
        if (currentPlayer == 0) {
            for (Boolean b : hitGridJ1) {
                if (b) count++;
            }
        } else {
            for (Boolean b : hitGridJ2) {
                if (b) count++;
            }
        }
        return count;
    }

    public void init() {
        for (int i = 0; i < boards.length; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                for (int k = 0; k < GRID_SIZE; k++) {
                    this.boards[i][j][k] = DEFAULT_VALUE;
                }
            }
        }
    }

    public void putBoat(int currentPlayer, Boat boat, int x, int y) {
        if (cannotPlace(currentPlayer, boat, x, y)) return;
        if (boat.isHorizontal()) {
            boat.setPosition(x, y);
            for (int i = 0; i < boat.getSize(); i++) {
                getPlayerGrid(currentPlayer)[x + i][y] = boat.getType().ordinal();
            }
        } else {
            boat.setPosition(x, y);
            for (int i = 0; i < boat.getSize(); i++) {
                getPlayerGrid(currentPlayer)[x][y + i] = boat.getType().ordinal();
            }
        }
    }

    public void deleteBoat(int currentPlayer, int id) {
        int[][] playerGrid = getPlayerGrid(currentPlayer);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if(playerGrid[i][j]==id) playerGrid[i][j] = DEFAULT_VALUE;
            }
        }
    }

    public boolean cannotPlace(int currentPlayer, Boat boat, int x, int y) {
        if (boat.isHorizontal()) {
            for (int i = 0; i < boat.getSize(); i++) {
                if (x + i >= GRID_SIZE || getPlayerGrid(currentPlayer)[x + i][y] != DEFAULT_VALUE) return true;
            }
        } else {
            for (int i = 0; i < boat.getSize(); i++) {
                if (y + i >= GRID_SIZE || getPlayerGrid(currentPlayer)[x][y + i] != DEFAULT_VALUE) return true;
            }
        }
        return false;
    }

    public void swapOrientation(Boat boat) {
        if (boat.isHorizontal()) boat.setHorizontal(false);
        else boat.setHorizontal(true);
    }

    public void shoot(int currentPlayer, int x, int y) {
        int[][] playerGrid = getPlayerGrid(currentPlayer);
        if (0 <= playerGrid[x][y] && playerGrid[x][y] <= 4) {
            playerGrid[x][y] = SUCCESS_HIT;
        } else {
            playerGrid[x][y] = FAILED_HIT;
        }
    }

    public boolean checkWinCondition(int currentPlayer) {
        int[][] playerGrid = getPlayerGrid(currentPlayer);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (0 <= playerGrid[i][j] && playerGrid[i][j] <= 4) return false;
            }
        }
        return true;
    }

    public int[][] getPlayerGrid(int player) {
        return this.boards[player];
    }

    public int[][] getNextPlayerGrid(int player) {
        if (player == 0) return this.boards[1];
        else return this.boards[0];
    }

    public int countBoat(int currentPlayer) {
        int[][] playerGrid = getPlayerGrid(currentPlayer);
        int nbT = 0, nbS = 0, nbF = 0, nbC = 0, nbA = 0, res = 5;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (playerGrid[i][j] == 0) nbT++;
                if (playerGrid[i][j] == 1) nbS++;
                if (playerGrid[i][j] == 2) nbF++;
                if (playerGrid[i][j] == 3) nbC++;
                if (playerGrid[i][j] == 4) nbA++;
            }
        }
        if (nbT == 0) res--;
        if (nbS == 0) res--;
        if (nbF == 0) res--;
        if (nbC == 0) res--;
        if (nbA == 0) res--;
        return res;
    }
}
