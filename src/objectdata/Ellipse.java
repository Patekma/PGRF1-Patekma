package objectdata;

import rasterdata.Raster;

public class Ellipse extends Polygon{

    public void draw(Raster img, double h, double k, double a, double b, int color) {
        //nakresli elipsu pomoci 4 kvadrantů
        drawQuadrant(img, h, k, a, b, color, 1, 1);
        drawQuadrant(img, h, k, a, b, color, -1, 1);
        drawQuadrant(img, h, k, a, b, color, 1, -1);
        drawQuadrant(img, h, k, a, b, color, -1, -1);
    }

    private void drawQuadrant(Raster img, double h, double k, double a, double b, int color, int xSign, int ySign) {
        double x = 0;
        double y = b;

        double r = b * b - a * a * b + 0.25 * a * a;

        //body v aktualnim kvadrantu
        drawPoints(img, h, k, x, y, color, xSign, ySign);

        while (x * b * b <= y * a * a) {
            x++;
            if (r < 0) {
                r += b * b * (2 * x + 1);
            } else {
                y--;
                r += b * b * (2 * x + 1) + a * a * (-2 * y + 2);
            }
            //body v aktualnim kvadrantu
            drawPoints(img, h, k, x, y, color, xSign, ySign);
        }

        r = a * a * (y - 0.5) * (y - 0.5) + b * b * (x + 1) * (x + 1) - a * a * b * b;

        //body pro druhou cast elipsy
        while (y > 0) {
            y--;
            if (r > 0) {
                r += a * a * (-2 * y + 1);
            } else {
                x++;
                r += b * b * (2 * x + 1) + a * a * (-2 * y + 1);
            }
            //body v aktualnim kvadrantu
            drawPoints(img, h, k, x, y, color, xSign, ySign);
        }
    }

    private void drawPoints(Raster img, double h, double k, double x, double y, int color, int xSign, int ySign) {
        //nastavi barvu pro 4 kvadranty elipsy
        img.setColor((int) (h + x * xSign), (int) (k + y * ySign), color);
        img.setColor((int) (h - x * xSign), (int) (k + y * ySign), color);
        img.setColor((int) (h + x * xSign), (int) (k - y * ySign), color);
        img.setColor((int) (h - x * xSign), (int) (k - y * ySign), color);
    }

}
