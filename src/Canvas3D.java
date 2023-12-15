import objectdata.Cube;
import objectdata.Pyramid;
import rasterdata.RasterBI;
import rasterops.LinerTrivial;
import rasterops.WiredRenderer;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas3D {

    private JFrame frame;
    private JPanel panel;

    private RasterBI img;

    private Camera camera;

    private Point2D mousePos;
    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();

    LinerTrivial linerTrivial = new LinerTrivial();

    WiredRenderer renderer = new WiredRenderer(linerTrivial);

    Mat4OrthoRH proj = new Mat4OrthoRH(8, 8, 8, 8);

    public Canvas3D(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : patekma1");
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

        camera = new Camera(new Vec3D(0.0, 0.0, 0.0), 0.0, 0.0, 1.0, true);

        renderer.setView(camera.getViewMatrix());
        renderer.setProj(proj);
        renderer.setImg(img);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cameraMovement(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mousePos = new Point2D(e.getX(), e.getY());
                panel.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent f) {
                        super.mouseDragged(f);
                        double dx = f.getX() - mousePos.getX();
                        double dy = f.getY() - mousePos.getY();

                        camera = camera.addAzimuth(-(dx) * Math.PI / 360);
                        camera = camera.addZenith(-(dy) * Math.PI / 360);

                        mousePos = new Point2D(f.getX(), f.getY());
                        renderScene();
                        panel.repaint();
                    }
                });
            }
        });

        panel.setFocusable(true);

        renderScene();
        panel.repaint();
    }

    private void cameraMovement(KeyEvent e) {
        int key = e.getKeyCode();

        // Adjust camera based on key events
        switch (key) {
            case KeyEvent.VK_UP:
                camera = camera.forward(1.0);
                System.out.println(camera.getPosition());
                System.out.println(cube.getModel());
                break;
            case KeyEvent.VK_DOWN:
                camera = camera.backward(1.0);
                System.out.println(camera.getPosition());
                break;
            case KeyEvent.VK_LEFT:
                camera = camera.right(0.1);
                System.out.println(camera.getPosition());
                break;
            case KeyEvent.VK_RIGHT:
                camera = camera.left(0.1);
                System.out.println(camera.getPosition());
                break;
            case KeyEvent.VK_E:
                camera = camera.up(0.1);
                break;
            case KeyEvent.VK_D:
                camera = camera.down(0.1);
                break;
        }
//        draw();
        renderScene();
        panel.repaint();
    }

    public void present(Graphics graphics) {
        img.present(graphics);
    }

    public void draw() {
        img.clear(0x2f2f2f);
    }

    public void start() {
//            draw();
        panel.repaint();
    }

    public void clear() {
        img.clear(0x2f2f2f);
        panel.repaint();
    }

    public void renderScene() {
        draw();
        renderer.setView(camera.getViewMatrix());
        renderer.setProj(proj);
        renderer.render(cube, 0xff00ff);
        renderer.render(pyramid, 0x00ffff);
        panel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas3D(800, 600).start());
    }

}

