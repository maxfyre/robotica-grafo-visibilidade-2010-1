/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Lucas
 */
public class Line extends Line2D.Float{
    float angle;

    public Line(Point2D p1, Point2D p2) {
        this.setLine(p1, p2);
    }



    public float getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void calculateAngle(){
        double value;
        
        value = (this.getY2()-this.getY1())/(this.getX2()-this.getX1());
        angle = (float) Math.toDegrees(Math.atan(value));
    }
}
