/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mouselisteners;

import util.ObjectsControl;
import util.Obstacle;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Lucas
 */
public class DrawObjectsListener extends MouseAdapter {

    ObjectsControl objectsControl;
    Point pStart, pEnd;
    boolean startPointPlaced = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (!startPointPlaced) {
                pStart = e.getPoint();
                startPointPlaced = true;
            } else {
                objectsControl.getCurrentObstacle().add(new Point(pStart), new Point(pEnd));
                pStart = e.getPoint();
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            objectsControl.finishObject();
            if(!objectsControl.getCurrentObstacle().isError()){
                objectsControl.addObstacle(objectsControl.getCurrentObstacle());
                objectsControl.resetObstacle();
                startPointPlaced = false;
            }
            
            objectsControl.repaintWorld();

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (startPointPlaced) {
            pEnd = null;
            objectsControl.getCurrentObstacle().getCurrentLine().setLine(0, 0, 0, 0);
            objectsControl.repaintWorld();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (startPointPlaced) {
            pEnd = e.getPoint();
            objectsControl.getCurrentObstacle().getCurrentLine().setLine(pStart.x, pStart.y, pEnd.x, pEnd.y);
            objectsControl.repaintWorld();
        }
    }

    public void setObjectsControl(ObjectsControl objectsControl) {
        this.objectsControl = objectsControl;
    }
}
