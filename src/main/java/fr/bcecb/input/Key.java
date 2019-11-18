package fr.bcecb.input;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public enum Key {
    UNKNOWN(GLFW_KEY_UNKNOWN),
    FULLSCREEN(GLFW_KEY_F),
    BACK(GLFW_KEY_ESCAPE),
    LEFT(GLFW_KEY_LEFT),
    RIGHT(GLFW_KEY_RIGHT),
    BATTLESHIP_SWAP_H(GLFW_KEY_Z),
    BATTLESHIP_SWAP_V(GLFW_KEY_A);

    private final int glfwKey;

    Key(int glfwKey) {
        this.glfwKey = glfwKey;
    }

    public static Key valueOf(int glfwKey) {
        return Arrays.stream(Key.values()).filter(k -> k.glfwKey == glfwKey).findFirst().orElse(Key.UNKNOWN);
    }
}