package rasterops;

import objectdata.Line;
import objectdata.Point;
import rasterdata.Raster;

public interface Liner {

    void drawLine(Raster img, double x1, double y1, double x2, double y2, int color);

    default void drawLine(Raster img, Point p1, Point p2, int color){
    }

    default void drawLine(Raster img, Line line, int color){
    }
}
