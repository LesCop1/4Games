package fr.bcecb.render;

import fr.bcecb.Game;
import fr.bcecb.util.Log;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class RenderEngine {
    private Window window;

    public RenderEngine() {
    }

    public boolean init() {
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

        if (!glfwInit()) {
            Log.severe(Log.RENDER, "Couldn't initialize GLFW");
            return false;
        }

        this.window = Window.newInstance("Game", 800, 600, false);

        if (this.window == null) {
            Log.severe(Log.RENDER, "Couldn't create window");
            return false;
        }

        GL.createCapabilities();

        RenderManager.init();
        glfwShowWindow(window.id());

        return true;
    }

    public void cleanUp() {
        if (this.window != null) {
            this.window.destroy();
            this.window = null;
        }

        glfwTerminate();
    }

    public void update() {
        if (window.shouldClose()) {
            Game.getInstance().stop();
            return;
        }

        glfwSwapBuffers(window.id());
        glfwPollEvents();
    }

    public void render(double partialTick) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glViewport(0, 0, window.width(), window.height());
    }

    public Window getWindow() {
        return window;
    }
}
