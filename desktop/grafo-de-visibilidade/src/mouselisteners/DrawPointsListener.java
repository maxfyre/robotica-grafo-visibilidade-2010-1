/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mouselisteners;

import gui.World;
import util.ObjectsControl;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Lucas
 */
public class DrawPointsListener extends MouseAdapter{
    
    private ObjectsControl objectsControl;
    private Point pStart;
    private Point pEnd;
    private boolean button1Pressed = false;
    private boolean button3Pressed = false;

    public DrawPointsListener() {
        
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            pStart = e.getPoint();
            objectsControl.setpStart(pStart);
        }else if (e.getButton() == MouseEvent.BUTTON3){
            pEnd = e.getPoint();
            objectsControl.setpEnd(pEnd);
        }

        objectsControl.repaintWorld();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (button1Pressed) {
            pStart = e.getPoint();
            objectsControl.getpStart().setLocation(pStart);
        }else if (button3Pressed){
            pEnd = e.getPoint();
            objectsControl.getpEnd().setLocation(pEnd);
        }

        objectsControl.repaintWorld();
    }

    @Override
    public void mousePressed(MouseEvent e){
        if (e.getButton() == MouseEvent.BUTTON1) {
           button1Pressed = true;
           button3Pressed = false;
        }else if (e.getButton() == MouseEvent.BUTTON3){
            button1Pressed = false;
           button3Pressed = true;
        }

        objectsControl.repaintWorld();
    }

    public void setObjectsControl(ObjectsControl objectsControl) {
        this.objectsControl = objectsControl;
    }    
}
