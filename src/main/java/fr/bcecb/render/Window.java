package fr.bcecb.render;

import fr.bcecb.Game;
import fr.bcecb.input.MouseManager;
import fr.bcecb.util.Log;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long windowId;

    private final MouseManager mouseManager;

    private int width, minWidth;
    private int height, minHeight;

    private Window(String title, int width, int height, boolean fullscreen) {
        this.windowId = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
        this.mouseManager = new MouseManager(this);
    }

    public static Window newInstance(String title, int width, int height, boolean fullscreen) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (vidMode != null) {
            glfwWindowHint(GLFW_RED_BITS, vidMode.redBits());
            glfwWindowHint(GLFW_GREEN_BITS, vidMode.greenBits());
            glfwWindowHint(GLFW_BLUE_BITS, vidMode.blueBits());
        } else Log.SYSTEM.warning("No video mode available !");

        Log.SYSTEM.config("Window size is {0}x{1}", width, height);

        Window window = new Window(title, width, height, fullscreen);

        if (window.id() == NULL) {
            return null;
        }

        if (!fullscreen) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer pWidth = stack.mallocInt(1);
                IntBuffer pHeight = stack.mallocInt(1);

                glfwGetWindowSize(window.id(), pWidth, pHeight);

                if (vidMode != null) {
                    glfwSetWindowPos(window.id(), (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
                } else Log.SYSTEM.warning("No video mode available !");
            }
        }

        glfwSetFramebufferSizeCallback(window.id(), window::setSize);

        glfwMakeContextCurrent(window.id());
        glfwSwapInterval(1);

        return window;
    }

    public static Window getCurrentWindow() {
        return Game.instance().getRenderEngine().getWindow();
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public void destroy() {
        glfwDestroyWindow(windowId);
    }

    public long id() {
        return windowId;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void setFullscreen(boolean fullscreen) {
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (vidMode != null) {
            if (fullscreen) {
                this.minWidth = this.width;
                this.minHeight = this.height;
                glfwSetWindowMonitor(windowId, glfwGetPrimaryMonitor(), 0, 0, vidMode.width(), vidMode.height(), GLFW_DONT_CARE);
            } else {
                glfwSetWindowMonitor(windowId, NULL, (vidMode.width() - this.minWidth) / 2, (vidMode.height() - this.minHeight) / 2, minWidth, minHeight, GLFW_DONT_CARE);
            }
        } else Log.SYSTEM.warning("No video mode available !");
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowId);
    }

    private void setSize(long window, int width, int height) {
        assert window == this.windowId;

        this.width = width;
        this.height = height;
    }
}