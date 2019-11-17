package fr.bcecb.state;

import com.google.common.eventbus.Subscribe;
import fr.bcecb.Game;
import fr.bcecb.event.Event;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.event.StateEvent;
import fr.bcecb.render.Renderer;
import fr.bcecb.render.RendererRegistry;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class StateManager {
    private final Game game;

    private int width;
    private int height;

    private final Deque<State> stateStack = new ArrayDeque<>();

    public StateManager(Game game, int width, int height) {
        this.game = game;
        this.width = width;
        this.height = height;

        pushState(new MainMenuScreen());

        Game.EVENT_BUS.register(this);
    }

    public void pushState(State state) {
        Event event = new StateEvent.Enter(state);
        Game.EVENT_BUS.post(event);

        if (!event.isCancelled()) {
            if (state instanceof ScreenState) {
                ScreenState screenState = (ScreenState) state;
                screenState.clearGuiElements();
                screenState.initGui(this.width, this.height);
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
                    this.game.stop();
                }
            }
        }
    }

    public void rebuildGui(int width, int height) {
        this.width = width;
        this.height = height;

        for (State state : stateStack) {
            if (state instanceof ScreenState) {
                ScreenState screenState = (ScreenState) state;
                screenState.clearGuiElements();
                screenState.initGui(width, height);
            }
        }
    }

    public void update() {
        for (State state : stateStack) {
            state.onUpdate();

            if (state.shouldPauseBelow()) break;
        }
    }

    @Subscribe
    private void handleClickEvent(MouseEvent.Click event) {
        ScreenState screenState;
        for (State state : stateStack) {
            if (state instanceof ScreenState) {
                screenState = (ScreenState) state;
                screenState.onClick(event);
            }

            if (state.shouldPauseBelow() || event.isCancelled()) break;
        }
    }

    @Subscribe
    private void handleHoverEvent(MouseEvent.Move event) {
        ScreenState screenState;
        for (State state : stateStack) {
            if (state instanceof ScreenState) {
                screenState = (ScreenState) state;
                screenState.onHover(event);

                if (state.shouldPauseBelow() || event.isCancelled()) break;
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

                if (state.shouldPauseBelow() || event.isCancelled()) break;
            }
        }
    }

    public void render(RendererRegistry rendererRegistry, float partialTick) {
        renderState(rendererRegistry, stateStack.iterator(), partialTick);
    }

    private void renderState(RendererRegistry rendererRegistry, Iterator<State> stateIterator, float partialTick) {
        if (stateIterator.hasNext()) {
            State state = stateIterator.next();

            if (state.shouldRenderBelow()) {
                renderState(rendererRegistry, stateIterator, partialTick);
            }

            Renderer<State> renderer = rendererRegistry.getRendererFor(state);

            if (renderer != null) {
                renderer.render(state, partialTick);
            } else Log.RENDER.warning("State {0} has no renderer !", state.getName());
        }
    }
}
