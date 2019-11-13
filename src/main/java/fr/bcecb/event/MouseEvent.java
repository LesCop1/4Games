package fr.bcecb.event;

import fr.bcecb.util.Log;

@Event.Logging(Log.UI)
public abstract class MouseEvent extends Event {
    private MouseEvent() {
    }

    @Cancellable
    public static final class Click extends MouseEvent {
        private final int button;
        private final float x, y;
        private final Type type;

        public Click(Type type, int button, float x, float y) {
            this.type = type;
            this.button = button;
            this.x = x;
            this.y = y;
        }

        public Type getType() {
            return type;
        }

        public int getButton() {
            return button;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public enum Type {PRESSED, RELEASED}
    }

    public static final class Move extends MouseEvent {
        private final float x;
        private final float y;

        public Move(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    @Cancellable
    public static final class Scroll extends MouseEvent {
        private final float x;
        private final float y;

        public Scroll(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}