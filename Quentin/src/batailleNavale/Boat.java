package batailleNavale;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum Boat {

    A("AircraftCarrier", 1/*5*/, true, true),
    C("Cruiser", 1/*4*/, true, true),
    F("Frigate", 1/*3*/, true, true),
    S("Submarine", 1/*3*/, true, true),
    T("Torpedo", 1/*2*/, true, true);

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
