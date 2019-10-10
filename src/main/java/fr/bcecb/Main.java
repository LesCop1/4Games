package fr.bcecb;

import org.lwjgl.opengl.GL;

import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    private static final Logger LOGGER = Logger.getLogger("Game");

    private static final Main INSTANCE = new Main();

    private long window;

    private boolean running;

    private Main() { }

    private void start() {
        LOGGER.info("Starting the game");

        if (!glfwInit()) {
            LOGGER.severe("GLFW couldn't initialize");
            return; //Don't let the game start
        }

        this.window = glfwCreateWindow(800, 600, "Main", NULL, NULL);
        this.running = true;

        while(this.isRunning()) { //Main game loop
            if (glfwWindowShouldClose(window)) {
                this.stop();
            }

            glfwPollEvents();
        }
    }

    public void stop() {
        LOGGER.info("Stopping the game");
        this.running = false;

        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public boolean isRunning() {
        return running;
    }

    public long getWindow() {
        return window;
    }

    /**
     * Returns the instance of the game, which is constant.
     * @return the instance of the game
     */
    public static Main getInstance() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static void main(String[] args) {
        Main.getInstance().start();
    }
}
