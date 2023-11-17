package objectdata;

import rasterdata.Raster;

public class Ellipse extends Polygon{

    public void draw(Raster img, double h, double k, double a, double b, int color) {
        drawQuadrant(img, h, k, a, b, color, 1, 1);
        drawQuadrant(img, h, k, a, b, color, -1, 1);
        drawQuadrant(img, h, k, a, b, color, 1, -1);
        drawQuadrant(img, h, k, a, b, color, -1, -1);
    }

    private void drawQuadrant(Raster img, double h, double k, double a, double b, int color, int xSign, int ySign) {
        double x = 0;
        double y = b;

        double r = b * b - a * a * b + 0.25 * a * a;
        drawPoints(img, h, k, x, y, color, xSign, ySign);

        while (x * b * b <= y * a * a) {
            x++;
            if (r < 0) {
                r += b * b * (2 * x + 1);
            } else {
                y--;
                r += b * b * (2 * x + 1) + a * a * (-2 * y + 2);
            }
            drawPoints(img, h, k, x, y, color, xSign, ySign);
        }

        r = a * a * (y - 0.5) * (y - 0.5) + b * b * (x + 1) * (x + 1) - a * a * b * b;
        while (y > 0) {
            y--;
            if (r > 0) {
                r += a * a * (-2 * y + 1);
            } else {
                x++;
                r += b * b * (2 * x + 1) + a * a * (-2 * y + 1);
            }
            drawPoints(img, h, k, x, y, color, xSign, ySign);
        }
    }

    private void drawPoints(Raster img, double h, double k, double x, double y, int color, int xSign, int ySign) {
        img.setColor((int) (h + x * xSign), (int) (k + y * ySign), color);
        img.setColor((int) (h - x * xSign), (int) (k + y * ySign), color);
        img.setColor((int) (h + x * xSign), (int) (k - y * ySign), color);
        img.setColor((int) (h - x * xSign), (int) (k - y * ySign), color);
    }

}
