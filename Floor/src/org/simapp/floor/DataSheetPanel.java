package org.simapp.floor;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author vusa
 */
public class DataSheetPanel extends JPanel {

    public JComboBox combo = new JComboBox();
    public JLabel liveLbl = new JLabel();

    public DataSheetPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Data Sheet"));
        add(combo);
        add(liveLbl);
        combo.setDoubleBuffered(true);
    }
}
