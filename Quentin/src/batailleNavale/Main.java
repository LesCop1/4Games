package batailleNavale;

public class Main {
    public static void main(String[] args) {
        Game g = new Game();
        g.initBoard();
        g.putBoat(Boat.A,0);
        g.putBoat(Boat.C,3);
        g.affichage();
//        g.genListBoat();
//        g.touch(Boat.S);
    }
}
