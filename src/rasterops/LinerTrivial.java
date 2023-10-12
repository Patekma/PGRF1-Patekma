package rasterops;

import rasterdata.Raster;

//naive line algorithm
//nekresli doleva, protoze predpokladam, ze x1 je vetsi nez x2

public class LinerTrivial implements Liner{
    @Override
    public void drawLine(Raster img, double x1, double y1, double x2, double y2, int color) {
        double k = (y2-y1) / (x2-x1);
        double q = y1 - k * x1;
        double y;

        for (int x = (int) Math.round(x1); x < x2; x++) {
            y = (k * x) + q;
            img.setColor(x, (int) y,color);
        }
    }
}
