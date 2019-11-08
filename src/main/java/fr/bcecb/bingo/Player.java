package fr.bcecb.bingo;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Grid> grids = new ArrayList<>();
    private boolean win;

    public Player(int nbGrid){
        this.win = false;
        for (int i = 0; i < nbGrid; i++) {
            addGrid();
        }
    }

    private void checkWin(){
        for (Grid g :
                grids) {
            if (g.checkWin()) this.win = true;
        }
    }

    private void addGrid(){
        Grid g = new Grid();
        this.grids.add(g);
    }

    public List<Grid> getGrids() {
        return grids;
    }

    public void setGrids(List<Grid> grids) {
        this.grids = grids;
    }
}
