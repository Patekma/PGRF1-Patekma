package objectdata;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> points;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public void clearPoints() {
        points.clear();
    }

    public int getCount() {
        return points.size();
    }

    public List<Point> getPoints() {
        return points;
    }
}
