package fr.bcecb.batailleNavale;

public class Battleship { //Gère tous les aspects d'une partie, création de la grille, changer l'orientation d'un bateau, le placer, touché/coulé, win condition
    private final int[][][] boards = new int[2][10][10];
    private int currentPlayer = 0;

    public int[][] getCurrentPlayerBoard() {
        return boards[currentPlayer];
    }

    public int[][] getNextPlayerBoard() {
        return boards[getNextPlayer()];
    }

    public void initGrid(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                getCurrentPlayerBoard()[i][j]=0;
            }
        }
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNextPlayer() {
        return (this.currentPlayer + 1) % 2;
    }

    public void putBoat(Boat boat, int x, int y) { //Place les bateaux
        if (cannotPlace(boat, x, y)) return;

        if (boat.isHorizontal()) {
            while (x + boat.getSize() >= 10) { //Si on veut placer un bateau sur une case qui fait que le bateau dépasse à gauche
                --x; //On le décale vers la droite
            }

            boat.setPosition(x, y);

            for (int i = 0; i < boat.getSize(); i++) {
                getCurrentPlayerBoard()[x + i][y] = boat.getType().getId();
            }
        } else {
            while (y + boat.getSize() >= 10) { //Pareil que pour x mais en vertical
                --y;
            }

            boat.setPosition(x, y);

            for (int i = 0; i < boat.getSize(); i++) {
                getCurrentPlayerBoard()[x][y + i] = boat.getType().getId();
            }
        }
    }

    public boolean cannotPlace(Boat boat, int x, int y) {
        if (boat.isHorizontal()) {
            for (int i = 0; i < boat.getSize(); i++) {
                if (x + i >= 10 || getCurrentPlayerBoard()[x + i][y] != 0) return true;
            }
        } else {
            for (int i = 0; i < boat.getSize(); i++) {
                if (y + i >= 10 || getCurrentPlayerBoard()[x][y + i] != 0) return true;
            }
        }
        return false;
    }

    public void swapOrientation(Boat boat) { //Change l'orientation du bateau passé en paramètre
        if (boat.isHorizontal()) boat.setHorizontal(false);
        else boat.setHorizontal(true);
    }

//    public boolean shoot(int x, int y) {
//        Boat boat = getNextPlayerBoard()[x][y];
//        if (boat == null) return false;
//        else {
//            int hitPosition = boat.isHorizontal() ? boat.getX() - x : boat.getY() - y;
//            if (boat.getHits()[hitPosition]) return false;
//            else boat.hit(hitPosition);
//        }
//        int hitPosition = boat.isHorizontal() ? boat.getX() - x : boat.getY() - y;
//        if (boat.getHits()[hitPosition]) return false;
//        else boat.hit(hitPosition);
//        return true;
//    }
}
