package fr.bcecb.util;

import java.util.function.Predicate;

public class MathHelper {
    private MathHelper() {

    }

    public static String stringifyInteger(int value) {
        return stringifyInteger(value, MathHelper::isPositiveOrZero);
    }

    public static String stringifyInteger(int value, Predicate<Integer> predicate) {
        return predicate.test(value) ? String.valueOf(value) : "";
    }

    public static boolean isPositive(int value) {
        return value > 0;
    }

    public static boolean isPositiveOrZero(int value) {
        return value >= 0;
    }

    public static boolean isNegative(int value) {
        return value < 0;
    }

    public static boolean isNegativeOrZero(int value) {
        return value <= 0;
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
