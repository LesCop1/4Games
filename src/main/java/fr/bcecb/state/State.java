package fr.bcecb.state;

import fr.bcecb.Event;
import fr.bcecb.render.IRenderable;

public abstract class State implements IRenderable {
    private final String name;

    protected State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onEnter();

    public abstract void onExit();

    public abstract void update();

    public abstract boolean shouldRenderBelow();

    public abstract boolean shouldUpdateBelow();

    @Event.Cancellable
    public static abstract class StateEvent extends Event {
        private final State state;

        StateEvent(State state) {
            this.state = state;
        }

        public State getState() {
            return state;
        }

        static class Exit extends StateEvent {
            Exit(State state) {
                super(state);
            }
        }

        public static class Enter extends StateEvent {
            private final State currentState;

            Enter(State newState, State currentState) {
                super(newState);
                this.currentState = currentState;
            }

            public State getCurrentState() {
                return currentState;
            }
        }
    }
}