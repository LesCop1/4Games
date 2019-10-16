package fr.bcecb.util;

import java.util.Objects;

public class BoundingBox {
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public BoundingBox(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public BoundingBox(BoundingBox boundingBox) {
        this(boundingBox.minX, boundingBox.minY, boundingBox.maxX, boundingBox.maxY);
    }

    public boolean checkCoordinates(double x, double y) {
        return minX <= x && maxX >= x && minY <= y && maxY >= y;
    }

    public double getSizeX() {
        return Math.abs(maxX - minX);
    }

    public double getSizeY() {
        return Math.abs(maxY - minY);
    }

    public Point getCenter() {
        return new Point((maxX - minX) / 2, (maxY - minY) / 2);
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
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
}
