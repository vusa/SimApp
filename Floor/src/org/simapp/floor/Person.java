package org.simapp.floor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author vusa
 */
public class Person extends FloorItem {

    PathWay currentPathWay;
    int dirX;
    int dirY;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    private Person() {
    }

    public Person(int x, int y) {
        super(Constants.MOVING_OBJECT_IMAGE, x, y);
        dirX = Math.random() < 0.5 ? 1 : -1;
        dirY = Math.random() < 0.5 ? 1 : -1;
    }

    public Person(PathWay path, int _id, String _name) {
        name = _name;
        id = _id;
        if (WayOrientation.HORIZONTAL.equals(path.orientation)) {
            setY(path.start.y);
            dirY = 0;
            setX(1 + path.start.x + (int) (Math.random() * (path.end.x - path.start.x - 2)));
            dirX = Math.random() < 0.5 ? 1 : -1;
        } else {
            setX(path.start.x);
            dirX = 0;
            setY(1 + path.start.y + (int) (Math.random() * (path.end.y - path.start.y - 2)));
            dirY = Math.random() < 0.5 ? 1 : -1;
        }
        setImage(Constants.MOVING_OBJECT_IMAGE);
        currentPathWay = path;
    }

    public void move() {
        setX(getX() + dirX);
        setY(getY() + dirY);
        checkBoundariesAndCollision();
    }

    private void turn(PathWay path) {
        currentPathWay = path;
        if (WayOrientation.HORIZONTAL.equals(path.orientation)) {
            dirY = 0;
            if (getX() <= path.start.x) {
                dirX = 1;
            } else if (getX() >= path.end.x) {
                dirX = -1;
            } else {
                dirX = Math.random() < 0.5 ? 1 : -1;
            }
        } else if (WayOrientation.VERTICAL.equals(path.orientation)) {
            dirX = 0;
            if (getY() <= path.start.y) {
                dirY = 1;
            } else if (getY() >= path.end.y) {
                dirY = -1;
            } else {
                dirY = Math.random() < 0.5 ? 1 : -1;
            }
        }
    }

    private void checkBoundariesAndCollision() {
        //log.log(Level.INFO, "x={0} y={1} path.start.x={2} path.start.y={3} path.end.x={4} path.end.y={5} path.orientation={6}", new Object[]{getX(), getY(), currentPathWay.start.x, currentPathWay.start.y, currentPathWay.end.x, currentPathWay.end.y, currentPathWay.orientation});
        if (currentPathWay.orientation.equals(WayOrientation.HORIZONTAL)) {
            if (getX() == currentPathWay.start.x) {
                if (!currentPathWay.joinAtStart.isEmpty()) {
                    turn(Board.PATHWAYS.get(currentPathWay.joinAtStart));
                } else {
                    dirX *= -1;
                }
                move();
            } else if (getX() == currentPathWay.end.x) {
                if (!currentPathWay.joinAtEnd.isEmpty()) {
                    turn(Board.PATHWAYS.get(currentPathWay.joinAtEnd));
                } else {
                    dirX *= -1;
                }
                move();
            }
        }
        if (currentPathWay.orientation.equals(WayOrientation.VERTICAL)) {
            if (getY() == currentPathWay.start.y) {
                if (!currentPathWay.joinAtStart.isEmpty()) {
                    turn(Board.PATHWAYS.get(currentPathWay.joinAtStart));
                } else {
                    dirY *= -1;
                }
                move();
            } else if (getY() == currentPathWay.end.y) {
                if (!currentPathWay.joinAtEnd.isEmpty()) {
                    turn(Board.PATHWAYS.get(currentPathWay.joinAtEnd));
                } else {
                    dirY *= -1;
                }
                move();
            }
        }
    }

    @Override
    public String toString() {
        return name + " (Tag - " + id + ")";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void save(Database db) throws SQLException {
        StringBuilder sb = new StringBuilder("insert into person (id, name, dirx, diry, lastx, lasty, pathway) values(");
        sb.append(id);
        sb.append(",");
        sb.append("'").append(name).append("'");
        sb.append(",");
        sb.append(dirX);
        sb.append(",");
        sb.append(dirY);
        sb.append(",");
        sb.append(getX());
        sb.append(",");
        sb.append(getY());
        sb.append(",");
        sb.append("'").append(currentPathWay.pathName).append("'");
        sb.append(")");
        db.execute(sb.toString());
    }

    public void update(Database db) throws SQLException {
        StringBuilder sb = new StringBuilder("update person set ");
        sb.append(" name='").append(name).append("'");
        sb.append(" dirx=").append(dirX);
        sb.append(" diry=").append(dirY);
        sb.append(" lastx=").append(getX());
        sb.append(" lasty=").append(getY());
        sb.append(" pathway='").append(currentPathWay.pathName).append("'");
        sb.append("where id=").append(id);
        sb.append(id);
        db.execute(sb.toString());
    }

    public static Person get(Database db, int id) throws SQLException {
        StringBuilder sb = new StringBuilder("select * from person where id=");
        sb.append(id);
        ArrayList<HashMap<String, Object>> res = db.query(sb.toString());
        if (res.size() == 1) {
            HashMap map = res.get(0);
            Person p = new Person();
            p.id = (Integer) map.get("id");
            p.name = (String) map.get("name");
            p.dirX = (Integer) map.get("dirx");
            p.dirY = (Integer) map.get("diry");
            p.setX((Integer) map.get("lastx"));
            p.setY((Integer) map.get("lasty"));
            p.currentPathWay = PathWay.get(db, (String) map.get("pathway"));
            p.setImage(Constants.MOVING_OBJECT_IMAGE);
            return p;
        }
        return null;
    }

    public static List<Person> getAll(Database db) throws SQLException {
        String sql = "select * from person";
        List<Person> persons = new ArrayList<Person>();
        ArrayList<HashMap<String, Object>> res = db.query(sql);
        if (res.size() > 0) {
            for (HashMap<String, Object> map : res) {
                Person p = new Person();
                p.id = (Integer) map.get("id");
                p.name = (String) map.get("name");
                p.dirX = (Integer) map.get("dirx");
                p.dirY = (Integer) map.get("diry");
                p.setX((Integer) map.get("lastx"));
                p.setY((Integer) map.get("lasty"));
                p.currentPathWay = PathWay.get(db, (String) map.get("pathway"));
                p.setImage(Constants.MOVING_OBJECT_IMAGE);
                persons.add(p);
            }
        }
        return persons;
    }
}