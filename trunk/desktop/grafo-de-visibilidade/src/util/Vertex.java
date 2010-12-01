/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.Point;

/**
 *
 * @author Elvio
 */
public class Vertex {

    private Point id;
    private boolean wasVisited;

    public Vertex(Point id){
        this.id = id;
        wasVisited=false;
    }

    public Point getId() {
        return id;
    }

    public void setId(Point id) {
        this.id = id;
    }

    public boolean isWasVisited() {
        return wasVisited;
    }

    public void setWasVisited(boolean wasVisited) {
        this.wasVisited = wasVisited;
    }



}
