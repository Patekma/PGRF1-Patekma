import objectdata.*;
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
    Prism prism = new Prism();
    Axes axes = new Axes();
    InterpolatedCubic ferguson = new InterpolatedCubic(Cubic.FERGUSON);
    InterpolatedCubic coons = new InterpolatedCubic(Cubic.COONS);
    InterpolatedCubic bezier = new InterpolatedCubic(Cubic.BEZIER);

    Cube animatedCube = new Cube();

    LinerTrivial linerTrivial = new LinerTrivial();

    WiredRenderer renderer = new WiredRenderer(linerTrivial);

    // TODO: prohazovani kamer
    /*
    if () {
        proj = new Mat4PerspRH(Math.PI / 4, 1, 0.01, 100);
    }
    if () {
        proj = new Mat4OrthoRH(8, 8, 8, 8);
    }
     */

    Mat4PerspRH proj = new Mat4PerspRH(Math.PI / 4, 1, 0.01, 100);
//    Mat4OrthoRH proj = new Mat4OrthoRH(8, 8, 8, 8);

    Mat4Transl cubeTransl = new Mat4Transl(new Vec3D(2, 2, 1));
    Mat4Transl prismTransl = new Mat4Transl(new Vec3D(-1, 0, 2));
    Mat4Transl pyramidTransl = new Mat4Transl(new Vec3D(0, -1, -3));
    Mat4Transl animatedCubeTrans = new Mat4Transl(new Vec3D(0, 4, 0));
    Mat4Transl fergusonTransl = new Mat4Transl(new Vec3D(-4, 4, 3));
    Mat4Transl coonsTransl = new Mat4Transl(new Vec3D(0, 1, 4));
    Mat4Transl bezierTransl = new Mat4Transl(new Vec3D(0, -5, 1));
    double change = 0;
    Mat4RotXYZ animatedCubeRotate = new Mat4RotXYZ(0, 50, 0);
    Mat4RotXYZ cubeRotate = new Mat4RotXYZ(8, 8, 8);

    Mat4 cubeModel = cubeTransl.mul(cubeRotate);
    public int selected = 1;
    int cameraMode = 1;

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

        camera = new Camera(new Vec3D(-15, 1, 1), 0.0, 0.0, 1.0, true);

        renderer.setView(camera.getViewMatrix());
        renderer.setProj(proj);
        renderer.setImg(img);

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                cameraMovement(e);
                selectSolid(e);
                moveSolid(e);
                renderScene();
                panel.repaint();
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

        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Mat4 modifier;
                if (e.getWheelRotation() < 0) {
                    modifier = new Mat4Scale(1.2, 1.2, 1.2);
                } else {
                    modifier = new Mat4Scale(0.8, 0.8, 0.8);
                }
                cube.setModel(cube.getModel().mul(modifier));
                pyramid.setModel(pyramid.getModel().mul(modifier));
                prism.setModel(prism.getModel().mul(modifier));
                ferguson.setModel(ferguson.getModel().mul(modifier));
                coons.setModel(coons.getModel().mul(modifier));
                bezier.setModel(bezier.getModel().mul(modifier));
                renderScene();
                panel.repaint();
            }
        });

        panel.setFocusable(true);

        renderScene();
        panel.repaint();
        startAnimation();
    }

    private void cameraMovement(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                camera = camera.up(1.0);
                break;
            case KeyEvent.VK_DOWN:
                camera = camera.down(1.0);
                break;
            case KeyEvent.VK_LEFT:
                camera = camera.left(0.1);
                break;
            case KeyEvent.VK_RIGHT:
                camera = camera.right(0.1);
                break;
            case KeyEvent.VK_E:
                camera = camera.forward(0.1);
                break;
            case KeyEvent.VK_D:
                camera = camera.backward(0.1);
                break;
            case KeyEvent.VK_N:
                cameraMode = 1;
                break;
            case KeyEvent.VK_M:
                cameraMode = 2;
                break;
        }
    }

    private void selectSolid(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_J:
                selected = 1; //cube
                break;
            case KeyEvent.VK_K:
                selected = 2; //prism
                break;
            case KeyEvent.VK_L:
                selected = 3; //pyramid
                break;
            case KeyEvent.VK_U:
                selected = 4; //ferguson
                break;
            case KeyEvent.VK_I:
                selected = 5; //coons
                break;
            case KeyEvent.VK_O:
                selected = 6; //bezier
                break;
        }
    }

    private void moveSolid(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_NUMPAD8:
                moveSelectedSolid(new Mat4Transl(0, 0, 1));
                break;
            case KeyEvent.VK_NUMPAD2:
                moveSelectedSolid(new Mat4Transl(0, 0, -1));
                break;
            case KeyEvent.VK_NUMPAD4:
                moveSelectedSolid(new Mat4Transl(0, 1, 0));
                break;
            case KeyEvent.VK_NUMPAD6:
                moveSelectedSolid(new Mat4Transl(0, -1, 0));
                break;
            case KeyEvent.VK_NUMPAD9:
                rotateSelectedSolid(new Mat4Rot(1, 2, 0, 0));
                break;
            case KeyEvent.VK_NUMPAD7:
                rotateSelectedSolid(new Mat4Rot(-1, 2, 0, 0));
                break;
        }
    }

    public void moveSelectedSolid(Mat4Transl value){
        if(selected == 1){
            cube.setModel(cube.getModel().mul(value));
        }
        if(selected == 2){
            prism.setModel(prism.getModel().mul(value));
        }
        if(selected == 3){
            pyramid.setModel(pyramid.getModel().mul(value));
        }
        if(selected == 4){
            ferguson.setModel(ferguson.getModel().mul(value));
        }
        if(selected == 5){
            coons.setModel(prism.getModel().mul(value));
        }
        if(selected == 6){
            bezier.setModel(pyramid.getModel().mul(value));
        }

    }

    public void rotateSelectedSolid(Mat4Rot value){
        if(selected == 1){
            cube.setModel(cube.getModel().mul(value));
        }
        if(selected == 2){
            prism.setModel(prism.getModel().mul(value));
        }
        if(selected == 3){
            pyramid.setModel(pyramid.getModel().mul(value));
        }
        if(selected == 4){
            ferguson.setModel(ferguson.getModel().mul(value));
        }
        if(selected == 5){
            coons.setModel(prism.getModel().mul(value));
        }
        if(selected == 6){
            bezier.setModel(pyramid.getModel().mul(value));
        }
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

        ferguson.setModel(fergusonTransl);
        coons.setModel(coonsTransl);
        bezier.setModel(bezierTransl);

        draw();
        renderScene();
        panel.repaint();

    }

    public void renderScene() {
        draw();
        renderer.setView(camera.getViewMatrix());
        renderer.setProj(proj);

        renderer.render(cube, 0xff00ff);

        renderer.render(pyramid, 0x00ffff);

        renderer.render(prism, 0xff0000);

        renderer.render(axes, new int[]{0xff0000, 0x00ff00, 0x0000ff});

        renderer.render(ferguson, 0xff0000);
        renderer.render(coons, 0x00ff00);
        renderer.render(bezier, 0x0000ff);

        renderer.render(animatedCube, 0xf0f0f0);

        panel.repaint();
    }

    public void startAnimation() {
        int delay = 100;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateAnimatedCube();
                renderScene();
                panel.repaint();
            }
        };
        new Timer(delay, taskPerformer).start();
    }

    private void updateAnimatedCube() {
        change += 1;
        animatedCubeRotate = new Mat4RotXYZ(0, change, 0);
        animatedCube.setModel(animatedCubeTrans.mul(animatedCubeRotate));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas3D(800, 600).start());
    }

}

