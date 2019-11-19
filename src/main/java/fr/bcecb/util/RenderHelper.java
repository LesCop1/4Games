package fr.bcecb.util;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public final class RenderHelper {
    private static final Matrix4f PROJECTION_MATRIX = new Matrix4f();

    private static final TransformStack TRANSFORM_STACK = new TransformStack(16);

    private RenderHelper() {

    }

    public static void setupProjection(int width, int height, int guiScale) {
        GL11.glViewport(0, 0, width, height);

        PROJECTION_MATRIX.identity();
        PROJECTION_MATRIX.ortho2D(0, (float) width / guiScale, (float) height / guiScale, 0);

        TRANSFORM_STACK.clear();
    }

    public static Matrix4f getProjection() {
        return PROJECTION_MATRIX;
    }

    public static Transform currentTransform() {
        return TRANSFORM_STACK;
    }

    public static Transform pushTransform() {
        return TRANSFORM_STACK.pushTransform();
    }

    public static Transform popTransform() {
        return TRANSFORM_STACK.popTransform();
    }
}
