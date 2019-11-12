package fr.bcecb.state;

import com.google.common.eventbus.Subscribe;
import fr.bcecb.Game;
import fr.bcecb.event.*;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class StateEngine {
    private final Deque<State> stateStack = new ArrayDeque<>();

    private boolean shouldRebuildGUI = false;

    public StateEngine() {
        Game.EVENT_BUS.register(this);
    }

    public void pushState(State state) {
        Event event = new StateEvent.Enter(state, getCurrentState());
        Game.EVENT_BUS.post(event);

        if (!event.isCancelled()) {
            if (state instanceof ScreenState) {
                ScreenState screenState = (ScreenState) state;
                screenState.clearGuiElements();
                screenState.initGui();
            }

            stateStack.push(state);
            state.onEnter();
        }
    }

    public void popState() {
        if (!stateStack.isEmpty()) {
            Event event = new StateEvent.Exit(stateStack.peek());
            Game.EVENT_BUS.post(event);

            if (!event.isCancelled()) {
                stateStack.pop().onExit();

                if (stateStack.isEmpty()) {
                    Event event1 = new GameEvent.Close();
                    Game.EVENT_BUS.post(event1);
                }
            }
        }
    }

    public void update() {
        if (shouldRebuildGUI) {
            ScreenState screenState;
            for (State state : stateStack) {
                if (state instanceof ScreenState) {
                    screenState = (ScreenState) state;
                    screenState.clearGuiElements();
                    screenState.initGui();
                }
            }

            this.shouldRebuildGUI = false;
        }

        for (State state : stateStack) {
            state.onUpdate();

            if (!state.shouldUpdateBelow()) break;
        }
    }

    @Subscribe
    private void handleWindowResizeEvent(WindowEvent.Size event) {
        this.shouldRebuildGUI = true;
    }

    @Subscribe
    private void handleClickEvent(MouseEvent.Click event) {
        ScreenState screenState;
        for (State state : stateStack) {
            if (state instanceof ScreenState) {
                screenState = (ScreenState) state;
                screenState.onClick(event);

                if (event.isCancelled()) break;
            }

            if (!state.shouldUpdateBelow()) break;
        }
    }

    @Subscribe
    private void handleHoverEvent(MouseEvent.Move event) {
        ScreenState screenState;
        for (State state : stateStack) {
            if (state instanceof ScreenState) {
                screenState = (ScreenState) state;
                screenState.onHover(event);

                if (event.isCancelled()) break;
            }
        }
    }

    @Subscribe
    private void handleScrollEvent(MouseEvent.Scroll event) {
        ScreenState screenState;
        for (State state : stateStack) {
            if (state instanceof ScreenState) {
                screenState = (ScreenState) state;
                screenState.onScroll(event);

                if (event.isCancelled()) break;

                if (!state.shouldUpdateBelow()) break;
            }
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
