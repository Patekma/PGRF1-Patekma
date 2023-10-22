import objectdata.Point;
import objectdata.Polygon;
import rasterdata.RasterBI;
import rasterops.LinerDashed;
import rasterops.LinerDotted;
import rasterops.LinerTrivial;
import rasterops.Polygoner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2020
 */

//TODO: udelat ten krizek (do vsech stran), ale aby byla videt trasa

public class Canvas {

    private JFrame frame;
    private JPanel panel;
    private RasterBI img;
    LinerTrivial linerTrivial = new LinerTrivial();
    LinerDotted linerDotted = new LinerDotted(5);
    LinerDashed linerDashed = new LinerDashed(10,10);
    Polygon polygon = new Polygon();
    Polygoner polygoner = new Polygoner(linerTrivial, linerDotted);
    double x1;
    double y1;
    double x2;
    double y2;

    boolean lineMode = true;
    boolean polygonMode = false;
    boolean dashedLineMode = false;


    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new RasterBI(width, height);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.requestFocusInWindow();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_C) {
                    polygon.clearPoints();
                    clear();
                }
                if(e.getKeyCode() == KeyEvent.VK_L) {
                    lineMode = !lineMode;
                    polygonMode = false;
                    System.out.println("Line mode: " + lineMode);
                    System.out.println("Polygon mode: " +polygonMode);
                    clear();
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_K) {
                    lineMode = false;
                    polygonMode = !polygonMode;
                    System.out.println("Line mode: " + lineMode);
                    System.out.println("Polygon mode: " + polygonMode);
                    clear();
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_J) {
                    dashedLineMode = !dashedLineMode;
                    System.out.println("Dashed line: " + dashedLineMode);
                }
            }
        });

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    if (lineMode) {
                        x1 = mouseEvent.getX();
                        y1 = mouseEvent.getY();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

                if (lineMode){
                    if (dashedLineMode){
                        x2 = mouseEvent.getX();
                        y2 = mouseEvent.getY();
                        linerDashed.drawLine(img, x1, y1, x2, y2, 0xff00ff);
                        panel.repaint();
                    }else {
                        x2 = mouseEvent.getX();
                        y2 = mouseEvent.getY();
                        linerTrivial.drawLine(img, x1, y1, x2, y2, 0xff00ff);
                        panel.repaint();
                    }
                }

                if (polygonMode){
                    if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                        clear();
                        polygon.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
                        if (polygon.getCount() > 1) {
                            polygoner.rasterizePolygon(img, polygon, 0xffffff, mouseEvent);
                            panel.repaint();
                        }
                    }
                    if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        clear();
                        polygoner.updatePolygon(polygon, mouseEvent);
                        polygoner.rasterizePolygon(img, polygon, 0xffffff, mouseEvent);
                        panel.repaint();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                    if (lineMode) {
                        clear();
                        linerDotted.drawLine(img, x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
                        panel.repaint();
                    }
                    if (polygonMode && polygon.getCount() > 0) {
                        clear();
                        polygoner.rasterizePolygonDotted(img, polygon, 0xffffff, mouseEvent);
                        panel.repaint();
                    }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void draw() {
        img.clear(0x2f2f2f);
    }

    public void start() {
        draw();
        panel.repaint();
        System.out.println("Line mode: " + lineMode);
        System.out.println("Polygon mode: " + polygonMode);
    }

    public void clear(){
        img.clear(0x2f2f2f);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}
