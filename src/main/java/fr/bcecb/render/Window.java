package fr.bcecb.render;

import fr.bcecb.Game;
import fr.bcecb.util.Log;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements AutoCloseable {
    private final Game game;

    private final long handle;

    private final GLFWErrorCallback errorCallback;

    private int width;
    private int height;

    private int framebufferWidth;
    private int framebufferHeight;

    private int guiScale;

    private int scaledWidth;
    private int scaledHeight;

    public Window(Game game, String title, int width, int height) {
        this.game = game;

        this.errorCallback = Log.createErrorCallback();
        GLFW.glfwSetErrorCallback(this.errorCallback);

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 1);

        Log.SYSTEM.config("Window size is {0}x{1}", width, height);

        this.handle = GLFW.glfwCreateWindow(width, height, title, GLFW.GLFW_FALSE, NULL);
        this.width = width;
        this.height = height;

        GLFW.glfwMakeContextCurrent(this.handle);
        GL.createCapabilities();

        GLFW.glfwSetWindowSizeCallback(this.handle, this::setWindowSize);
        GLFW.glfwSetFramebufferSizeCallback(this.handle, this::setFramebufferSize);

        this.updateFramebufferSize();
        this.updateGuiScale();
    }

    public void update() {
        if (GLFW.glfwGetWindowAttrib(this.handle, GLFW.GLFW_VISIBLE) == GLFW.GLFW_FALSE) {
            GLFW.glfwShowWindow(this.handle);
        }

        GLFW.glfwSwapBuffers(this.handle);
        GLFW.glfwPollEvents();

        if (GLFW.glfwWindowShouldClose(this.handle)) {
            this.game.stop();
        }
    }

    @Override
    public void close() {
        this.errorCallback.close();

        GLFW.glfwDestroyWindow(this.handle);
        GLFW.glfwTerminate();
    }

    public void updateFramebufferSize() {
        int[] framebufferWidth = new int[1];
        int[] framebufferHeight = new int[1];

        GLFW.glfwGetFramebufferSize(this.handle, framebufferWidth, framebufferHeight);
        this.framebufferWidth = framebufferWidth[0];
        this.framebufferHeight = framebufferHeight[0];
    }

    public int computeGuiScale() {
        int computedScale = 1;
        while (computedScale < this.framebufferWidth && computedScale < this.framebufferHeight && this.framebufferWidth / (computedScale + 1) >= 320 && this.framebufferHeight / (computedScale + 1) >= 240) {
            ++computedScale;
        }

        return computedScale;
    }

    public void updateGuiScale() {
        this.guiScale = computeGuiScale();
        int frameBufferWidthRatio = (int) ((float) this.framebufferWidth / (float) this.guiScale);
        this.scaledWidth = (float) this.framebufferWidth / (float) this.guiScale > (float) frameBufferWidthRatio ? frameBufferWidthRatio + 1 : frameBufferWidthRatio;
        int framebufferHeightRatio = (int) ((float) this.framebufferHeight / (float) this.guiScale);
        this.scaledHeight = (float) this.framebufferHeight / (float) this.guiScale > (float) framebufferHeightRatio ? framebufferHeightRatio + 1 : framebufferHeightRatio;
    }

    private void setWindowSize(long window, int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void setFramebufferSize(long window, int width, int height) {
        float lastFramebufferWidth = framebufferWidth;
        float lastFramebufferHeight = framebufferHeight;

        if (width != 0 && height != 0) {
            this.framebufferWidth = width;
            this.framebufferHeight = height;

            if (lastFramebufferWidth != this.framebufferWidth || lastFramebufferHeight != this.framebufferHeight) {
                this.updateGuiScale();
                this.game.updateWindowSize();
            }
        }
    }

    public long getHandle() {
        return handle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFramebufferWidth() {
        return framebufferWidth;
    }

    public int getFramebufferHeight() {
        return framebufferHeight;
    }

    public int getScaledWidth() {
        return scaledWidth;
    }

    public int getScaledHeight() {
        return scaledHeight;
    }

    public int getGuiScale() {
        return guiScale;
    }
}