package rasterops;

import objectdata.Line;
import objectdata.Point;
import objectdata.Polygon;

public class Clipper {
    Polygon clipper;

    public Clipper(Polygon polygon) {
        this.clipper = polygon;
    }
    public Polygon clipPolygon(Polygon polygonClip) {
        Polygon out = new Polygon(polygonClip);
        for (int i = 0; i < clipper.getCount(); i++) {
            Line cutter = new Line(clipper.getPoint(i).getX(), clipper.getPoint(i).getY(), clipper.getPoint((i + 1) % clipper.getCount()).getX(), clipper.getPoint((i + 1) % clipper.getCount()).getY());
            polygonClip = new Polygon(out);
            out.clearPoints();
            Point v1 = new Point(polygonClip.getPoint(polygonClip.getCount() - 1).getX(), polygonClip.getPoint(polygonClip.getCount() - 1).getY());
            for (int j = 0; j < polygonClip.getCount(); j++) {
                Point v2 = new Point(polygonClip.getPoint(j).getX(), polygonClip.getPoint(j).getY());
                if (cutter.isInside(v2)) {
                    if (!cutter.isInside(v1))
                        out.addPoint(cutter.isIntersection(v1, v2));
                    out.addPoint(v2);
                } else {
                    if (cutter.isInside(v1)) {
                        out.addPoint(cutter.isIntersection(v1, v2));
                    }
                    v1 = v2;
                }
            }
        }
        return out;
    }
}
