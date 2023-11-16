package rasterops;

import objectdata.Point;
import objectdata.Polygon;
import rasterdata.Raster;

import java.awt.event.MouseEvent;
import java.util.List;

public class Polygoner {
    private LinerTrivial linerTrivial;

    private LinerDotted linerDotted;

    public Polygoner(LinerTrivial linerTrivial, LinerDotted linerDotted) {
        this.linerTrivial = linerTrivial;
        this.linerDotted = linerDotted;
    }


    public void rasterizePolygon(Raster img, Polygon polygon, int color, MouseEvent mouseEvent){
        for (int i = 0; i < polygon.getCount() - 1; i++) {
            linerTrivial.drawLine(img, polygon.getPoint(i).getX(), polygon.getPoint(i).getY(), polygon.getPoint(i + 1).getX(), polygon.getPoint(i + 1).getY(), color);
            //propojení předposleního bodu s poslednim: předposlední hrana polygonu
            linerTrivial.drawLine(img, polygon.getPoint(polygon.getCount() - 1).getX(), polygon.getPoint(polygon.getCount() - 1).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
        }
        //propojení posledního bodu s prvnim: poslední hrana polygonu
        linerTrivial.drawLine(img, polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
    }

    public void rasterizePolygonDotted(Raster img, Polygon polygon, int color, MouseEvent mouseEvent){
        for (int i = 0; i < polygon.getCount() - 1; i++) {
            linerDotted.drawLine(img, polygon.getPoint(i).getX(), polygon.getPoint(i).getY(), polygon.getPoint(i + 1).getX(), polygon.getPoint(i + 1).getY(), color);
            linerDotted.drawLine(img, polygon.getPoint(polygon.getCount() - 1).getX(), polygon.getPoint(polygon.getCount() - 1).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
        }
        linerDotted.drawLine(img, polygon.getPoint(0).getX(), polygon.getPoint(0).getY(), mouseEvent.getX(), mouseEvent.getY(), color);
    }

    public void updatePolygon(Polygon polygon, MouseEvent mouseEvent){
        Point tempPoint = new Point(0,0);
        double tempX;
        double tempY;
        double tempValue;
        double tempPointX;
        double tempPointY;
        double tempPointValue;
        int temp;
        for (int i = 0; i < polygon.getCount(); i++) {
            /*
            pro zjištění vzdálenosti kde se klikce myší:
                odečtou se "x" a "y" z každého bodu od "x" a "y" mouse eventu, výsledek se přičte
                to samé se udělá s dočasně uloženým bodem
                porovná se zda je momentálně počítaný bod blíže k mouse eventu, než uložený bod
                => pokud ano, uloží se do dočasného bodu a hodnoty aktuálně projížděného bodu se změní na hodnoty mouse eventu
            */
            tempX = polygon.getPoint(i).getX() - mouseEvent.getX();
            tempY = polygon.getPoint(i).getY() - mouseEvent.getY();
            tempValue = tempX + tempY;
            tempPointX = tempPoint.getX() - mouseEvent.getX();
            tempPointY = tempPoint.getY() - mouseEvent.getY();
            tempPointValue = tempPointX + tempPointY;
            if (tempValue < 0 && tempValue > tempPointValue){
                tempPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                polygon.getPoint(i).setX(mouseEvent.getX());
                polygon.getPoint(i).setY(mouseEvent.getY());
            } else if (tempValue > 0 && tempValue < tempPointValue) {
                tempPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
                polygon.getPoint(i).setX(mouseEvent.getX());
                polygon.getPoint(i).setY(mouseEvent.getY());
            }
        }

    }


    //nová a jednodušší rasterizace: využita při scanLine
    public void rasterize(Raster img, Polygon polygon, LinerTrivial liner, int color){
        List<Point> points = polygon.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point prev = points.get(i - 1);
            Point curr = points.get(i);
            liner.drawLine(img, prev.getX(), prev.getY(), curr.getX(), curr.getY(), color);
        }
        Point last = points.get(points.size() - 1);
        Point first = points.get(0);
        liner.drawLine(img, last.getX(), last.getY(), first.getX(), first.getY(), color);

    }
}
