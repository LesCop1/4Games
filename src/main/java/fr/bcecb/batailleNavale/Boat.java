package fr.bcecb.batailleNavale;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Constants;

public class Boat {
    protected final Boat.Type type;
    private boolean horizontal;
    private int x, y;

    public Boat(Boat.Type type) {
        this.type = type;
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
        TORPEDO("T", 2, Constants.BS_TORPEDO_H, Constants.BS_TORPEDO_V),
        SUBMARINE("S", 3, Constants.BS_SUBMARINE_H, Constants.BS_SUBMARINE_V),
        FRIGATE("F", 3, Constants.BS_FRIGATE_H, Constants.BS_FRIGATE_V),
        CRUISER("C", 4, Constants.BS_CRUISER_H, Constants.BS_CRUISER_V),
        AIRCRAFT_CARRIER("A", 5, Constants.BS_AIRCRAFT_CARRIER_H, Constants.BS_AIRCRAFT_CARRIER_V);

        private final String name;
        private final int sizeBoat;
        private final ResourceHandle<Texture> textureHandleH;
        private final ResourceHandle<Texture> textureHandleV;

        Type(String name, int sizeBoat, ResourceHandle<Texture> textureHandleH, ResourceHandle<Texture> textureHandleV) {
            this.name = name;
            this.sizeBoat = sizeBoat;
            this.textureHandleH = textureHandleH;
            this.textureHandleV = textureHandleV;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return sizeBoat;
        }

        public ResourceHandle<Texture> getTextureHandleH() {
            return textureHandleH;
        }

        public ResourceHandle<Texture> getTextureHandleV() {
            return textureHandleV;
        }
    }
}