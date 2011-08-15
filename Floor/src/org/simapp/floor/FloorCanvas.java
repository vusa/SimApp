package org.simapp.floor;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

/**
 *
 * @author vusa
 */
public class FloorCanvas extends JComponent {

    private final MouseL mouseListener = new MouseL();

    public FloorCanvas() {
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        setBackground(Color.WHITE);
        setFocusable(true);
    }


    public void clear() {
        repaint();
    }

  
    private final class MouseL extends MouseAdapter implements MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
               //set nearest sprite position maybe?
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseClicked(e);
        }
    }
}
