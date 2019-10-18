package fr.bcecb;

import com.google.common.eventbus.EventBus;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.state.MainMenuScreen;
import fr.bcecb.state.StateEngine;
import fr.bcecb.util.Log;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

public final class Game {
    private static final Game INSTANCE = new Game();

    private static final EventBus EVENT_BUS = new EventBus();

    private static final int ticksPerSecond = 60;

    private final RenderEngine renderEngine;
    private final StateEngine stateEngine;

    private final ResourceManager resourceManager;

    private boolean running = false;

    private Game() {
        if (!glfwInit()) {
            Log.SYSTEM.severe("Couldn't initialize GLFW");
        }

        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
        Log.SYSTEM.config("Tick rate is set to {0} ticks/s", ticksPerSecond);

        this.resourceManager = new ResourceManager();

        this.renderEngine = new RenderEngine(resourceManager);
        this.stateEngine = new StateEngine();
    }

    private void start() {
        glfwShowWindow(renderEngine.getWindow().id());

        Log.SYSTEM.info("Starting the game");
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
                stateEngine.update();

                --delta;
            }

            renderEngine.render(stateEngine, delta);

            renderEngine.update();
        }

        resourceManager.cleanUp();
        renderEngine.cleanUp();
    }

    public void stop() {
        Log.SYSTEM.info("Stopping the game");
        running = false;
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
