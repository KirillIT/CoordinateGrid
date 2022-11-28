package com.company;

import java.awt.*;

public class DrawUtils {

    static public void drawString(Graphics2D g, ScreenConverter sc, StringData str){

        ScreenPoint p = sc.s2r(str.getPoint());
        g.drawString(str.getStr(), p.getC(), p.getR());

    }

    static public void drawLine(Graphics2D g, ScreenConverter sc, LineData l, Color color, int X, int Y){
        ScreenPoint p1 = sc.s2r(l.getP1());
        ScreenPoint p2 = sc.s2r(l.getP2());
        DrawUtils.drawLineBresenham(g, p1.getC(), p1.getR(), p2.getC(), p2.getR(), color, X, Y);
    }

    static private void drawLineBresenham(Graphics2D g, int x1, int y1, int x2, int y2, Color color, int X, int Y){

        if (x1 > X && x2 > X ) {return;} //оптимизация
        if (y1 > Y && y2 > Y ) {return;}
        if (x1 < 0 && x2 < 0 ) {return;}
        if (y1  < 0 && y2 < 0 ) {return;}

        if (x1 < 0) {x1 = 0;} //оптимизация
        if (x1 > X) {x1 = X;}
        if (y1 < 0) {y1 = 0;}
        if (y1 > Y) {y1 = Y;}
        if (x2 < 0) {x2 = 0;}
        if (x2 > X) {x2 = X;}
        if (y2 < 0) {y2 = 0;}
        if (y2 > Y) {y2 = Y;}

        g.setColor(color);

        int x, y, stepX, stepY, deltaX, deltaY, realDeltaX, realDeltaY, error;

        int projectionX = x2 - x1;
        int projectionY = y2 - y1;

        stepX = sign(projectionX);
        stepY = sign(projectionY);

        if (projectionX < 0) {
            projectionX = -projectionX;
        }

        if (projectionY < 0) {
            projectionY = -projectionY;
        }

        if (projectionX > projectionY) {
            deltaX = stepX;
            deltaY = 0;
            realDeltaX = projectionY;
            realDeltaY = projectionX;
        } else {
            deltaX = 0;
            deltaY = stepY;
            realDeltaX = projectionX;
            realDeltaY = projectionY;
        }

        x = x1;
        y = y1;

        error = realDeltaY/2;
        g.fillOval(x, y, 1, 2);


        for (int t = 0; t < realDeltaY; t++) {

            error -= realDeltaX;

            if (error < 0) {
                error += realDeltaY;
                x += stepX;
                y += stepY;
            } else {
                x += deltaX;
                y += deltaY;
            }
            g.fillOval(x, y, 1, 2);


        }
    }

    private static int sign(int x) {
        return Integer.compare(x, 0);
    }
}
