package fr.bcecb.render;

import fr.bcecb.Game;
import fr.bcecb.event.Event;
import fr.bcecb.event.GameEvent;
import fr.bcecb.event.WindowEvent;
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (vidMode != null) {
            glfwWindowHint(GLFW_RED_BITS, vidMode.redBits());
            glfwWindowHint(GLFW_GREEN_BITS, vidMode.greenBits());
            glfwWindowHint(GLFW_BLUE_BITS, vidMode.blueBits());
            glfwWindowHint(GLFW_REFRESH_RATE, vidMode.refreshRate());
        } else Log.SYSTEM.warning("No video mode available !");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        Log.SYSTEM.config("Window size is {0}x{1}", width, height);

        Window window = new Window(title, width, height, fullscreen);

        if (window.getId() == NULL) {
            return null;
        }

        if (!fullscreen) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer pWidth = stack.mallocInt(1);
                IntBuffer pHeight = stack.mallocInt(1);

                glfwGetWindowSize(window.getId(), pWidth, pHeight);

                if (vidMode != null) {
                    glfwSetWindowPos(window.getId(), (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
                } else Log.SYSTEM.warning("No video mode available !");
            }
        }

        glfwSetFramebufferSizeCallback(window.getId(), window::setFramebufferSize);
        glfwSetWindowCloseCallback(window.getId(), window::close);

        glfwMakeContextCurrent(window.getId());
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

    public long getId() {
        return windowId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
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

    private void close(long window) {
        Event event = new GameEvent.Close();
        Game.getEventBus().post(event);
    }

    private void setFramebufferSize(long window, int width, int height) {
        assert window == this.windowId;

        this.width = width;
        this.height = height;

        Event event = new WindowEvent.Size(this.width, this.height);
        Game.getEventBus().post(event);
    }
}