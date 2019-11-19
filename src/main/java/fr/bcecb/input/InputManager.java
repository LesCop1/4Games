package fr.bcecb.input;

import com.google.common.collect.Maps;
import fr.bcecb.Game;
import fr.bcecb.render.Window;
import org.lwjgl.glfw.GLFW;

import java.util.Map;

public class InputManager implements AutoCloseable {
    private final Game game;
    private final Window window;

    private float mouseX;
    private float mouseY;

    private final Map<MouseButton, Boolean> mouseButtonStates = Maps.newEnumMap(MouseButton.class);
    private final Map<Key, Boolean> keyStates = Maps.newEnumMap(Key.class);

    public InputManager(Game game, Window window) {
        this.game = game;
        this.window = window;

        GLFW.glfwSetKeyCallback(window.getHandle(), this::keyCallback);
        GLFW.glfwSetMouseButtonCallback(window.getHandle(), this::mouseButtonCallback);
        GLFW.glfwSetCursorPosCallback(window.getHandle(), this::mouseMoveCallback);
    }

    private void mouseButtonCallback(long window, int buttonCode, int action, int mods) {
        MouseButton button = MouseButton.valueOf(buttonCode);

        if (button != MouseButton.UNKNOWN) {
            mouseButtonStates.put(button, action == GLFW.GLFW_PRESS);
        }

        if (action == GLFW.GLFW_RELEASE) {
            this.game.getStateManager().mouseClicked(this.mouseX, this.mouseY);
        }
    }

    private void mouseMoveCallback(long window, double x, double y) {
        if (this.mouseX != x || this.mouseY != y) {
            this.mouseX = (float) x * this.window.getScaledWidth() / this.window.getWidth();
            this.mouseY = (float) y * this.window.getScaledHeight() / this.window.getHeight();

            this.game.getStateManager().mouseMoved(this.mouseX, this.mouseY, isMouseButtonDown(MouseButton.LEFT));
        }
    }

    public boolean isKeyDown(Key key) {
        return keyStates.getOrDefault(key, false);
    }

    public boolean isMouseButtonDown(MouseButton button) {
        return mouseButtonStates.getOrDefault(button, false);
    }

    private void keyCallback(long window, int keyCode, int scancode, int action, int mods) {
        Key key = Key.valueOf(keyCode);

        if (key != Key.UNKNOWN) {
            keyStates.put(key, action == GLFW.GLFW_PRESS);
        }
    }

    @Override
    public void close() {
        GLFW.glfwSetKeyCallback(window.getHandle(), null);
        GLFW.glfwSetMouseButtonCallback(window.getHandle(), null);
        GLFW.glfwSetCursorPosCallback(window.getHandle(), null);
    }
}
