package fr.bcecb.batailleNavale;

public class Boat {
    private final Boat.Type type;
    private final boolean[] hits;
    private boolean horizontal;
    private String name;

    private int x, y;

    public Boat(Boat.Type type) {
        this.type = type;
        this.hits = new boolean[type.getSize()];
        this.horizontal = true;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean[] getHits() {
        return hits;
    }

    public void hit(int i) {
        hits[i] = true;
    }

    public boolean isAlive() {
        for (boolean hit : hits) {
            if (!hit) return true; //Le bateau n'a pas été touché à cet endroit, il est encore vivant
        }

        return false;
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
        AIRCRAFT_CARRIER("A", 5), //AircraftCarrier
        CRUISER("C", 4), //Cruiser
        FRIGATE("F", 3), //Frigate
        SUBMARINE("S", 3), //Submarine
        TORPEDO("T", 2); //Torpedo

        private final String name;
        private final int sizeBoat;

        Type(String name, int sizeBoat) {
            this.name = name;
            this.sizeBoat = sizeBoat;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return sizeBoat;
        }
    }
}