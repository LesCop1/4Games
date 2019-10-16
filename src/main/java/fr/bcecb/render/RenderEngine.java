package fr.bcecb.render;

import fr.bcecb.Game;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Shader;
import fr.bcecb.util.Log;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class RenderEngine {
    private Window window;

    private final ResourceManager resourceManager;

    private final ResourceHandle<Shader> shaderResourceHandle = new ResourceHandle<>("shaders/main.json") {};

    private final RenderManager renderManager;

    public RenderEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.renderManager = new RenderManager(resourceManager);
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
        glfwShowWindow(window.id());


        Shader shader = (Shader) resourceManager.getResource(shaderResourceHandle);
        shader.bind();

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
            Game.instance().stop();
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

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public Window getWindow() {
        return window;
    }
}
