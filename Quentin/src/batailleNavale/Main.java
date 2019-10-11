package batailleNavale;

public class Main {
    public static void main(String[] args) {
        Game g = new Game();
        g.initBoard();
        g.putBoat(Boat.A,1);
        g.affichage();
//        g.genListBoat();
//        g.touch(Boat.T);
//        g.touch(Boat.A);
//        g.touch(Boat.C);
//        g.touch(Boat.F);
//        g.touch(Boat.S);
    }
}
