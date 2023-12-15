import objectdata.Prism;
import objectdata.Cube;
import objectdata.Pyramid;
import objectdata.Scene;
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

    private Scene scene = new Scene();

    private Camera camera;

    private Point2D mousePos;
    Cube cube = new Cube();
    Pyramid pyramid = new Pyramid();
    Prism prism = new Prism();

    LinerTrivial linerTrivial = new LinerTrivial();

    WiredRenderer renderer = new WiredRenderer(linerTrivial);

    Mat4OrthoRH proj = new Mat4OrthoRH(8, 8, 8, 8);

    Mat4Transl cubeTransl = new Mat4Transl(new Vec3D(2, 2, 1));
    Mat4Transl prismTransl = new Mat4Transl(new Vec3D(-1, -2, 0));
    Mat4Transl pyramidTransl = new Mat4Transl(new Vec3D(0, 0, 0));
    Mat4RotXYZ cubeRotate = new Mat4RotXYZ(8, 8, 8);
    Mat4 cubeModel = cubeTransl.mul(cubeRotate);
    public int selected = 1;

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
            case KeyEvent.VK_J:
                selected = 1;
                break;
            case KeyEvent.VK_K:
                selected = 2;
                break;
            case KeyEvent.VK_L:
                selected = 3;
                break;
            case KeyEvent.VK_NUMPAD8:
                if (selected == 1){
                    cubeTransl = new Mat4Transl(cubeTransl.get(3, 0), cubeTransl.get(3, 1), cubeTransl.get(3, 2) + 0.1);
                    cube.setModel(cubeTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 2){
                    prismTransl = new Mat4Transl(prismTransl.get(3, 0), prismTransl.get(3, 1), prismTransl.get(3, 2) + 0.1);
                    prism.setModel(prismTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 3){
                    pyramidTransl = new Mat4Transl(pyramidTransl.get(3, 0), pyramidTransl.get(3, 1), pyramidTransl.get(3, 2) + 0.1);
                    pyramid.setModel(pyramidTransl);
                    renderScene();
                    panel.repaint();
                }
                break;
            case KeyEvent.VK_NUMPAD2:
                if (selected == 1){
                    cubeTransl = new Mat4Transl(cubeTransl.get(3, 0), cubeTransl.get(3, 1), cubeTransl.get(3, 2) - 0.1);
                    cube.setModel(cubeTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 2){
                    prismTransl = new Mat4Transl(prismTransl.get(3, 0), prismTransl.get(3, 1), prismTransl.get(3, 2) - 0.1);
                    prism.setModel(prismTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 3){
                    pyramidTransl = new Mat4Transl(pyramidTransl.get(3, 0), pyramidTransl.get(3, 1), pyramidTransl.get(3, 2) - 0.1);
                    pyramid.setModel(pyramidTransl);
                    renderScene();
                    panel.repaint();
                }
                break;
            case KeyEvent.VK_NUMPAD4:
                if (selected == 1){
                    cubeTransl = new Mat4Transl(cubeTransl.get(3, 0), cubeTransl.get(3, 1) + 0.1, cubeTransl.get(3, 2));
                    cube.setModel(cubeTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 2){
                    prismTransl = new Mat4Transl(prismTransl.get(3, 0), prismTransl.get(3, 1) + 0.1, prismTransl.get(3, 2));
                    prism.setModel(prismTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 3){
                    pyramidTransl = new Mat4Transl(pyramidTransl.get(3, 0), pyramidTransl.get(3, 1) + 0.1, pyramidTransl.get(3, 2));
                    pyramid.setModel(pyramidTransl);
                    renderScene();
                    panel.repaint();
                }
                break;
            case KeyEvent.VK_NUMPAD6:
                if (selected == 1){
                    cubeTransl = new Mat4Transl(cubeTransl.get(3, 0), cubeTransl.get(3, 1) - 0.1, cubeTransl.get(3, 2));
                    cube.setModel(cubeTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 2){
                    prismTransl = new Mat4Transl(prismTransl.get(3, 0), prismTransl.get(3, 1) - 0.1, prismTransl.get(3, 2));
                    prism.setModel(prismTransl);
                    renderScene();
                    panel.repaint();
                }
                if (selected == 3){
                    pyramidTransl = new Mat4Transl(pyramidTransl.get(3, 0), pyramidTransl.get(3, 1) - 0.1, pyramidTransl.get(3, 2));
                    pyramid.setModel(pyramidTransl);
                    renderScene();
                    panel.repaint();
                }
                break;
            case KeyEvent.VK_NUMPAD9:
                    cubeRotate = new Mat4RotXYZ(cubeRotate.get(3,0), cubeRotate.get(3,1), cubeRotate.get(3,2) + 0.1);

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

        cube.setModel(cubeModel);
        prism.setModel(prismTransl);
        pyramid.setModel(pyramidTransl);

        draw();
        renderScene();
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

        renderer.render(prism, 0xff0000);
        panel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas3D(800, 600).start());
    }

}

