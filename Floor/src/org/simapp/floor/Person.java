package org.simapp.floor;

/**
 *
 * @author vusa
 */
public class Person extends FloorItem {

    PathWay currentPathWay;
    int dirX;
    int dirY;
    private int id;

    public int getId() {
        return id;
    }

    public Person(int x, int y) {
        super(Constants.MOVING_OBJECT_IMAGE, x, y);
        dirX = Math.random() < 0.5 ? 1 : -1;
        dirY = Math.random() < 0.5 ? 1 : -1;
    }

    public Person(PathWay path, int _id) {
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
        return "Person id - " + id + "";
    }
}