package fr.bcecb;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    public static void main(String[] args) {
        GLFW.glfwCreateWindow(800, 600, "Main", NULL, NULL);
    }
}
