package fr.bcecb;

import com.google.common.eventbus.EventBus;
import fr.bcecb.event.EventExceptionHandler;
import fr.bcecb.input.InputManager;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Window;
import fr.bcecb.resources.Profile;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.sound.SoundManager;
import fr.bcecb.state.StateManager;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Log;
import fr.bcecb.util.RenderHelper;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;

public final class Game implements AutoCloseable {
    private static final int ticksPerSecond = 60;

    public static final EventBus EVENT_BUS = new EventBus(EventExceptionHandler.getInstance());

    private final Window window;

    private final RenderManager renderManager;
    private final StateManager stateManager;
    private final SoundManager soundManager;

    private final InputManager inputManager;

    private final ResourceManager resourceManager;

    private boolean running = false;

    private static final Game INSTANCE = new Game();
    private Profile profile;

    private Game() {
        if (!glfwInit()) {
            Log.SYSTEM.severe("Couldn't initialize GLFW");
        }

        this.window = new Window(this, "4Games", 800, 600);

        this.resourceManager = new ResourceManager();

        this.soundManager = new SoundManager(this.resourceManager);
        this.stateManager = new StateManager(this, this.window.getScaledWidth(), this.window.getScaledHeight());
        this.renderManager = new RenderManager(this.resourceManager);

        this.inputManager = new InputManager(this, this.window);

        this.profile = resourceManager.getResource(new ResourceHandle<>(Constants.PROFILE_FILE_PATH) {});
    }

    private void start() {
        Log.SYSTEM.info("Starting the game");
        this.running = true;

        final float ticks = 1.0f / ticksPerSecond;
        float currentTime;
        float lastTime = 0.0f;
        float delta = 0.0f;

        while (this.running) {
            currentTime = (float) glfwGetTime();
            delta += (currentTime - lastTime) / ticks;
            lastTime = currentTime;

            while (delta >= 1.0) {
                this.stateManager.update();

                --delta;
            }

            RenderHelper.setupProjection(window.getFramebufferWidth(), window.getFramebufferHeight(), window.getGuiScale());
            this.renderManager.render(this.stateManager, delta);

            this.window.update();
        }

        this.close();
    }

    @Override
    public void close() {
        try {
            this.renderManager.close();
            this.resourceManager.close();
            this.soundManager.close();
        } finally {
            this.window.close();
        }
    }

    public void stop() {
        this.running = false;
    }

    public void updateWindowSize() {
        this.renderManager.getFontRenderer().setGuiScale(this.window.getGuiScale());
        this.stateManager.rebuildGui(this.window.getScaledWidth(), this.window.getScaledHeight());
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public Profile getProfile() {
        return profile;
    }

    public static Game instance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Game.instance().start();
    }
}
