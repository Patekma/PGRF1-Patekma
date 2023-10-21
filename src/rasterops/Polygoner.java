package rasterops;

import objectdata.Point;
import objectdata.Polygon;
import rasterdata.Raster;

import java.awt.event.MouseEvent;

public class Polygoner {
    private LinerTrivial linerTrivial;

    private LinerDotted linerDotted;

    public Polygoner(LinerTrivial linerTrivial, LinerDotted linerDotted) {
        this.linerTrivial = linerTrivial;
        this.linerDotted = linerDotted;
    }


    public void rasterizePolygon(Raster img, Polygon polygon, int color, MouseEvent e){
        for (int i = 0; i < polygon.getCount() - 1; i++) {
            linerTrivial.drawLine(img, polygon.getPoint(i).getX(), polygon.getPoint(i).getY(), polygon.getPoint(i + 1).getX(), polygon.getPoint(i + 1).getY(), color);
            linerTrivial.drawLine(img, polygon.getPoint(polygon.getCount() - 1).getX(), polygon.getPoint(polygon.getCount() - 1).getY(), e.getX(), e.getY(), color);
        }
        linerTrivial.drawLine(img, polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), e.getX(), e.getY(), color);
    }

    public void rasterizePolygonDotted(Raster img, Polygon polygon, int color, MouseEvent e){
        for (int i = 0; i < polygon.getCount() - 1; i++) {
            linerDotted.drawLine(img, polygon.getPoint(i).getX(), polygon.getPoint(i).getY(), polygon.getPoint(i + 1).getX(), polygon.getPoint(i + 1).getY(), color);
            linerDotted.drawLine(img, polygon.getPoint(polygon.getCount() - 1).getX(), polygon.getPoint(polygon.getCount() - 1).getY(), e.getX(), e.getY(), color);
        }
        linerDotted.drawLine(img, polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), e.getX(), e.getY(), color);
    }

    public void updatePolygon(Raster img, Polygon polygon, int color){
        for (int i = 0; i < polygon.getCount() - 1; i++) {
            linerDotted.drawLine(img, polygon.getPoint(i).getX(), polygon.getPoint(i).getY(), polygon.getPoint(i + 1).getX(), polygon.getPoint(i + 1).getY(), color);
            linerDotted.drawLine(img, polygon.getPoint(polygon.getCount() - 1).getX(), polygon.getPoint(polygon.getCount() - 1).getY(), polygon.getPoint(polygon.getCount()).getX(), polygon.getPoint(polygon.getCount()).getY(), color);
        }
        linerDotted.drawLine(img, polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), polygon.getPoint(polygon.getCount()).getX(), polygon.getPoint(polygon.getCount()).getY(), color);
    }
}
