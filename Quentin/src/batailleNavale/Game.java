package batailleNavale;

import java.util.ArrayList;
import java.util.List;

public class Game {

    List<Boat> listBoat = new ArrayList<>();

    public void genListBoat() {
        listBoat.add(Boat.A);
        listBoat.add(Boat.C);
        listBoat.add(Boat.F);
        listBoat.add(Boat.S);
        listBoat.add(Boat.T);
        System.out.println(listBoat);
    }

    public int deleteBoat(Boat b){
        listBoat.remove(b);
        if(listBoat.isEmpty()){
            dead();
            System.out.println("gg");
            System.exit(1);
            return 1;
        }
        return 0;
    }

    public void touch(Boat boat){
        boat.setSizeBoat(-1);
        sink(boat);
    }

    public void sink(Boat boat){
        if(boat.getSizeBoat()==0){
            boat.setAlive(false);
            deleteBoat(boat);
        }
    }

    public void putBoat(){
        //TODO Placement bateau en fonction de la grille
    }

    public void swapOrientation(Boat boat){
        if(boat.isOrientation()){
            boat.setOrientation(false);
        }else{
            boat.setOrientation(true);
        }
    }

    public void dead(){
        Boat.stream()
                .filter(d -> d.isAlive()==false)
                .forEach(System.out::println);
    }
}
