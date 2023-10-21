package rasterops;

import rasterdata.Raster;

public class LinerDotted implements Liner{
    private int spaceLength;
    public LinerDotted(int spaceLength) {
        this.spaceLength = spaceLength;
    }

    public void setSpaceLength(int spaceLength) {
        this.spaceLength = spaceLength;
    }

    @Override
    public void drawLine(Raster img, double x1, double y1, double x2, double y2, int color) {
        int space = 0;
        int length = 0;

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
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= 1) {
                    double x = x1;
                    img.setColor((int) x, (int) y, color);
                    length++;
                } else {
                    space++;
                }

            }
        }
        else if(Math.abs(k) <= 1) {
            for (double x = x1; x <= x2; x++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= 1) {
                    int y = (int) Math.round(k * x + q);
                    img.setColor((int) x, y, color);
                    length++;
                } else {
                    space++;
                }

            }
        }
        else if(Math.abs(k) > 1)
        {
            for (double y = y1; y <= y2; y++) {
                if (space > spaceLength) {
                    length = 0;
                    space = 0;
                }
                if (length <= 1) {
                    double x = (int) Math.round((y - q)/k);
                    img.setColor((int) x, (int) y, color);
                    length++;
                } else {
                    space++;
                }
            }
        }
    }
}
