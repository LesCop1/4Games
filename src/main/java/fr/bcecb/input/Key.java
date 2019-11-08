package fr.bcecb.input;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public enum Key {
    UNKNOWN(GLFW_KEY_UNKNOWN),
    FULLSCREEN(GLFW_KEY_F),
    BACK(GLFW_KEY_ESCAPE),
    LEFT(GLFW_KEY_LEFT),
    RIGHT(GLFW_KEY_RIGHT);

    private int glfwKey;

    Key(int glfwKey) {
        this.glfwKey = glfwKey;
    }

    public void setGlfwKey(int glfwKey) {
        this.glfwKey = glfwKey;
    }

    public int getGLFWKey() {
        return glfwKey;
    }

    public static Key valueOf(int glfwKey) {
        return Arrays.stream(Key.values()).filter(k -> k.getGLFWKey() == glfwKey).findFirst().orElse(Key.UNKNOWN);
    }
}