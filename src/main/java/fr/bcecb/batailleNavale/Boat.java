package fr.bcecb.batailleNavale;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;

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

    public boolean isAlive() {
        return hits != type.getSize();
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

    public enum Type {
        TORPEDO("T", 2, new ResourceHandle<>("textures/BatailleNavale/Torpedo.png") {}),
        SUBMARINE("S", 3, new ResourceHandle<>("textures/BatailleNavale/Submarine.png") {}),
        FRIGATE("F", 3, new ResourceHandle<>("textures/BatailleNavale/Frigate.png") {}),
        CRUISER("C", 4, new ResourceHandle<>("textures/BatailleNavale/Cruiser.png") {}),
        AIRCRAFT_CARRIER("A", 5, new ResourceHandle<>("textures/BatailleNavale/Aircraft_Carrier.png") {});

        private final String name;
        private final int sizeBoat;
        private final ResourceHandle<Texture> textureHandle;

        Type(String name, int sizeBoat, ResourceHandle<Texture> textureHandle) {
            this.name = name;
            this.sizeBoat = sizeBoat;
            this.textureHandle = textureHandle;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return sizeBoat;
        }

        public ResourceHandle<Texture> getTextureHandle() {
            return textureHandle;
        }
    }
}