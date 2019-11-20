package fr.bcecb.input;

import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

public enum MouseButton {
    UNKNOWN(-1),
    LEFT(GLFW.GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW.GLFW_MOUSE_BUTTON_RIGHT);

    private final int glfwKey;

    MouseButton(int glfwKey) {
        this.glfwKey = glfwKey;
    }

    public static MouseButton valueOf(int glfwKey) {
        return Arrays.stream(MouseButton.values()).filter(k -> k.glfwKey == glfwKey).findFirst().orElse(MouseButton.UNKNOWN);
    }
}