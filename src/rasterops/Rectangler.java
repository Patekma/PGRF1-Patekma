package rasterops;

import objectdata.Rectangle;
import rasterdata.Raster;

public class Rectangler {

    public Rectangler() {
    }

    public void rasterizeRectangle(Raster img, Rectangle rectangle, int color, Liner liner){
        liner.drawLine(img, rectangle.getPoint(0).getX(), rectangle.getPoint(0).getY(), rectangle.getPoint(1).getX(), rectangle.getPoint(0).getY(), color);
        liner.drawLine(img, rectangle.getPoint(0).getX(), rectangle.getPoint(0).getY(), rectangle.getPoint(0).getX(), rectangle.getPoint(1).getY(), color);
        liner.drawLine(img, rectangle.getPoint(1).getX(), rectangle.getPoint(0).getY(), rectangle.getPoint(1).getX(), rectangle.getPoint(1).getY(), color);
        liner.drawLine(img, rectangle.getPoint(0).getX(), rectangle.getPoint(1).getY(), rectangle.getPoint(1).getX(), rectangle.getPoint(1).getY(), color);
    }
}
