package fr.bcecb.bingo;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Grid> grids = new ArrayList<>();

    public Player(int nbGrid) {
        for (int i = 0; i < nbGrid; i++) {
            addGrid();
        }
    }

    public boolean checkWin() {
        for (Grid g :
                grids) {
            if (g.checkWin()) return true;
        }
        return false;
    }

    private void addGrid() {
        Grid g = new Grid();
        this.grids.add(g);
    }

    public Grid getGrid(int numGrid) {
        return this.grids.get(numGrid);
    }
}
