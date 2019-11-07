package fr.bcecb;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import fr.bcecb.event.Event;
import fr.bcecb.event.EventExceptionHandler;
import fr.bcecb.event.GameEvent;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.state.MainMenuScreen;
import fr.bcecb.state.StateEngine;
import fr.bcecb.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.*;

public final class Game {
    private static final Game INSTANCE = new Game();

    private static final int ticksPerSecond = 60;

    private static final ExecutorService EVENT_EXECUTOR = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), (r, executor) -> {
    });
    private static final EventBus EVENT_BUS = new AsyncEventBus(EVENT_EXECUTOR, EventExceptionHandler.getInstance());

    private final RenderEngine renderEngine;
    private final StateEngine stateEngine;

    private final ResourceManager resourceManager;

    private boolean running = false;

    private Game() {
        if (!glfwInit()) {
            Log.SYSTEM.severe("Couldn't initialize GLFW");
        }

        glfwSetErrorCallback(Log.createErrorCallback());

        this.resourceManager = new ResourceManager();

        this.renderEngine = new RenderEngine(resourceManager);
        this.stateEngine = new StateEngine();
    }

    private void start() {
        glfwShowWindow(renderEngine.getWindow().getId());

        Log.SYSTEM.debug("Starting the game");
        running = true;

        stateEngine.pushState(new MainMenuScreen());

        float ticks = 1.0f / ticksPerSecond;
        float currentTime;
        float lastTime = 0.0f;
        float delta = 0.0f;

        while (this.isRunning()) {
            currentTime = (float) glfwGetTime();
            delta += (currentTime - lastTime) / ticks;
            lastTime = currentTime;

            while (delta >= 1.0) {
                Event event = new GameEvent.Tick();
                Game.getEventBus().post(event);

                stateEngine.update();

                --delta;
            }

            renderEngine.render(stateEngine, delta);

            glfwSwapBuffers(renderEngine.getWindow().getId());
            glfwPollEvents();
        }

        resourceManager.cleanUp();
        renderEngine.cleanUp();
    }

    @Subscribe
    public void stop(GameEvent.Close event) {
        EVENT_EXECUTOR.shutdown();
        running = false;
    }

    private boolean isRunning() {
        return running;
    }

    public RenderEngine getRenderEngine() {
        return renderEngine;
    }

    public StateEngine getStateEngine() {
        return stateEngine;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public static EventBus getEventBus() {
        return EVENT_BUS;
    }

    public static Game instance() {
        return INSTANCE;
    }

    static {
        Game.getEventBus().register(INSTANCE);
        Game.getEventBus().register(INSTANCE.getStateEngine());
        Game.getEventBus().register(INSTANCE.getRenderEngine());
    }

    public static void main(String[] args) {
        Game.instance().start();
    }
}
