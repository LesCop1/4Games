package fr.bcecb;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import fr.bcecb.event.EventExceptionHandler;
import fr.bcecb.event.GameEvent;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.MainMenuScreen;
import fr.bcecb.state.StateEngine;
import fr.bcecb.util.Log;

import static org.lwjgl.glfw.GLFW.*;

public final class Game {

    private static final int ticksPerSecond = 60;

    public static final EventBus EVENT_BUS = new EventBus(EventExceptionHandler.getInstance());

    private final RenderEngine renderEngine;
    private final StateEngine stateEngine;

    private final ResourceManager resourceManager;

    private boolean running = false;

    private static final Game INSTANCE = new Game();

    public ResourceHandle<Texture> currentProfile = new ResourceHandle<>("textures/defaultProfile.png") {};

    private Game() {
        if (!glfwInit()) {
            Log.SYSTEM.severe("Couldn't initialize GLFW");
        }

        glfwSetErrorCallback(Log.createErrorCallback());

        this.resourceManager = new ResourceManager();

        this.renderEngine = new RenderEngine(resourceManager);
        this.stateEngine = new StateEngine();

        Game.EVENT_BUS.register(this);
    }

    private void start() {
        renderEngine.getWindow().show();
        Log.SYSTEM.info("Starting the game");
        running = true;

        stateEngine.pushState(new MainMenuScreen());

        float ticks = 1.0f / ticksPerSecond;
        float currentTime;
        float lastTime = 0.0f;
        float delta = 0.0f;

        while (this.running) {
            currentTime = (float) glfwGetTime();
            delta += (currentTime - lastTime) / ticks;
            lastTime = currentTime;

            while (delta >= 1.0) {
                EVENT_BUS.post(new GameEvent.Tick());
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
        running = false;
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

    public static Game instance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Game.instance().start();
    }
}
