package fr.bcecb.input;

import fr.bcecb.Event;
import fr.bcecb.Game;
import fr.bcecb.render.Window;

import static org.lwjgl.glfw.GLFW.*;

public class MouseManager {
    private static final MouseManager INSTANCE = new MouseManager();

    private double positionX, lastPositionX;
    private double positionY, lastPositionY;

    private MouseManager() {
        long id = Window.getCurrentWindow().id();
        glfwSetMouseButtonCallback(id, this::mouseButtonCallback);
        glfwSetCursorPosCallback(id, this::mouseMoveCallback);
        glfwSetScrollCallback(id, this::mouseScrollCallback);
        glfwSetCursorEnterCallback(id, this::mouseEnterCallback);
    }

    public static double getPositionX() {
        return INSTANCE.positionX;
    }

    public static double getPositionY() {
        return INSTANCE.positionY;
    }

    public static double getLastPositionX() {
        return INSTANCE.lastPositionX;
    }

    public static double getLastPositionY() {
        return INSTANCE.lastPositionY;
    }

    private void mouseButtonCallback(long window, int button, int action, int mods) {
        MouseEvent.Click.Type type = action == GLFW_PRESS ? MouseEvent.Click.Type.PRESSED : MouseEvent.Click.Type.RELEASED;

        Event event = new MouseEvent.Click(type, button, positionX, positionY);
        Game.getEventBus().post(event);
    }

    private void mouseMoveCallback(long window, double x, double y) {
        this.lastPositionX = positionX;
        this.lastPositionY = positionY;

        this.positionX = x;
        this.positionY = y;

        Event event = new MouseEvent.Move(x, y, lastPositionX - positionX, lastPositionY - positionY);
        Game.getEventBus().post(event);
    }

    private void mouseScrollCallback(long window, double x, double y) {
        Event event = new MouseEvent.Scroll(x, y);
        Game.getEventBus().post(event);
    }

    private void mouseEnterCallback(long window, boolean entered) {
        Event event = new MouseEvent.Enter(entered);
        Game.getEventBus().post(event);
    }
}
