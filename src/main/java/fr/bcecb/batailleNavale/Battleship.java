package fr.bcecb.batailleNavale;

public class Battleship { //Gère tous les aspects d'une partie, création de la grille, changer l'orientation d'un bateau, le placer, touché/coulé, win condition
    public static final int GRID_SIZE = 10;
    public static final int DEFAULT_VALUE = -1;
    public static final int SUCCESS_HIT = 100;
    public static final int FAILED_HIT = 200;

    private final int[][][] boards = new int[2][GRID_SIZE][GRID_SIZE];

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
}
