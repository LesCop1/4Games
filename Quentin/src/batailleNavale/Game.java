package batailleNavale;

import java.util.ArrayList;
import java.util.List;

public class Game {

    List<String> board = new ArrayList<>();
    List<Boat> listBoat = new ArrayList<>();

    public void initBoard() {
        for (int i = 0; i < 100; i++) {
            board.add("o");
        }
    }

    public void affichage(){
        for(int i=0; i<board.size(); i++){
            if(i%10==0) System.out.println();
            System.out.print(board.get(i));
        }
    }

    public void putBoat(Boat boat, int i) {
        if (boat.isOrientation()) {
            for(int j=i; j<boat.getSizeBoat(); j++){
                board.set(j,"A");
            }
        }else{
            System.out.println("ALED");
        }
    }

    public void swapOrientation(Boat boat) {
        if (boat.isOrientation()) {
            boat.setOrientation(false);
        } else {
            boat.setOrientation(true);
        }
    }

    public void genListBoat() {
        listBoat.add(Boat.A);
        listBoat.add(Boat.C);
        listBoat.add(Boat.F);
        listBoat.add(Boat.S);
        listBoat.add(Boat.T);
        System.out.println(listBoat);
    }

    public int deleteBoat(Boat b) {
        listBoat.remove(b);
        if (listBoat.isEmpty()) {
            dead();
            System.out.println("gg");
            System.exit(1);
            return 1;
        }
        return 0;
    }

    public void touch(Boat boat) {
        boat.setSizeBoat(-1);
        sink(boat);
    }

    public void sink(Boat boat) {
        if (boat.getSizeBoat() == 0) {
            boat.setAlive(false);
            deleteBoat(boat);
        }
    }

    public void dead() {
        Boat.stream()
                .filter(d -> d.isAlive() == false)
                .forEach(System.out::println);
    }
}
