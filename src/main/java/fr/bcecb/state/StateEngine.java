package fr.bcecb.state;

import fr.bcecb.Game;
import fr.bcecb.event.Event;
import fr.bcecb.event.GameEvent;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static fr.bcecb.state.State.StateEvent;

public class StateEngine {
    private final Deque<State> stateStack = new ArrayDeque<>();

    public void pushState(State state) {
        Event event = new StateEvent.Enter(state, getCurrentState());
        Game.getEventBus().post(event);

        if (!event.isCancelled()) {
            stateStack.push(state);
            state.onEnter();
        }
    }

    public void popState() {
        if (!stateStack.isEmpty()) {
            Event event = new StateEvent.Exit(stateStack.peek());
            Game.getEventBus().post(event);

            if (!event.isCancelled()) {
                stateStack.pop().onExit();

                if (stateStack.isEmpty()) {
                    Event event1 = new GameEvent.Close();
                    Game.getEventBus().post(event1);
                }
            }
        }
    }

    public void update() {
        for (State state : stateStack) {
            state.update();

            if (!state.shouldUpdateBelow()) break;
        }
    }

    public void render(RenderManager renderManager, float partialTick) {
        renderState(renderManager, stateStack.iterator(), partialTick);
    }

    private void renderState(RenderManager renderManager, Iterator<State> stateIterator, float partialTick) {
        if (stateIterator.hasNext()) {
            State state = stateIterator.next();

            if (state.shouldRenderBelow()) {
                renderState(renderManager, stateIterator, partialTick);
            }

            Renderer<State> renderer = renderManager.getRendererFor(state);

            if (renderer != null) {
                renderer.render(state, partialTick);
            } else Log.RENDER.warning("State {0} has no renderer !", state.getName());
        }
    }

    public boolean isCurrentState(State state) {
        return state == getCurrentState();
    }

    public State getCurrentState() {
        return !stateStack.isEmpty() ? stateStack.peek() : null;
    }

    public Deque<State> getStateStack() {
        return stateStack;
    }
}
