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
    private BufferedImage img;
    int up = 0;
    int down = 0;
    int left = 0;
    int right = 0;
    int trail = 0;
    int trailCol = 0;
    int trailRow = 0;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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
                    clear();
                    up += 1;
                    drawCross(up, down, left, right);
                    trail += 1;
                    trailCol -= 1;
                    drawTrail(trail,trailRow,trailCol);
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    clear();
                    down += 1;
                    drawCross(up, down, left, right);
                    trail += 1;
                    trailCol += 1;
                    drawTrail(trail,trailRow,trailCol);
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    clear();
                    left += 1;
                    drawCross(up, down, left, right);
                    trail += 1;
                    trailRow -= 1;
                    drawTrail(trail,trailRow,trailCol);
                    panel.repaint();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    clear();
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
                img.setRGB(mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
                panel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

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
                img.setRGB(mouseEvent.getX(), mouseEvent.getY(), 0xff00ff);
                panel.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
    }

    public void drawCross(int up, int down, int left, int right){
        img.setRGB(img.getWidth()/2 - left + right,img.getHeight()/2 + down - up, 0xffff00);
        img.setRGB(img.getWidth()/2 - 1 - left + right,img.getHeight()/2 + down - up, 0xffff00);
        img.setRGB(img.getWidth()/2 + 1 - left + right,img.getHeight()/2 + down - up, 0xffff00);
        img.setRGB(img.getWidth()/2 - left + right,(img.getHeight()/2 - 1) + down - up, 0xffff00);
        img.setRGB(img.getWidth()/2 - left + right,(img.getHeight()/2 + 1) + down - up, 0xffff00);
    }

    public void drawTrail(int trail, int trailRow, int trailCol){
        for (int i = 0; i < trail; i++) {
            if (trailCol<0){
                for (int j = 0; j < -trailCol; j++) {
                    img.setRGB(img.getWidth()/2,img.getHeight()/2 - j, 0xffff00);
                }
            }
            if (trailCol>0){
                for (int j = 0; j < trailCol; j++) {
                    img.setRGB(img.getWidth()/2,img.getHeight()/2 + j, 0xffff00);
                }
            }
            if (trailRow<0){
                for (int j = 0; j < -trailRow; j++) {
                    img.setRGB(img.getWidth()/2 - j,img.getHeight()/2 + trailCol, 0xffff00);
                }
            }
            if (trailRow>0){
                for (int j = 0; j < trailRow; j++) {
                    img.setRGB(img.getWidth()/2 + j,img.getHeight()/2 + trailCol, 0xffff00);
                }
            }

        }
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear();
        img.setRGB(img.getWidth()/2,img.getHeight()/2, 0xffff00);
    }

    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}
