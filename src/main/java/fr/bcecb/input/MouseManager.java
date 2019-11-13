package fr.bcecb.input;

import fr.bcecb.Game;
import fr.bcecb.event.Event;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;

import static org.lwjgl.glfw.GLFW.*;

public class MouseManager {
    private float positionX;
    private float positionY;

    public MouseManager(Window window) {
        glfwSetMouseButtonCallback(window.getId(), this::mouseButtonCallback);
        glfwSetCursorPosCallback(window.getId(), this::mouseMoveCallback);
        glfwSetScrollCallback(window.getId(), this::mouseScrollCallback);
    }

    private void mouseButtonCallback(long window, int button, int action, int mods) {
        MouseEvent.Click.Type type = action == GLFW_PRESS ? MouseEvent.Click.Type.PRESSED : MouseEvent.Click.Type.RELEASED;

        Event event = new MouseEvent.Click(type, button, positionX, positionY);
        Game.EVENT_BUS.post(event);
    }

    private void mouseMoveCallback(long window, double x, double y) {
        this.positionX = (float) x / Window.getCurrentWindow().getContentScaleX();
        this.positionY = (float) y / Window.getCurrentWindow().getContentScaleY();

        Event event = new MouseEvent.Move(positionX, positionY);
        Game.EVENT_BUS.post(event);
    }

    private void mouseScrollCallback(long window, double x, double y) {
        Event event = new MouseEvent.Scroll((float) x, (float) y);
        Game.EVENT_BUS.post(event);
    }
}
