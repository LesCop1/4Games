package batailleNavale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Boat {

    A("AircraftCarrier",5,true,true),
    C("Cruiser",4,true,true),
    F("Frigate",3,true,true),
    S("Submarine",3,true,true),
    T("Torpedo",2,true,true);

    private String name;
    private int sizeBoat;
    private boolean orientation; // True = Horizontal, False = Vertical
    private boolean alive;

    Boat(){}

    Boat(String name, int sizeBoat, boolean orientation, boolean alive) {
        this.name = name;
        this.sizeBoat = sizeBoat;
        this.orientation = orientation;
        this.alive = alive;
    }

    public static List genBoat(){
        List<Boat> listBoat = new ArrayList<>();
        listBoat.addAll(Collections.singleton(A));
        listBoat.addAll(Collections.singleton(C));
        listBoat.addAll(Collections.singleton(F));
        listBoat.addAll(Collections.singleton(S));
        listBoat.addAll(Collections.singleton(T));
        System.out.println(listBoat);
        return listBoat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSizeBoat() {
        return sizeBoat;
    }

    public void setSizeBoat(int sizeBoat) {
        this.sizeBoat = sizeBoat;
    }

    public boolean isOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
