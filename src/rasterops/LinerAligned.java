package rasterops;

import rasterdata.Raster;

public class LinerAligned implements Liner{
    @Override
    public void drawLine(Raster img, double x1, double y1, double x2, double y2, int color) {

        double k = (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;
        double temp;


        //sklon se zakrouhluje podle toho k čemu je blíže
        if (k < 1 && k >= 0.5){
            k = 1;
        }
        else if (k > 0 && k < 0.5){
            k = 0;
        }
        else if (k < 0 && k > -0.5){
            //vodorovná
            k = 0;
        }
        else if (k > -1 && k <= -0.5){
            k = -1;
        } else {
            //pokud je sklon jiný, úsečka se zarovná na svislou
            x2 = x1;
        }

        if(x2 < x1)
        {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }

        if(y2 < y1)
        {
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        if(x2 - x1 == 0)
        {
            for (double y = y1; y <= y2; y++) {
                double x = x1;
                img.setColor((int) x, (int) y, color);
            }
        }
        else if(Math.abs(k) <= 1) {
            for (double x = x1; x <= x2; x++) {
                int y = (int) Math.round(k * x + q);
                img.setColor((int) x, y, color);
            }
        }
        else if(Math.abs(k) > 1)
        {
            for (double y = y1; y <= y2; y++) {
                double x = (int) Math.round((y - q)/k);
                img.setColor((int) x, (int) y, color);
            }
        }
    }
}
