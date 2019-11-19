package fr.bcecb.util;

public class MathHelper {
    private MathHelper() {

    }

    public static float upscaleRatio(float width, float height, float surfaceWidth, float surfaceHeight) {
        return (surfaceWidth / surfaceHeight) > (width / height) ? (surfaceWidth / width) : (surfaceHeight / height);
    }

    public static float downscaleRatio(float width, float height, float surfaceWidth, float surfaceHeight) {
        return (width / height) < (surfaceWidth / surfaceHeight) ? (surfaceWidth / width) : (surfaceHeight / height);
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(Math.min(value, max), min);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }
}
