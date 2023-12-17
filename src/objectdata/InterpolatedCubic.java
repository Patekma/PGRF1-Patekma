package objectdata;

import transforms.Bicubic;
import transforms.Mat4;
import transforms.Point3D;

public class InterpolatedCubic extends Solid {

    public InterpolatedCubic(Mat4 transformation) {
        super();
        generateCubic(transformation);
    }

    private void generateCubic(Mat4 transformation) {
        Point3D[] controlPoints = {
                new Point3D(0, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 2, 1),
                new Point3D(0, 3, 0),
                new Point3D(1, 0, 0),
                new Point3D(1, 1, 2),
                new Point3D(1, 2, 0),
                new Point3D(1, 3, 0),
                new Point3D(2, 0, 3),
                new Point3D(2, 1, 0),
                new Point3D(2, 2, 0),
                new Point3D(2, 3, 0),
                new Point3D(3, 0, -4),
                new Point3D(3, 1, 0),
                new Point3D(3, 2, -6),
                new Point3D(3, 3, 0)
        };

        Bicubic bicubicInterpolator = new Bicubic(transformation, controlPoints);
        int subdivisions = 20;

        for (int i = 0; i <= subdivisions; i++) {
            float u = (float) i / subdivisions;
            for (int j = 0; j <= subdivisions; j++) {
                float v = (float) j / subdivisions;
                vb.add(bicubicInterpolator.compute(u, v));
            }
        }

        for (int i = 0; i < subdivisions; i++) {
            for (int j = 0; j < subdivisions; j++) {
                int index = i * (subdivisions + 1) + j;

                ib.add(index);
                ib.add(index + 1);
                ib.add(index + subdivisions + 1);

                ib.add(index + 1);
                ib.add(index + subdivisions + 2);
                ib.add(index + subdivisions + 1);
            }
        }
    }
}
