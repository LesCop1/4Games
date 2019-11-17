package fr.bcecb.batailleNavale;

public class Boat {
    private final Boat.Type type;
    private final boolean[] hits;
    private boolean horizontal;

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
        AIRCRAFT_CARRIER("A", 5, 5), //AircraftCarrier
        CRUISER("C", 4, 4), //Cruiser
        FRIGATE("F", 3, 31), //Frigate
        SUBMARINE("S", 3, 30), //Submarine
        TORPEDO("T", 2, 2); //Torpedo

        private final String name;
        private final int sizeBoat;
        private final int id;

        Type(String name, int sizeBoat, int id) {
            this.name = name;
            this.sizeBoat = sizeBoat;
            this.id = id;
        }

        public int getId() { return id;}

        public String getName() {
            return name;
        }

        public int getSize() {
            return sizeBoat;
        }
    }
}