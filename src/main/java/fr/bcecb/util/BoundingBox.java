package fr.bcecb.util;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class BoundingBox {
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public BoundingBox(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public BoundingBox(BoundingBox boundingBox) {
        this(boundingBox.minX, boundingBox.minY, boundingBox.maxX, boundingBox.maxY);
    }

    public static boolean checkCoordinates(float minX, float maxX, float minY, float maxY, float x, float y) {
        return minX <= x && maxX >= x && minY <= y && maxY >= y;
    }

    public static boolean checkCoordinates(BoundingBox boundingBox, float x, float y) {
        return boundingBox.checkCoordinates(x, y);
    }

    public boolean checkCoordinates(float x, float y) {
        return minX <= x && maxX >= x && minY <= y && maxY >= y;
    }

    public float getWidth() {
        return Math.abs(maxX - minX);
    }

    public float getHeight() {
        return Math.abs(maxY - minY);
    }

    public Point getCenter() {
        return new Point(getWidth() / 2, getHeight() / 2);
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundingBox that = (BoundingBox) o;
        return getMinX() == that.getMinX() &&
                getMaxX() == that.getMaxX() &&
                getMinY() == that.getMinY() &&
                getMaxY() == that.getMaxY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMinX(), getMaxX(), getMinY(), getMaxY());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("minX", minX)
                .add("maxX", maxX)
                .add("minY", minY)
                .add("maxY", maxY)
                .toString();
    }
}
