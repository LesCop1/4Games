package fr.bcecb.batailleNavale;

public class Boat {
    private final Boat.Type type;
    private int hits;
    private boolean horizontal;

    private int x, y;

    public Boat(Boat.Type type) {
        this.type = type;
        this.hits = 0;
        this.horizontal = true;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHits() {
        return hits;
    }

    public void hits() {
        hits+=1;
    }

    //TODO I will use this shit to display some shit information on the screen
    public boolean isAlive() {
        if(hits==type.getSize()) return false;
        else return true;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public int getSize() {
        return type.getSize();
    }

    public Type getType() {
        return type;
    }

    enum Type {
        AIRCRAFT_CARRIER("A", 5, 5), //AircraftCarrier
        CRUISER("C", 4, 4), //Cruiser
        FRIGATE("F", 3, 3), //Frigate
        SUBMARINE("S", 3, 2), //Submarine
        TORPEDO("T", 2, 1); //Torpedo

        private final String name;
        private final int sizeBoat;
        private final int id;

        Type(String name, int sizeBoat, int id) {
            this.name = name;
            this.sizeBoat = sizeBoat;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return sizeBoat;
        }
    }
}