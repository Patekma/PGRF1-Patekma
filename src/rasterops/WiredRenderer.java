package rasterops;

import objectdata.Solid;
import rasterdata.Raster;
import rasterdata.RasterBI;
import transforms.*;

import java.awt.*;
import java.util.ArrayList;

public class WiredRenderer {
    private Liner liner;
    private Mat4 view;
    private Mat4 proj;

    private Raster img;

    public WiredRenderer(Liner liner) {
        this.liner = liner;
    }

    public void render(Solid solid, int color) {
        // Solid má index buffer, projdu ho v cyklu
        // pro každé dva prvky si načtu odpovídající vertex
        // spojím vertexy linou

        for (int i = 0; i < solid.getIb().size(); i += 2) {
            int indexA = solid.getIb().get(i);
            int indexB = solid.getIb().get(i + 1);

            Point3D a = solid.getVb().get(indexA);
            Point3D b = solid.getVb().get(indexB);

            a = a.mul(solid.getModel()).mul(view).mul(proj);
            b = b.mul(solid.getModel()).mul(view).mul(proj);

            if (a.getW() < 0 || b.getW() < 0) {
                break;
            }

            Vec3D w1 = null;
            Vec3D w2 = null;

            if (a.dehomog().isPresent()) {
                w1 = a.dehomog().get();
            }
            if (b.dehomog().isPresent()) {
                w2 = b.dehomog().get();
            }

            assert w1 != null;
            Vec3D v1 = new Vec3D(w1);
            assert w2 != null;
            Vec3D v2 = new Vec3D(w2);

            v1 = transformToWindow(v1);
            v2 = transformToWindow(v2);

            liner.drawLine(img,
                    (int)Math.round(v1.getX()), (int)Math.round(v1.getY()),
                    (int)Math.round(v2.getX()), (int)Math.round(v2.getY()),
                    color);
        }
    }

    public Vec3D transformToWindow(Vec3D p) {
        return p.mul(new Vec3D(1, -1, 1))
                .add(new Vec3D(1, 1, 0))
                .mul(new Vec3D((800 - 1) / 2., (600 - 1) / 2., 1));
    }

    public void renderScene(ArrayList<Solid> scene, int color) {
        for (Solid solid : scene)
            render(solid, color);
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }

    public void setImg(Raster img) {
        this.img = img;
    }
}
