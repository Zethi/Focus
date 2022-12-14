package com.github.zethi.cursor;

public final class CursorLocation {
    private final double X;
    private final double Y;
    public CursorLocation(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

}
