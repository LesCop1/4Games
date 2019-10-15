package batailleNavale;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum Boat {

    A("A", 5, false, true), //AircraftCarrier
    C("C", 4, true, true), //Cruiser
    F("F", 3, true, true), //Frigate
    S("S", 3, true, true), //Submarine
    T("T", 2, true, true); //Torpedo

    private String name;
    private int sizeBoat;
    private boolean orientation; // True = Horizontal, False = Vertical
    private boolean alive;

    Boat(String name, int sizeBoat, boolean orientation, boolean alive) {
        this.name = name;
        this.sizeBoat = sizeBoat;
        this.orientation = orientation;
        this.alive = alive;
    }

    public static Stream<Boat> stream() {
        return Stream.of(Boat.values());
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
        this.sizeBoat += sizeBoat;
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
