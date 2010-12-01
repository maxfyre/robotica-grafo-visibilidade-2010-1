/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Point;
import java.awt.geom.Line2D;

/**
 *
 * @author Lucas
 */
public class Line2 {

    private Point start;
    private Point end;
    private int a, b, c;
    private float m, q;
    private float angle;

    public Line2(Point start, Point end) {
        this.start = start;
        this.end = end;
        initLine();
    }

    private void initLine() {
        a = start.y - end.y;
        b = end.x - start.x;
        c = start.x * end.y - end.x * start.y;

        q = -(c / b);
        m = -(a / b);
        //y = mx + q
    }

    public Point calculatePointIntersection(Line2 l) {
        boolean intersec = Line2D.linesIntersect(this.start.x, this.start.y, this.end.x, this.end.y,
                l.getStart().x, l.getStart().y, l.getEnd().x, l.getEnd().y);
        if (intersec) {
            int x;
            int y;
            x = (int) ((l.getQ() - this.q) / (this.m - l.getM()));
            y = (int) ((this.m * x) + this.q);
            return new Point(x, y);
        } else {
            return null;
        }
    }

    public static Point calculatePointIntersection(Point start, Point end, Line2 l) {
        boolean intersec = Line2D.linesIntersect(start.x, start.y, end.x, end.y,
                l.getStart().x, l.getStart().y, l.getEnd().x, l.getEnd().y);
        if (intersec) {
            int x;
            int y;
            int a = start.y - end.y;
            int b = end.x - start.x;
            int c = start.x * end.y - end.x * start.y;

            float q = -(c / b);
            float m = -(a / b);

            x = (int) ((l.getQ() - q) / (m - l.getM()));
            y = (int) ((m * x) + q);

            return new Point(x, y);
        } else {
            return null;
        }
    }

    public void calculateAngle() {
        double value;

        value = (this.end.y - this.start.y) / (this.end.x - this.start.x);
        angle = (float) Math.toDegrees(Math.atan(value));
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public float getM() {
        return m;
    }

    public float getQ() {
        return q;
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public int getX1(){
        return start.x;
    }

    public int getX2(){
        return end.x;
    }

    public int getY1(){
        return start.y;
    }

    public int getY2(){
        return end.y;
    }
}
