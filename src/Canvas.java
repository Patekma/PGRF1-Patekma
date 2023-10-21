import rasterdata.RasterBI;
import rasterops.LinerDashed;
import rasterops.LinerDotted;
import rasterops.LinerTrivial;

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
    int up = 0;
    int down = 0;
    int left = 0;
    int right = 0;
    int trail = 0;
    int trailCol = 0;
    int trailRow = 0;
    double x1;
    double y1;
    double x2;
    double y2;

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
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    img.clear(0x2f2f2f);
                    up += 1;
                    drawCross(up, down, left, right);
                    trail += 1;
                    trailCol -= 1;
                    drawTrail(trail,trailRow,trailCol);
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    img.clear(0x2f2f2f);
                    down += 1;
                    drawCross(up, down, left, right);
                    trail += 1;
                    trailCol += 1;
                    drawTrail(trail,trailRow,trailCol);
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    img.clear(0x2f2f2f);
                    left += 1;
                    drawCross(up, down, left, right);
                    trail += 1;
                    trailRow -= 1;
                    drawTrail(trail,trailRow,trailCol);
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    img.clear(0x2f2f2f);
                    right += 1;
                    drawCross(up, down, left, right);
                    trail += 1;
                    trailRow += 1;
                    drawTrail(trail,trailRow,trailCol);
                    panel.repaint();
                }
            }
        });

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                x1 = mouseEvent.getX();
                y1 = mouseEvent.getY();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                x2 = mouseEvent.getX();
                y2 = mouseEvent.getY();
                linerDotted.drawLine(img, x1, y1, x2, y2, 0xff00ff);
                panel.repaint();
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
                img.clear(0x2f2f2f);
                linerDotted.drawLine(img, x1, y1, mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
                panel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

//        panel.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent mouseEvent) {
//                img.setColor(mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
//                panel.repaint();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent mouseEvent) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent mouseEvent) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent mouseEvent) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent mouseEvent) {
//
//            }
//        });

//        panel.addMouseMotionListener(new MouseMotionListener() {
//            @Override
//            public void mouseDragged(MouseEvent mouseEvent) {
//                img.setColor(mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
//                panel.repaint();
//            }
//
//            @Override
//            public void mouseMoved(MouseEvent mouseEvent) {
//
//            }
//        });
    }

    public void drawCross(int up, int down, int left, int right){
        img.setColor(img.getWidth()/2 - left + right,img.getHeight()/2 + down - up, 0xffff00);
        img.setColor(img.getWidth()/2 - 1 - left + right,img.getHeight()/2 + down - up, 0xffff00);
        img.setColor(img.getWidth()/2 + 1 - left + right,img.getHeight()/2 + down - up, 0xffff00);
        img.setColor(img.getWidth()/2 - left + right,(img.getHeight()/2 - 1) + down - up, 0xffff00);
        img.setColor(img.getWidth()/2 - left + right,(img.getHeight()/2 + 1) + down - up, 0xffff00);
    }

    public void drawTrail(int trail, int trailRow, int trailCol){
        for (int i = 0; i < trail; i++) {
            if (trailCol<0){
                for (int j = 0; j < -trailCol; j++) {
                    img.setColor(img.getWidth()/2,img.getHeight()/2 - j, 0xffff00);
                }
            }
            if (trailCol>0){
                for (int j = 0; j < trailCol; j++) {
                    img.setColor(img.getWidth()/2,img.getHeight()/2 + j, 0xffff00);
                }
            }
            if (trailRow<0){
                for (int j = 0; j < -trailRow; j++) {
                    img.setColor(img.getWidth()/2 - j,img.getHeight()/2 + trailCol, 0xffff00);
                }
            }
            if (trailRow>0){
                for (int j = 0; j < trailRow; j++) {
                    img.setColor(img.getWidth()/2 + j,img.getHeight()/2 + trailCol, 0xffff00);
                }
            }

        }
    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void draw() {
        img.clear(0x2f2f2f);
        img.setColor(img.getWidth(),img.getHeight(), 0xffff00);
    }

    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}
