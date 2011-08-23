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
    public JLabel liveLbl2 = new JLabel();

    public DataSheetPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Data Sheet"));
        combo.setSize(40, 15);
        add(combo);
        add(liveLbl);
        add(liveLbl2);
        combo.setDoubleBuffered(true);
        setComponentZOrder(combo, 1);
        add(new JPanel());
    }
}