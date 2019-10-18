package fr.bcecb.input;

import fr.bcecb.Event;

public abstract class MouseEvent extends Event {
    private MouseEvent() {
    }

    @Cancellable
    public static final class Click extends MouseEvent {
        private final int button;
        private final float x, y;
        private final Type type;

        Click(Type type, int button, float x, float y) {
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
        private final float x, dx;
        private final float y, dy;

        Move(float x, float y, float dx, float dy) {
            this.x = x;
            this.dx = dx;
            this.y = y;
            this.dy = dy;
        }

        public float getX() {
            return x;
        }

        public float getDeltaX() {
            return dx;
        }

        public float getY() {
            return y;
        }

        public float getDeltaY() {
            return dy;
        }
    }

    @Cancellable
    public static final class Scroll extends MouseEvent {
        private final float x;
        private final float y;

        Scroll(float x, float y) {
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

    public static final class Enter extends MouseEvent {
        private final boolean entered;

        Enter(boolean entered) {
            this.entered = entered;
        }

        public boolean hasEntered() {
            return entered;
        }
    }
}