package fr.bcecb.event;

import fr.bcecb.state.State;

@Event.Cancellable
public abstract class StateEvent extends Event {
    private final State state;

    StateEvent(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public static class Exit extends StateEvent {
        public Exit(State state) {
            super(state);
        }
    }

    public static class Enter extends StateEvent {
        private final State currentState;

        public Enter(State newState, State currentState) {
            super(newState);
            this.currentState = currentState;
        }

        public State getCurrentState() {
            return currentState;
        }
    }
}
