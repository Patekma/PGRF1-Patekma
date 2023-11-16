package objectdata;

public class Line {

    Point p1;
    Point p2;
    double x1, y1, x2, y2;


    public Line(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public boolean isInside(Point v) {
        double side = ((x2 - x1) * (v.getY() - y1) - (y2 - y1) * (v.getX() - x1));
        return side < 0;
    }

    public Point isIntersection(Point v1, Point v2) {
        double x, y;
        double xa = v2.getX();
        double ya = v2.getY();
        double xb = v1.getX();
        double yb = v1.getY();
        double v = (x1 - x2) * (ya - yb) - (y1 - y2) * (xa - xb);
        x = ((x1 * y2 - x2 * y1) * (xa - xb) - (xa * yb - xb * ya) * (x1 - x2)) / v;
        y = ((x1 * y2 - x2 * y1) * (ya - yb) - (xa * yb - xb * ya) * (y1 - y2)) / v;
        return new Point((int) Math.round(x), (int) Math.round(y));
    }
}
