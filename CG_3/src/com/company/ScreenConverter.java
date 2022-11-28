package com.company;

public class ScreenConverter {

    private double cx, cy, rw, rh;
    private int sw, sh;

    public ScreenConverter(double cx, double cy, double rw, double rh, int sw, int sh) {
        this.cx = cx;
        this.cy = cy;
        this.rw = rw;
        this.rh = rh;
        this.sw = sw;
        this.sh = sh;
    }


    public ScreenPoint s2r(RealPoint p) {
        double x = (p.getX() - cx)/rw *sw;
        double y = (cy - p.getY())/rh *sh;

        return new ScreenPoint((int)x, (int)y);
    }

    public void moveCorner(RealPoint delta){
        cx += delta.getX();
        cy += delta.getY();
    }

    public void  changeScale(double s){
        rw*=s;
        rh*=s;
        cx *=s;
        cy *=s;
    }

    public RealPoint s2r(ScreenPoint p) {
        double x = p.getC() *rw / sw + cx;
        double y = cy - p.getR() * rh/ sh;

        return new RealPoint(x, y);
    }

    public void setSw(int sw) {
        this.sw = sw;
    }

    public void setSh(int sh) {
        this.sh = sh;
    }
}
