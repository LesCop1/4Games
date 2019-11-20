package fr.bcecb.bingo;

public class Player {
    private final Grid[] grids;

    public Player(int nbGrid) {
        this.grids = new Grid[nbGrid];

        for (int i = 0; i < nbGrid; i++) {
            this.grids[i] = new Grid();
        }
    }

    public boolean checkWin() {
        for (Grid grid : this.grids) {
            if (grid.isComplete()) return true;
        }

        return false;
    }

    public Grid getGrid(int i) {
        return this.grids[i];
    }

    public int getValue(int i, int x, int y) {
        return this.grids[i].getValue(x, y);
    }

    public void setValue(int i, int x, int y, int value) {
        this.grids[i].setValue(x, y, value);
    }
}
