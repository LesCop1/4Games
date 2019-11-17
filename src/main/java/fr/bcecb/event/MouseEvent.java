package fr.bcecb.event;

import fr.bcecb.input.InputManager;
import fr.bcecb.util.Log;

@Event.Logging(Log.UI)
public abstract class MouseEvent extends Event {
    private final InputManager inputManager;
    private final float x;
    private final float y;

    private MouseEvent(InputManager inputManager, float x, float y) {
        this.inputManager = inputManager;
        this.x = x;
        this.y = y;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Cancellable
    public static final class Click extends MouseEvent {
        private final int button;

        private final Type type;

        public Click(InputManager inputManager, float x, float y, Type type, int button) {
            super(inputManager, x, y);
            this.type = type;
            this.button = button;
        }

        public Type getType() {
            return type;
        }

        public int getButton() {
            return button;
        }

        public enum Type {PRESSED, RELEASED}
    }

    public static final class Move extends MouseEvent {
        private final float dx;
        private final float dy;

        public Move(InputManager inputManager, float x, float y, float dx, float dy) {
            super(inputManager, x, y);
            this.dx = dx;
            this.dy = dy;
        }

        public float getDx() {
            return dx;
        }

        public float getDy() {
            return dy;
        }
    }

    @Cancellable
    public static final class Scroll extends MouseEvent {
        private final float dx;
        private final float dy;

        public Scroll(InputManager inputManager, float x, float y, float dx, float dy) {
            super(inputManager, x, y);
            this.dx = dx;
            this.dy = dy;
        }

        public double getdX() {
            return dx;
        }

        public double getdY() {
            return dy;
        }
    }
}