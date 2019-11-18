package fr.bcecb.input;

import com.google.common.collect.Maps;
import fr.bcecb.Game;
import fr.bcecb.event.Event;
import fr.bcecb.event.KeyboardEvent;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

public class InputManager implements AutoCloseable {
    private final Window window;

    private float mouseX;
    private float mouseY;

    private final Map<Key, Boolean> keyStates = Maps.newEnumMap(Key.class);

    public InputManager(Window window) {
        this.window = window;

        GLFW.glfwSetKeyCallback(window.getHandle(), this::keyCallback);
        GLFW.glfwSetMouseButtonCallback(window.getHandle(), this::mouseButtonCallback);
        GLFW.glfwSetCursorPosCallback(window.getHandle(), this::mouseMoveCallback);
        GLFW.glfwSetScrollCallback(window.getHandle(), this::mouseScrollCallback);
    }

    private void mouseButtonCallback(long window, int button, int action, int mods) {
        MouseEvent.Click.Type type = action == GLFW.GLFW_PRESS ? MouseEvent.Click.Type.PRESSED : MouseEvent.Click.Type.RELEASED;

        Event event = new MouseEvent.Click(this, this.mouseX, this.mouseY, type, button);
        Game.EVENT_BUS.post(event);
    }

    private void mouseMoveCallback(long window, double x, double y) {
        float lastMouseX = this.mouseX;
        float lastMouseY = this.mouseY;

        if (this.mouseX != x || this.mouseY != y) {
            this.mouseX = (float) x * this.window.getScaledWidth() / this.window.getWidth();
            this.mouseY = (float) y * this.window.getScaledHeight() / this.window.getHeight();

            Event event = new MouseEvent.Move(this, this.mouseX, this.mouseY, lastMouseX - this.mouseX, lastMouseY - this.mouseY);
            Game.EVENT_BUS.post(event);
        }
    }

    private void mouseScrollCallback(long window, double x, double y) {
        Event event = new MouseEvent.Scroll(this, this.mouseX, this.mouseY, (float) x, (float) y);
        Game.EVENT_BUS.post(event);
    }

    public boolean isKeyDown(Key key) {
        return keyStates.getOrDefault(key, false);
    }

    private void keyCallback(long window, int keyCode, int scancode, int action, int mods) {
        Key key = Key.valueOf(keyCode);

        if (key != Key.UNKNOWN) {
            keyStates.put(key, action == GLFW.GLFW_PRESS);
        }

        Event event = switch (action) {
            case GLFW.GLFW_RELEASE -> new KeyboardEvent.Release(key);
            case GLFW.GLFW_PRESS -> new KeyboardEvent.Press(key);
            case GLFW.GLFW_REPEAT -> new KeyboardEvent.Repeat(key);
            default -> new KeyboardEvent(key, false) {};
        };

        Game.EVENT_BUS.post(event);
    }

    @Override
    public void close() {
        GLFW.glfwSetKeyCallback(window.getHandle(), null);
        GLFW.glfwSetMouseButtonCallback(window.getHandle(), null);
        GLFW.glfwSetCursorPosCallback(window.getHandle(), null);
        GLFW.glfwSetScrollCallback(window.getHandle(), null);
    }
}
