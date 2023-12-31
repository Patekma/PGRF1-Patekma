package rasterops.fill;

import objectdata.Edge;
import objectdata.Point;
import objectdata.Polygon;
import rasterdata.Raster;
import rasterops.LinerTrivial;
import rasterops.Polygoner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine{

    public void fill(Raster img, Polygon polygon, int fillColor, int edgeColor, Polygoner polygoner, LinerTrivial linerTrivial){
        //seznam hran
        List<Edge> edges = new ArrayList<>();

        //projde body a vytvori hrany
        for (int i = 0; i < polygon.getCount(); i++) {
            int nextIndex = (i + 1) % polygon.getCount();
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint(nextIndex);
            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());

            //kontrola orientace
            if (!edge.isHorizontal()) {
                edge.orientate();
                edge.shorten();
                edges.add(edge);
            }
        }

        //min a max y polygonu
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

            //list pruseciku
            List<Integer> prusecik = new ArrayList<>();

            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    prusecik.add(edge.getIntersection(y));
                }
            }

            //sort listu pruseciku
            Collections.sort(prusecik);

            //nakresli lines mezi páry pruseciku
            for (int i = 0; i < prusecik.size()-1; i += 2) {
                linerTrivial.drawLine(img, prusecik.get(i), y, prusecik.get(i+1), y, fillColor);
            }
        }

        //hrany polygonu
        polygoner.rasterize(img, polygon, linerTrivial, edgeColor);

    }
}
