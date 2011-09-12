package org.simapp.floor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author vusa
 */
public class PathWay {

    Coordinates start;
    Coordinates end;
    WayOrientation orientation;
    String joinAtStart;
    String joinAtEnd;
    String pathName;

    private PathWay() {
    }

    public PathWay(Coordinates start, Coordinates end, WayOrientation orientation, String joinAtStart, String joinAtEnd, String... junction) {
        this.start = start;
        this.end = end;
        this.orientation = orientation;
        this.joinAtStart = joinAtStart;
        this.joinAtEnd = joinAtEnd;
    }

    public PathWay(String name, Coordinates start, Coordinates end, WayOrientation orientation, String joinAtStart, String joinAtEnd, String... junction) {
        this.pathName = name;
        this.start = start;
        this.end = end;
        this.orientation = orientation;
        this.joinAtStart = joinAtStart;
        this.joinAtEnd = joinAtEnd;
    }
    
        public void save(Database db) throws SQLException {
        StringBuilder sb = new StringBuilder("insert into pathway (name, startx, starty, endx, endy, joinatstart, joinatend, orientation) values(");
        sb.append("'").append(pathName).append("'");
        sb.append(",");
        sb.append(start.x);
        sb.append(",");
        sb.append(start.y);
        sb.append(",");
        sb.append(end.x);
        sb.append(",");
        sb.append(end.y);
        sb.append(",");
        sb.append("'").append(joinAtStart).append("'");
        sb.append(",");
        sb.append("'").append(joinAtEnd).append("'");
        sb.append(",");
        sb.append("'").append(orientation.name()).append("'");
        sb.append(")");
        db.execute(sb.toString());
    }

    static PathWay get(Database db, String name) throws SQLException {
        StringBuilder sb = new StringBuilder("select * from pathway where name=");
        sb.append("'").append(name).append("'");
        ArrayList<HashMap<String, Object>> res = db.query(sb.toString());
        if (res.size() == 1) {
            HashMap map = res.get(0);
            PathWay p = new PathWay();
            p.pathName = (String) map.get("name");
            p.start = new Coordinates((Integer) map.get("startx"), (Integer) map.get("starty"));
            p.end = new Coordinates((Integer) map.get("endx"), (Integer) map.get("endy"));
            p.joinAtStart = (String) map.get("joinatstart");
            p.joinAtEnd = (String) map.get("joinatend");
            p.orientation = WayOrientation.valueOf((String) map.get("orientation"));
            return p;
        }
        return null;
    }

    static List<PathWay> getAll(Database db) throws SQLException {
        String sql = "select * from pathway";
        List<PathWay> paths = new ArrayList<PathWay>();
        ArrayList<HashMap<String, Object>> res = db.query(sql);
        if (res.size() > 0) {
            for (HashMap<String, Object> map : res) {
                PathWay p = new PathWay();
                p.pathName = (String) map.get("name");
                p.start = new Coordinates((Integer) map.get("startx"), (Integer) map.get("starty"));
                p.end = new Coordinates((Integer) map.get("endx"), (Integer) map.get("endy"));
                p.joinAtStart = (String) map.get("joinatstart");
                p.joinAtEnd = (String) map.get("joinatend");
                p.orientation = WayOrientation.valueOf((String) map.get("orientation"));
                paths.add(p);
            }
        }
        return paths;
    }
}
