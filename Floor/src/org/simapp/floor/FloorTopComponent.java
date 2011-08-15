package org.simapp.floor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.java.dev.colorchooser.ColorChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 *
 * @author vusa
 */
@TopComponent.Description(preferredID = "FloorTopComponent",
iconBase = "/org/netbeans/floor/new_icon.png", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.netbeans.paint.FloorTopComponent")
@ActionReferences({
    @ActionReference(path = "Menu/Window", position = 0),
    @ActionReference(path = "Toolbars/File", position = 0)
})
@TopComponent.OpenActionRegistration(displayName = "#CTL_NewCanvasAction")
@Messages({
    "CTL_NewCanvasAction=New Canvas",
    "LBL_AddPerson=Add Person",
    "LBL_Foreground=Foreground",
    "LBL_Speed=Moving Speed",
    "# {0} - image",
    "SimNameFormat=New Sim {0}"})
public class FloorTopComponent extends TopComponent implements ActionListener, ChangeListener {

    private final JSlider personSpeedSlider = new JSlider(1, 24); //A slider to set the brush size
    private final JToolBar toolbar = new JToolBar(); //The toolbar
    private final ColorChooser color = new ColorChooser(); //Our color chooser component from the ColorChooser library
    private final JButton addPersonBtn = new JButton(NbBundle.getMessage(FloorTopComponent.class, "LBL_AddPerson")); //A button to clear the canvas
    private final JLabel label = new JLabel(NbBundle.getMessage(FloorTopComponent.class, "LBL_Foreground")); //A label for the color chooser
    private final JLabel speedLabel = new JLabel(NbBundle.getMessage(FloorTopComponent.class, "LBL_Speed")); //A label for the brush size slider
    //private static int speed = 1; 
    private Board board;
    public DataSheetPanel dataSheet;
    
    public FloorTopComponent() {
        initComponents();
        String displayName = NbBundle.getMessage(FloorTopComponent.class, "SimNameFormat");
        setDisplayName(displayName);
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        //Configure our components, attach listeners:
        color.addActionListener(this);
        addPersonBtn.addActionListener(this);
        personSpeedSlider.setValue(1);
        personSpeedSlider.addChangeListener(this);
        //color.setColor();
        color.setMaximumSize(new Dimension(16, 16));

        //Install the toolbar 
        add(toolbar, BorderLayout.NORTH);
        dataSheet = getDataSheet();
        board = new Board(this);
        add(new JScrollPane(board), BorderLayout.CENTER);
        add(dataSheet, BorderLayout.EAST);
        //Configure the toolbar:
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 7));
        toolbar.setFloatable(false);

        //Now populate our toolbar:
        toolbar.add(label);
        toolbar.add(color);
        toolbar.add(speedLabel);
        toolbar.add(personSpeedSlider);
        toolbar.add(addPersonBtn);

    }
    
    public DataSheetPanel getDataSheet(){
        DataSheetPanel data = new DataSheetPanel();
        data.setSize(400, 480);
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            //todo
        } else if (e.getSource() instanceof ColorChooser) {
            ColorChooser cc = (ColorChooser) e.getSource();
            //maybe we don't need colorchooser?
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //change sprite's speed - loop thru person list
    }
}
