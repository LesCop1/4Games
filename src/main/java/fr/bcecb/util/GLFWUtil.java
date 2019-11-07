package fr.bcecb.util;

import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.system.MemoryStack;

import javax.annotation.Nullable;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class GLFWUtil {
    public static void glfwInvoke(long window, @Nullable GLFWWindowSizeCallbackI windowSizeCB, @Nullable GLFWFramebufferSizeCallbackI framebufferSizeCB) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            if (windowSizeCB != null) {
                glfwGetWindowSize(window, w, h);
                windowSizeCB.invoke(window, w.get(0), h.get(0));
            }

            if (framebufferSizeCB != null) {
                glfwGetFramebufferSize(window, w, h);
                framebufferSizeCB.invoke(window, w.get(0), h.get(0));
            }
        }
    }
}