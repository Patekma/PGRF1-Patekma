package rasterops.fill;

import objectdata.Edge;
import objectdata.Line;
import objectdata.Point;
import objectdata.Polygon;
import rasterdata.Raster;
import rasterops.Liner;
import rasterops.LinerTrivial;
import rasterops.Polygoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine{

    public void fill(Raster img, Polygon polygon, int fillColor, int edgeColor, Polygoner polygoner, LinerTrivial linerTrivial){
        //seznam hran
        List<Edge> edges = new ArrayList<>();

        //projdou se body a vyzvoří se z nich hrany
        for (int i = 0; i < polygon.getCount(); i++) {
            int nextIndex = (i + 1) % polygon.getCount();
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint(nextIndex);
            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            if (!edge.isHorizontal()) {
                edge.orientate();
                edge.shorten();
                edges.add(edge);
            }
        }

        double yMin = polygon.getPoint(0).getY();
        double yMax = yMin;
        for (Point p : polygon.getPoints()) {
            if (yMin > p.getY()) {
                yMin = p.getY();
            }
            if (yMax < p.getY()) {
                yMax = p.getY();
            }
        }

        for (double y = yMin; y <= yMax; y++) {

            List<Integer> prusecik = new ArrayList<>();

            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    prusecik.add(edge.getIntersection(y));
                }
            }

            Collections.sort(prusecik);

            for (int i = 0; i < prusecik.size()-1; i += 2) {
                linerTrivial.drawLine(img, prusecik.get(i), y, prusecik.get(i+1), y, fillColor);
            }
        }
        polygoner.rasterize(img, polygon, linerTrivial, edgeColor);

    }
}
