package org.simapp.floor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.openide.util.Exceptions;

/**
 *
 * @author vusa
 */
public class Board extends JPanel implements Runnable {

    //private Image star;
    private FloorTopComponent parent;
    private Thread animator;
    public static Map<String, PathWay> PATHWAYS;
    Map<Integer, Person> persons;
    private List<Sensor> sensors;
    private BufferedImage floorMap;
    public int DELAY = 50;

    public Board(FloorTopComponent _parent) {
        parent = _parent;
        setBackground(Color.BLACK);
        setSize(650, 490);
        setDoubleBuffered(true);
        try {
            floorMap = ImageIO.read(new File("/home/vusa/Work/NetBeansProjects/SimApp/Floor/src/org/simapp/floor/" + Constants.FLOOR_MAP_IMAGE));
            initPathWays();
            initPersons();
            initSensors();
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InstantiationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ioe) {
            throw new RuntimeException(Constants.FLOOR_MAP_IMAGE + " Not found");
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(floorMap, 1, 1, this);

        for (Person p : persons.values()) {
            g2d.drawImage(p.getSprite(), p.getX(), p.getY(), this);
        }
        for (Sensor s : sensors) {
            g2d.drawImage(s.getSprite(), s.getX(), s.getY(), this);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void cycle() {

        for (Person p : persons.values()) {
            p.move();
        }

        int selectedId = ((Person) parent.dataSheet.combo.getSelectedItem()).getId();
        Person p = persons.get(selectedId);
        parent.dataSheet.liveLbl.setText(p.getName() + " is along " + p.currentPathWay.pathName.toUpperCase());
        parent.dataSheet.liveLbl2.setText("Person ID: " + selectedId + " X = " + p.getX() + " and Y =" + p.getY());
        double delta1, delta2;
        Sensor s1 = sensors.get(0);
        delta1 = Math.sqrt(Math.pow((s1.getX() - p.getX()), 2) + Math.pow((s1.getY() - p.getY()), 2));
        Sensor s2 = sensors.get(1);
        delta2 = Math.sqrt(Math.pow((s2.getX() - p.getX()), 2) + Math.pow((s2.getY() - p.getY()), 2));
        parent.dataSheet.liveLbl3.setText("Distance from sensor1: " + (int) delta1);
        parent.dataSheet.liveLbl4.setText("Distance from sensor2: " + (int) delta2);

    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
    }

    void initPersons() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        persons = new HashMap<Integer, Person>();
        List<Person> pple = Person.getAll(Database.getInstance());
        parent.dataSheet.setLayout(new BoxLayout(parent.dataSheet, BoxLayout.PAGE_AXIS));
        for (Person p : pple) {
            persons.put(p.getId(), p);
        }
        parent.dataSheet.combo.setModel(getComboModel());
    }

    void initPathWays() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        PATHWAYS = new HashMap<String, PathWay>();
        List<PathWay> pw = PathWay.getAll(Database.getInstance());
        for (PathWay p : pw) {
            PATHWAYS.put(p.pathName, p);
        }
        /*      PATHWAYS.put("h1", new PathWay("h1", new Coordinates(30, 22), new Coordinates(606, 22), WayOrientation.HORIZONTAL, "v1", "v11"));
        PATHWAYS.put("h2", new PathWay("h2", new Coordinates(94, 70), new Coordinates(480, 70), WayOrientation.HORIZONTAL, "v2", "v10"));
        PATHWAYS.put("h3", new PathWay("h3", new Coordinates(158, 118), new Coordinates(480, 118), WayOrientation.HORIZONTAL, "v4", "v10"));
        PATHWAYS.put("h4", new PathWay("h4", new Coordinates(30, 166), new Coordinates(606, 166), WayOrientation.HORIZONTAL, "v1", "v11"));
        PATHWAYS.put("h5", new PathWay("h5", new Coordinates(222, 310), new Coordinates(350, 310), WayOrientation.HORIZONTAL, "v6", "v8"));
        PATHWAYS.put("h6", new PathWay("h6", new Coordinates(415, 310), new Coordinates(606, 310), WayOrientation.HORIZONTAL, "v9", "v11"));
        PATHWAYS.put("h7", new PathWay("h7", new Coordinates(30, 358), new Coordinates(94, 358), WayOrientation.HORIZONTAL, "v1", "v3"));
        PATHWAYS.put("h8", new PathWay("h8", new Coordinates(94, 406), new Coordinates(415, 406), WayOrientation.HORIZONTAL, "v3", "v9"));
        PATHWAYS.put("h9", new PathWay("h9", new Coordinates(30, 454), new Coordinates(94, 454), WayOrientation.HORIZONTAL, "v1", "v3"));
        PATHWAYS.put("h10", new PathWay("h10", new Coordinates(158, 454), new Coordinates(606, 454), WayOrientation.HORIZONTAL, "v5", "v11"));
        PATHWAYS.put("v1", new PathWay("v1", new Coordinates(30, 22), new Coordinates(30, 454), WayOrientation.VERTICAL, "h1", "h9"));
        PATHWAYS.put("v2", new PathWay("v2", new Coordinates(94, 22), new Coordinates(94, 70), WayOrientation.VERTICAL, "h1", "h2"));
        PATHWAYS.put("v3", new PathWay("v3", new Coordinates(94, 310), new Coordinates(94, 454), WayOrientation.VERTICAL, "", "h9"));
        PATHWAYS.put("v4", new PathWay("v4", new Coordinates(158, 70), new Coordinates(158, 166), WayOrientation.VERTICAL, "h2", "h4"));
        PATHWAYS.put("v5", new PathWay("v5", new Coordinates(158, 406), new Coordinates(158, 454), WayOrientation.VERTICAL, "h8", "h10"));
        PATHWAYS.put("v6", new PathWay("v6", new Coordinates(222, 166), new Coordinates(222, 310), WayOrientation.VERTICAL, "h4", "h5"));
        PATHWAYS.put("v7", new PathWay("v7", new Coordinates(288, 310), new Coordinates(288, 454), WayOrientation.VERTICAL, "h5", "h10"));
        PATHWAYS.put("v8", new PathWay("v8", new Coordinates(350, 310), new Coordinates(350, 406), WayOrientation.VERTICAL, "h5", "h8"));
        PATHWAYS.put("v9", new PathWay("v9", new Coordinates(415, 166), new Coordinates(415, 454), WayOrientation.VERTICAL, "h4", "h10"));
        PATHWAYS.put("v10", new PathWay("v10", new Coordinates(480, 22), new Coordinates(480, 166), WayOrientation.VERTICAL, "h1", "h4"));
        PATHWAYS.put("v11", new PathWay("v11", new Coordinates(606, 22), new Coordinates(606, 454), WayOrientation.VERTICAL, "h1", "h10"));
         */    }

    private void initSensors() {
        sensors = new ArrayList<Sensor>();
        sensors.add(new Sensor(10, 15));
        sensors.add(new Sensor(600, 400));
    }

    private ComboBoxModel getComboModel() {
        return new DefaultComboBoxModel(persons.values().toArray());
    }

//    private String getPersonName(int i) {
//        String[] names = new String[]{"John", "James", "Sipho", "Kevin", "Mark", "Dudu", "Dumi", "Gugu", "Thembi", "Dan"};
//        return names[i % names.length];
//    }

    public static void main(String[] args) {
//        try {
//            //Board b = new Board();
//            b.initPathWays();
//            Database db = Database.getInstance();
//            for (PathWay p : PATHWAYS.values()) {
//                p.save(db);
//            }
//            b.initPersons();
//            for (Person per : b.persons.values()) {
//                per.save(db);
//            }
//        } catch (ClassNotFoundException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (SQLException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (InstantiationException ex) {
//            Exceptions.printStackTrace(ex);
//        } catch (IllegalAccessException ex) {
//            Exceptions.printStackTrace(ex);
//        }
    }
}