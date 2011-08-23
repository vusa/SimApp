package org.simapp.floor;

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

    public PathWay(Coordinates start, Coordinates end, WayOrientation orientation, String joinAtStart, String joinAtEnd, String ... junction) {
        this.start = start;
        this.end = end;
        this.orientation = orientation;
        this.joinAtStart = joinAtStart;
        this.joinAtEnd = joinAtEnd;
    }
    
    public PathWay(String name, Coordinates start, Coordinates end, WayOrientation orientation, String joinAtStart, String joinAtEnd, String ... junction) {
        this.pathName = name;
        this.start = start;
        this.end = end;
        this.orientation = orientation;
        this.joinAtStart = joinAtStart;
        this.joinAtEnd = joinAtEnd;
    }
}
