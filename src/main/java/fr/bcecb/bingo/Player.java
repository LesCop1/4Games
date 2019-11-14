package fr.bcecb.bingo;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Grid> grids = new ArrayList<>();
    private boolean win;

    public Player(int nbGrid) {
        this.win = false;
        for (int i = 0; i < nbGrid; i++) {
            addGrid();
        }
    }

    public boolean checkWin() {
        for (Grid g :
                grids) {
            if (g.checkWin()) this.win = true;
        }
        return this.win;
    }

    private void addGrid() {
        Grid g = new Grid();
        this.grids.add(g);
    }

    public List<Grid> getGrids() {
        return grids;
    }

    public Grid getGrid(int numGrid) {
        return this.grids.get(numGrid);
    }
}
