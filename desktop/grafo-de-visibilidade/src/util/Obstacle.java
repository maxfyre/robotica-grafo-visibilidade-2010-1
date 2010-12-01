/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas
 */
public class Obstacle {

    private ArrayList<Point> setOfPoints = new ArrayList<Point>();
    private ArrayList<Line2D> linesObject = new ArrayList<Line2D>();
    private ArrayList<Line2D> tempLinesObject = new ArrayList<Line2D>();
    private Line2D.Float currentLine = new Line2D.Float();
    private float tx, ty;
    private boolean error = false;

    public Obstacle() {
    }

    public void add(Point p1, Point p2) {
        tempLinesObject.add(new Line2D.Float(p1, p2));
    }

    public void finishObject() {
        if (tempLinesObject.size() > 1) {
            Point2D temp = tempLinesObject.get(tempLinesObject.size() - 1).getP2();
            Point p1 = new Point((int) temp.getX(), (int) temp.getY());

            temp = tempLinesObject.get(0).getP1();
            Point p2 = new Point((int) temp.getX(), (int) temp.getY());

            tempLinesObject.add(new Line2D.Float(p1, p2));
            linesObject.addAll(tempLinesObject);
            createSetOfPoints();
            tempLinesObject.clear();
            error = false;
        } else {
            this.clear();
            JOptionPane.showMessageDialog(null, "O objeto deve ter pelo menos 2 arestas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            error = true;
        }
    }

    private void createSetOfPoints() {

        for (int i = 0; i < tempLinesObject.size(); i++) {
            Line2D line2D = tempLinesObject.get(i);
            Point temp = new Point((int) line2D.getX1(), (int) line2D.getY1());
            if(!setOfPoints.contains(temp)){
                setOfPoints.add(temp);
            }
        }

        calculateCenter();

    }

    private void calculateCenter() {
        float a = 0;
        setOfPoints.add(new Point(setOfPoints.get(0)));
        for (int i = 0; i < setOfPoints.size() - 1; i++) {
            Point p1 = setOfPoints.get(i);
            Point p2 = setOfPoints.get(i + 1);
            a += ((p1.x * p2.y) - (p1.y * p2.x));
            tx += ((p2.x + p1.x) * ((p1.x * p2.y) - (p1.y * p2.x)));
            ty += ((p2.y + p1.y) * ((p1.x * p2.y) - (p1.y * p2.x)));
        }
        setOfPoints.remove(setOfPoints.size() - 1);

        tx /= (3 * a);
        ty /= (3 * a);
    }

    public void expand(float sx, float sy) {
        for (int i = 0; i < setOfPoints.size(); i++) {
            Point point = setOfPoints.get(i);
            int x = (int) (point.x * sx + tx * (1 - sx));
            int y = (int) (point.y * sy + ty * (1 - sy));

            point.setLocation(x, y);
        }
        tempLinesObject.clear();
        //até a penultima linha
        for (int i = 0; i < linesObject.size() - 1; i++) {
            Line2D line2D = linesObject.get(i);
            tempLinesObject.add(new Line2D.Float(line2D.getP1(), line2D.getP2()));
            line2D.setLine(setOfPoints.get(i), setOfPoints.get(i + 1));
        }

        //Ultima linha
        Line2D line2D = linesObject.get(linesObject.size() - 1);
        tempLinesObject.add(new Line2D.Float(line2D.getP1(), line2D.getP2()));
        line2D.setLine(setOfPoints.get(setOfPoints.size() - 1),
                setOfPoints.get(0));


    }

    public boolean intersectsLine(Point start, Point end, Line2 l) {
        Point p = Line2.calculatePointIntersection(start, end, l);
        if(p == null){
            System.out.println("null");
            return false;
        }else if(setOfPoints.contains(p)){
            System.out.println("contem");
            return false;
        }else{
            System.out.println("intercepta");
            return true;
        }
//        if (!setOfPoints.contains(end) && setOfPoints.contains(start)) {
//            for (int i = 0; i < linesObject.size(); i++) {
//                Line2D line2D = linesObject.get(i);
//                if (line2D.intersectsLine(start.x-1, start.y-1, end.x, end.y) ||
//                    line2D.intersectsLine(start.x+1, start.y+1, end.x, end.y)) {
//                    return true;
//                }
//            }
//            return false;
//        } else if (!setOfPoints.contains(start) && setOfPoints.contains(end)) {
//            for (int i = 0; i < linesObject.size(); i++) {
//                Line2D line2D = linesObject.get(i);
//                if (line2D.intersectsLine(start.x, start.y, end.x - 1, end.y - 1)) {
//                    return true;
//                }
//            }
//            return false;
//        } else if (setOfPoints.contains(start) && setOfPoints.contains(end)) {
//            return false;
//        } else {
//            for (int i = 0; i < linesObject.size(); i++) {
//                Line2D line2D = linesObject.get(i);
//                if (line2D.intersectsLine(start.x, start.y, end.x, end.y)) {
//                    return true;
//                }
//            }
//        }
//        return false;
    }

//    boolean intersectsLine(Point2D p1, Point2D p2) {
//        Point point1 = new Point((int)p1.getX(), (int)p1.getY());
//        Point point2 = new Point((int)p2.getX(), (int)p2.getY());
//        return this.intersectsLine(point1, point2);
//    }

    public void drawObstacle(Graphics2D g) {
        draw(g);
    }

    public void drawTempObstacle(Graphics2D g) {
        draw(g, tempLinesObject);
    }

    public void drawCurrentLine(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        g.drawLine((int) currentLine.getX1(), (int) currentLine.getY1(),
                (int) currentLine.getX2(), (int) currentLine.getY2());
    }

    public void drawPoints(Graphics2D g) {
        for (int i = 0; i < setOfPoints.size(); i++) {
            Point point = setOfPoints.get(i);
            g.fillOval(point.x - 3, point.y - 3, 7, 7);
        }
    }

    private void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        for (int i = 0; i < linesObject.size(); i++) {
            Line2D line2D = linesObject.get(i);
            //g.drawString(line2D.getP1().toString(), (float) line2D.getX1(), (float) line2D.getY1());
            g.drawLine((int) line2D.getX1(), (int) line2D.getY1(),
                    (int) line2D.getX2(), (int) line2D.getY2());
            //g.drawString(line2D.getP2().toString(), (float) line2D.getX2(), (float) line2D.getY2());
        }
    }

    private void draw(Graphics2D g, ArrayList<Line2D> linesObject) {
        g.setStroke(new BasicStroke(3));
        for (int i = 0; i < linesObject.size(); i++) {
            Line2D line2D = linesObject.get(i);
            g.drawLine((int) line2D.getX1(), (int) line2D.getY1(), (int) line2D.getX2(), (int) line2D.getY2());
        }
    }

    public void clear() {
        linesObject.clear();
        tempLinesObject.clear();
        setOfPoints.clear();
        currentLine = new Line2D.Float();
    }

    public boolean isVertice(int pX, int pY){
        if(setOfPoints.contains(new Point(pX, pY)))
            return true;
        else
         return false;
    }

    public boolean isVertice(Point2D P){
        if(setOfPoints.contains(P))
            return true;
        else
         return false;
    }

    public ArrayList<Line2D> getLinesObjects() {
        return linesObject;
    }

    public ArrayList<Point> getSetOfPoints() {
        return setOfPoints;
    }

    public Float getCurrentLine() {
        return currentLine;
    }

    public boolean isError() {
        return error;
    }
}
