package rasterops;

import objectdata.Line;
import objectdata.Point;
import rasterdata.Raster;

public class LinerTrivial implements Liner{
    @Override
    public void drawLine(Raster img, double x1, double y1, double x2, double y2, int color) {
        //výpočet sklonu k
        double k = (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;
        double temp;

        //pokud je x2 menší než x1, musíme prohodit, abychom mohli vykreslit linku na všechny strany
        if(x2 < x1)
        {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        //to samé pro y2 a y1
        if(y2 < y1)
        {
            temp = y1;
            y1 = y2;
            y2 = temp;
        }
        //pokud jsou x2 a x1 stejné, sklon bude přímo svislý: vykresluje se pouze po ose Y
        if(x2 - x1 == 0)
        {
            for (double y = y1; y <= y2; y++) {
                double x = x1;
                img.setColor((int) x, (int) y, color);
            }
        }
        // pro k < 1: nízký sklon je využita osa x
        else if(Math.abs(k) <= 1) {
            for (double x = x1; x <= x2; x++) {
                int y = (int) Math.round(k * x + q);
                img.setColor((int) x, y, color);
            }
        }
        // pro k > 1: strmý sklon je využita osa y
        else if(Math.abs(k) > 1)
        {
            for (double y = y1; y <= y2; y++) {
                double x = (int) Math.round((y - q)/k);
                img.setColor((int) x, (int) y, color);
            }
        }

    }

    @Override
    public void drawLine(Raster img, Point p1, Point p2,  int color) {

        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();

        double k = (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;
        double temp;

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
    @Override
    public void drawLine(Raster img, Line line, int color) {

        double x1 = line.getP1().getX();
        double y1 = line.getP1().getY();
        double x2 = line.getP2().getX();
        double y2 = line.getP2().getX();

        double k = (y2 - y1) / (x2 - x1);
        double q = y1 - k * x1;
        double temp;

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
