package fr.bcecb;

import com.google.common.eventbus.EventBus;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.state.MainMenuState;
import fr.bcecb.state.StateEngine;
import fr.bcecb.util.Log;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public final class Game {
    private static final Game INSTANCE = new Game();

    private static final EventBus EVENT_BUS = new EventBus();

    private static final int ticksPerSecond = 60;

    private final RenderEngine renderEngine;
    private final StateEngine stateEngine;

    private final ResourceManager resourceManager;

    private boolean running = false;

    private Game() {
        this.resourceManager = new ResourceManager();

        this.renderEngine = new RenderEngine(resourceManager);
        this.stateEngine = new StateEngine();
    }

    private boolean init() {
        Log.info("Initializing the game");

        Log.config("Tick rate is set to " + ticksPerSecond + " ticks/s");

        if (!renderEngine.init()) {
            Log.severe(Log.RENDER, "Couldn't initialize renderer");
            return false;
        }

        return true;
    }

    private void start() {
        if (!init()) {
            return;
        }

        Log.info("Starting the game");
        running = true;

        stateEngine.pushState(new MainMenuState());

        double ticks = 1.0D / ticksPerSecond;
        double currentTime;
        double lastTime = 0.0D;
        double delta = 0.0D;

        while (this.isRunning()) {
            currentTime = glfwGetTime();
            delta += (currentTime - lastTime) / ticks;
            lastTime = currentTime;

            while (delta >= 1.0) {
                stateEngine.update();

                --delta;
            }

            renderEngine.render(delta);
            stateEngine.render(renderEngine, delta);

            renderEngine.update();
        }
    }

    public void stop() {
        Log.info("Stopping the game");
        running = false;

        renderEngine.cleanUp();
        resourceManager.cleanUp();
    }

    public boolean isRunning() {
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

    public static void main(String[] args) {
        Game.instance().start();
    }
}
