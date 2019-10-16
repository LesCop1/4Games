package fr.bcecb.input;

import fr.bcecb.event.Event;

public abstract class MouseEvent extends Event {
    private MouseEvent() {
    }

    @Cancellable
    public static final class Click extends MouseEvent {
        private final int button;
        private final double x, y;
        private final Type type;

        Click(Type type, int button, double x, double y) {
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

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public enum Type {PRESSED, RELEASED}
    }

    public static final class Move extends MouseEvent {
        private final double x, dx;
        private final double y, dy;

        Move(double x, double y, double dx, double dy) {
            this.x = x;
            this.dx = dx;
            this.y = y;
            this.dy = dy;
        }

        public double getX() {
            return x;
        }

        public double getDeltaX() {
            return dx;
        }

        public double getY() {
            return y;
        }

        public double getDeltaY() {
            return dy;
        }
    }

    @Cancellable
    public static final class Scroll extends MouseEvent {
        private final double x;
        private final double y;

        Scroll(double x, double y) {
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