package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.EventManager;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.util.Log;

import java.util.Iterator;
import java.util.Stack;

import static fr.bcecb.state.State.StateEvent;

public class StateEngine {
    private final Stack<State> stateStack = new Stack<>();

    public void pushState(State state) {
        StateEvent.Enter event = new StateEvent.Enter(state, getCurrentState());
        EventManager.fireEvent(event);

        if (!event.isCancelled()) {
            state.onEnter();
            stateStack.push(state);
        }
    }

    public void popState() {
        if (!stateStack.empty()) {
            StateEvent.Exit event = new StateEvent.Exit(stateStack.peek());
            EventManager.fireEvent(event);

            if (!event.isCancelled()) {
                stateStack.pop().onExit();
            }
        }
    }

    public void update() {
        if (stateStack.empty()) {
            Game.getInstance().stop();
        }

        for (State state : stateStack) {
            state.update();

            if (!state.shouldUpdateBelow()) break;
        }
    }

    public void render(double partialTick) {
        renderState(stateStack.iterator(), partialTick);
    }

    private void renderState(Iterator<State> stateIterator, double partialTick) {
        if (stateIterator.hasNext()) {
            State state = stateIterator.next();

            if (state.shouldRenderBelow()) {
                renderState(stateIterator, partialTick);
            }

            Renderer<State> renderer = RenderManager.getRendererFor(state);

            if (renderer != null) {
                renderer.render(state, partialTick);
            } else Log.warning(Log.RENDER, "State " + state.getName() + " has no renderer !");
        }
    }

    public State getCurrentState() {
        return !stateStack.empty() ? stateStack.peek() : null;
    }

    public Stack<State> getStateStack() {
        return stateStack;
    }
}
