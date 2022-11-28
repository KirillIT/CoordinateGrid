package com.company;

public class RealPoint {

    final private double x, y;

    public RealPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public  RealPoint minus(RealPoint p){
        return new RealPoint(getX() - p.getX(), getY() - p.getY());
    }
}
