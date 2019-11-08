package fr.bcecb.event;

import fr.bcecb.input.Key;
import fr.bcecb.util.Log;

@Event.Logging(Log.UI)
public abstract class KeyboardEvent extends Event {
    private final Key key;
    private final boolean pressed;

    public KeyboardEvent(Key key, boolean pressed) {
        this.key = key;
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public Key getKey() {
        return key;
    }

    public static class Press extends KeyboardEvent {
        public Press(Key key) {
            super(key, true);
        }
    }

    public static class Repeat extends KeyboardEvent.Press {
        public Repeat(Key key) {
            super(key);
        }
    }

    public static class Release extends KeyboardEvent {
        public Release(Key key) {
            super(key, false);
        }
    }
}
