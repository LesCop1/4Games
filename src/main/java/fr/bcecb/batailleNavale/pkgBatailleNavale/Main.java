package fr.bcecb.batailleNavale.pkgBatailleNavale;

public class Main {
    public static void main(String[] args) {
        Battleship g = new Battleship();

        g.initBoard();

        g.swapOrientation(Boat.C);
        g.swapOrientation(Boat.F);
        g.swapOrientation(Boat.A);

        g.putBoat(Boat.A,23);
        g.putBoat(Boat.C,69);
        g.putBoat(Boat.F,55);
        g.putBoat(Boat.T,0);
        g.putBoat(Boat.C,1);

        g.isTouch(0);
        g.isTouch(10);
        g.isTouch(37);
        g.isTouch(61);

        g.affichage();
    }
}
