package fr.bcecb;

import com.google.common.eventbus.EventBus;
import fr.bcecb.event.EventExceptionHandler;
import fr.bcecb.input.InputManager;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateManager;
import fr.bcecb.util.Log;
import fr.bcecb.util.Render;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;

public final class Game implements AutoCloseable {
    private static final int ticksPerSecond = 60;

    public static final EventBus EVENT_BUS = new EventBus(EventExceptionHandler.getInstance());

    private final Window window;

    private final RenderManager renderManager;
    private final StateManager stateManager;

    private final InputManager inputManager;

    private final ResourceManager resourceManager;

    private boolean running = false;

    private static final Game INSTANCE = new Game();

    public ResourceHandle<Texture> currentProfile = new ResourceHandle<>("textures/defaultProfile.jpg") {
    };
    private int[] highScores; // 0 = Sudoku ; 1 = BattleShip ; 3 = Bingo ; 4 = Poker ;
    private int tokenBalance, nbGames, nbSuccess, nbLoose, timeSpent;


    private Game() {
        if (!glfwInit()) {
            Log.SYSTEM.severe("Couldn't initialize GLFW");
        }

        this.window = new Window(this, "4Games", 800, 600);

        this.resourceManager = new ResourceManager();

        this.stateManager = new StateManager(this, this.window.getScaledWidth(), this.window.getScaledHeight());
        this.renderManager = new RenderManager(this.resourceManager);

        this.inputManager = new InputManager(this.window);
    }

    private void start() {
        Log.SYSTEM.info("Starting the game");
        this.running = true;

        float ticks = 1.0f / ticksPerSecond;
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

            Render.setupProjection(window.getFramebufferWidth(), window.getFramebufferHeight(), window.getGuiScale());
            this.renderManager.render(this.stateManager, delta);

            this.window.update();
        }
    }

    @Override
    public void close() {
        try {
            this.renderManager.close();
            this.resourceManager.close();
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

    public InputManager getInputManager() {
        return inputManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public int getHighScore(int index) {
        return highScores[index];
    }

    public void setHighScore(int index,int score){
        this.highScores[index] = score;
    }

    public int getTokenBalance() {
        return tokenBalance;
    }

    public int getNbGames() {
        return nbGames;
    }

    public int getNbSuccess() {
        return nbSuccess;
    }

    public int getNbLoose() {
        return nbLoose;
    }

    public void addTokenSold(int amount) {
        this.tokenBalance += amount;
    }

    public void increaseNbSuccess() {
        this.nbSuccess++;
        this.nbGames++;
    }

    public void increaseNbLoose(int nbLoose) {
        this.nbLoose = nbLoose;
        this.nbGames++;
    }

    public static Game instance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Game.instance().start();
    }
}
