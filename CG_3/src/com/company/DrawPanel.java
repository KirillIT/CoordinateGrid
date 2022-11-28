package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private ScreenConverter sc;
    private ArrayList<LineData> linesX, linesY;
    private LineData mainX, mainY;
    private ArrayList<StringData> stringX, stringY;
    private final int X = 1044;
    private final int Y = 771;
    private static final double SCALE_STEP = 0.05;
    private static final int N_STEPS = 18;
    private int counter = 0;
    private double sumX = 0;
    private double sumY = 0;
    private int counterOnOneSide = 2;
    private double totalSize = 10;

    public DrawPanel() {
        initial();
    }

    public void initial() {

        sc = new ScreenConverter(-totalSize, totalSize, totalSize*2, totalSize*2, X, Y);
        fillInLines(counterOnOneSide, totalSize);

        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);

    }

    public void fillInLines(int counterOnOneSide, double size){

        int totalLines = counterOnOneSide * 2 + 1;
        final int coefficient = 100;

        final double constSize = size;
        linesX = new ArrayList<>(totalLines);
        linesY = new ArrayList<>(totalLines);
        stringX = new ArrayList<>(totalLines);
        stringY = new ArrayList<>(totalLines);

        for (double i = 0; i > -size*coefficient; i -= ((size *2)/((double) totalLines))){
            linesX.add(new LineData(new RealPoint(-size*coefficient, i), new RealPoint(size*coefficient, i)));
        }

        for (double i = 0; i < size*coefficient; i += ((size *2)/((double) totalLines))){
            linesX.add(new LineData(new RealPoint(-size*coefficient, i), new RealPoint(size*coefficient, i)));
        }

        mainX = new LineData(new RealPoint(-constSize*coefficient, 0), new RealPoint(constSize*coefficient, 0));
        mainY = new LineData(new RealPoint(0, -constSize*coefficient), new RealPoint(0, constSize*coefficient));

        size = size *(Y/(double)X);

        for (double i = 0; i < constSize*coefficient; i += ((size *2)/((double) totalLines))) {
            linesY.add(new LineData(new RealPoint(i, -constSize*coefficient), new RealPoint(i, constSize*coefficient)));
        }

        for (double i = 0; i > -constSize*coefficient; i -= ((size *2)/((double) totalLines))) {
            linesY.add(new LineData(new RealPoint(i, -constSize*coefficient), new RealPoint(i, constSize*coefficient)));
        }

        for (double i = 0; i < constSize*coefficient; i += ((size *2)/((double) totalLines))) {
            RealPoint rp = new RealPoint(i, 0);
            stringX.add(new StringData(rp, String.format("%.3f",i*(X/(double)Y))));
        }

        for (double i = 0; i > -constSize*coefficient; i -= ((size *2)/((double) totalLines))) {
            RealPoint rp = new RealPoint(i, 0);
            stringX.add(new StringData(rp, String.format("%.3f",i*(X/(double)Y))));

        }

        for (double i = 0; i < constSize*coefficient; i += ((constSize *2)/((double) totalLines))){
            RealPoint rp = new RealPoint(0, i);
            stringY.add(new StringData(rp, String.format("%.3f",i)));
        }

        for (double i = 0; i > -constSize*coefficient; i -= ((constSize *2)/((double) totalLines))){
            RealPoint rp = new RealPoint(0, i);
            stringY.add(new StringData(rp, String.format("%.3f",i)));
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        sc.setSw(getWidth());
        sc.setSh(getHeight());
        Graphics2D gr = (Graphics2D)g;
        gr.setColor(Color.white);
        gr.fillRect(0, 0, getWidth(), getHeight());

        for (LineData lineData : linesX) DrawUtils.drawLine(gr, sc, lineData, Color.GREEN, getWidth(), getHeight());
        for (LineData lineData : linesY) DrawUtils.drawLine(gr, sc, lineData, Color.GREEN, getWidth(), getHeight());
        for (StringData stringData : stringX) DrawUtils.drawString(gr, sc, stringData);
        for (StringData stringData : stringY) DrawUtils.drawString(gr, sc, stringData);

        gr.setColor(Color.RED);
        DrawUtils.drawLine(gr, sc, mainX, Color.RED, getWidth(), getHeight());
        DrawUtils.drawLine(gr, sc, mainY, Color.RED, getWidth(), getHeight());

        gr.dispose();
        repaint();

    }

    private ScreenPoint prevPoint = null;


    @Override
    public void mousePressed(MouseEvent e) {
        prevPoint = new ScreenPoint(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        prevPoint = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        ScreenPoint curPoint = new ScreenPoint(e.getX(), e.getY());
        RealPoint p1 = sc.s2r(curPoint);
        RealPoint p2 = sc.s2r(prevPoint);
        RealPoint delta = p2.minus(p1);
        sc.moveCorner(delta);
        sumX += delta.getX();
        sumY += delta.getY();
        prevPoint = curPoint;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        counter +=clicks;
        double coefficient = 1 + SCALE_STEP * (clicks < 0 ? -1 : 1);
        double scale = 1;
        double k = 2.406619233691;

        if (counter == N_STEPS) {


            totalSize *= k;

            sc = new ScreenConverter(-totalSize, totalSize, totalSize*2, totalSize*2, X, Y);

            fillInLines(counterOnOneSide, totalSize);

            counter = 0;
            sumX *= k;
            sumY *= k;
            RealPoint realPoint = new RealPoint(sumX, sumY);

            sc.moveCorner(realPoint);
        }

        else if (counter == -N_STEPS){

            totalSize /= k;

            sc = new ScreenConverter(-totalSize, totalSize, totalSize*2, totalSize*2, X, Y);

            fillInLines(counterOnOneSide, totalSize);
            counter = 0;
            sumX /= k;
            sumY /= k;
            RealPoint realPoint = new RealPoint(sumX, sumY);

            sc.moveCorner(realPoint);
        }

        else {

            for (int i = Math.abs(clicks); i > 0; i--) {
                scale *= coefficient;
            }

            sc.changeScale(scale);

        }

        repaint();

    }

    public void press1(){
        final int step = 3;

        totalSize += step;

        fillInLines(counterOnOneSide, totalSize);
    }

    public void press2(){

        final int step = 3;

        if (totalSize - step > 0) {
                totalSize -= step;
        }
        else {
            return;
        }

        fillInLines(counterOnOneSide, totalSize);

    }

    @Override
    public void keyPressed(KeyEvent e) {


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
}