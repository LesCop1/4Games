package fr.bcecb.render;

import fr.bcecb.Game;
import fr.bcecb.event.Event;
import fr.bcecb.event.GameEvent;
import fr.bcecb.event.WindowEvent;
import fr.bcecb.input.KeyboardManager;
import fr.bcecb.input.MouseManager;
import fr.bcecb.util.Log;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Platform;

import java.nio.FloatBuffer;

import static fr.bcecb.util.GLFWUtil.glfwInvoke;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long windowId;

    private final MouseManager mouseManager;
    private final KeyboardManager keyboardManager;

    private int width;
    private int height;

    private float contentScaleX, contentScaleY;

    private final Matrix4f projection = new Matrix4f();

    private Window(String title, int width, int height, float contentScaleX, float contentScaleY, boolean fullscreen) {
        this.windowId = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
        this.contentScaleX = contentScaleX;
        this.contentScaleY = contentScaleY;

        this.mouseManager = new MouseManager(this);
        this.keyboardManager = new KeyboardManager(this);
    }

    public static Window newInstance(String title, int width, int height, boolean fullscreen) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidMode = glfwGetVideoMode(monitor);

        if (vidMode != null) {
            glfwWindowHint(GLFW_RED_BITS, vidMode.redBits());
            glfwWindowHint(GLFW_GREEN_BITS, vidMode.greenBits());
            glfwWindowHint(GLFW_BLUE_BITS, vidMode.blueBits());
            glfwWindowHint(GLFW_REFRESH_RATE, vidMode.refreshRate());
        } else Log.SYSTEM.warning("No video mode available !");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        Log.SYSTEM.config("Window size is {0}x{1}", width, height);


        float contentScaleX;
        float contentScaleY;

        try (MemoryStack s = MemoryStack.stackPush()) {
            FloatBuffer px = s.mallocFloat(1);
            FloatBuffer py = s.mallocFloat(1);

            glfwGetMonitorContentScale(monitor, px, py);

            contentScaleX = px.get(0);
            contentScaleY = py.get(0);

            if (Platform.get() != Platform.MACOSX) {
                width = Math.round(width * contentScaleX);
                height = Math.round(height * contentScaleY);
            }
        }

        Log.SYSTEM.config("Framebuffer content scaling is ({0}, {1})", contentScaleX, contentScaleY);

        Window window = new Window(title, width, height, contentScaleX, contentScaleY, fullscreen);

        if (window.getId() == NULL) {
            return null;
        }

        if (!fullscreen) {
            if (vidMode != null) {
                glfwSetWindowPos(window.getId(), (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
            } else Log.SYSTEM.warning("No video mode available !");
        }

        glfwSetWindowSizeCallback(window.getId(), window::setWindowSize);
        glfwSetFramebufferSizeCallback(window.getId(), window::setFramebufferSize);
        glfwSetWindowCloseCallback(window.getId(), window::close);

        glfwMakeContextCurrent(window.getId());
        glfwSwapInterval(1);

        return window;
    }

    public void show() {
        glfwShowWindow(getId());
        glfwInvoke(getId(), this::setWindowSize, this::setFramebufferSize);
    }

    public float getContentScaleX() {
        return contentScaleX;
    }

    public float getContentScaleY() {
        return contentScaleY;
    }

    public static Window getCurrentWindow() {
        return Game.instance().getRenderEngine().getWindow();
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public KeyboardManager getKeyboardManager() {
        return keyboardManager;
    }

    public void destroy() {
        glfwDestroyWindow(windowId);
    }

    public long getId() {
        return windowId;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowId);
    }

    private void close(long window) {
        assert window == this.windowId;

        Event event = new GameEvent.Close();
        Game.EVENT_BUS.post(event);
    }

    private void setWindowSize(long window, int width, int height) {
        assert window == this.windowId;

        if (getContentScaleY() == 1 && getContentScaleX() == 1) {
            width /= getContentScaleX();
            height /= getContentScaleY();
        }

        this.width = width;
        this.height = height;

        this.projection.setOrtho2D(0, width, height, 0);

        Event event = new WindowEvent.Size(this.width, this.height);
        Game.EVENT_BUS.post(event);
    }

    private void setFramebufferSize(long window, int width, int height) {
        assert window == this.windowId;

        glViewport(0, 0, width, height);
    }
}