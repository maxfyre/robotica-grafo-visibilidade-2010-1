/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import gui.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Lucas
 */
public class ObjectsControl {
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private Obstacle currentObstacle;
    private World world;
    private float sx, sy;
    private boolean expandedObjects = false;
    private ArrayList<Point> listOfExpandedPoints = new ArrayList<Point>();
    private Point pStart = new Point();
    private Point pEnd = new Point();
    


    public ObjectsControl() {
        currentObstacle = new Obstacle();
    }

    public void finishObject() {
        currentObstacle.finishObject();
    }

    public void addObstacle(Obstacle o){
        obstacles.add(o);
    }

    public void resetObstacle(){
        currentObstacle = new Obstacle();
    }

    public void repaintWorld() {
        world.repaint();
    }

    public void expandObjects(int size){
        //TODO: Flexibilizar o tamanho da arena

        size = 2*size;
        Rectangle r = new Rectangle(200, 200, 50, 50);
        Rectangle r2 = new Rectangle(r);
        r2.grow(size, size);

        sx = (float)r2.width/(float)r.width;
        sy = (float)r2.height/(float)r.height;

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            obstacle.expand(sx, sy);
        }

        expandedObjects = true;
    }

    public void createListOfExtendedPoints(){
        listOfExpandedPoints.add(pStart);
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            listOfExpandedPoints.addAll(obstacle.getSetOfPoints());
        }
        listOfExpandedPoints.add(pEnd);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Obstacle getCurrentObstacle() {
        return currentObstacle;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public boolean isExpandedObjects() {
        return expandedObjects;
    }

    public Point getpEnd() {
        return pEnd;
    }

    public void setpEnd(Point pEnd) {
        this.pEnd = pEnd;
    }

    public Point getpStart() {
        return pStart;
    }

    public void setpStart(Point pStart) {
        this.pStart = pStart;
    }

    public ArrayList<Point> getListOfExpandedPoints() {
        return listOfExpandedPoints;
    }
}
