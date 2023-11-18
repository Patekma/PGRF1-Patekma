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

public class TriangleFill {
    public void fill(Raster img, Polygon polygon, int fillColor, int edgeColor, Polygoner polygoner, LinerTrivial linerTrivial){
        // List hran
        List<Edge> edges = new ArrayList<>();

        // Vytvorime hrany z bodů
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

            for (int i = 0; i < prusecik.size() - 1; i += 2) {
                int x1 = prusecik.get(i);
                int x2 = prusecik.get(i + 1);

                // Draw triangular pattern
                drawTriangularPattern(img, x1, y, x2, fillColor);
            }
        }

        polygoner.rasterize(img, polygon, linerTrivial, edgeColor);
    }

    private void drawTriangularPattern(Raster img, int x1, double y1, int x2, int fillColor) {
        boolean drawTriangle = true;
        int gapSize = 1; //mezera

        for (int x = x1; x <= x2; x += gapSize) {
            if (drawTriangle) {
                // Horní roh trojuhelniku
                img.setColor(x, (int) y1, fillColor);

                // Levý roh trojuhelniku
                for (int i = 1; i <= gapSize; i++) {
                    img.setColor(x + i, (int) y1 + i, fillColor);
                }

                // Pravý roh trojuhelniku
                for (int i = 1; i <= gapSize; i++) {
                    img.setColor(x - i, (int) y1 + i, fillColor);
                }
            }

            drawTriangle = !drawTriangle;
        }
}
}
