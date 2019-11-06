package fr.bcecb.event;

public abstract class WindowEvent extends Event {
    private WindowEvent() {
    }

    public static final class Size extends WindowEvent {
        private final int width;
        private final int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
