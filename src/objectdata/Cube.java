package objectdata;

import transforms.Mat4;
import transforms.Point3D;

import java.util.List;

public class Cube extends Solid{
    public Cube() {
        // Vertex buffers
        vb.add(new Point3D(-1, -1, -1));
        vb.add(new Point3D(1, -1, -1));
        vb.add(new Point3D(1, 1, -1));
        vb.add(new Point3D(-1, 1, -1));

        vb.add(new Point3D(-1, -1, 1));
        vb.add(new Point3D(1, -1, 1));
        vb.add(new Point3D(1, 1, 1));
        vb.add(new Point3D(-1, 1, 1));

        // Index buffer
        addIndices(
                0, 1,
                1, 2,
                2, 3,
                3, 0,
                4, 5,
                5, 6,
                6, 7,
                7, 4,
                0, 4,
                1, 5,
                2, 6,
                3, 7

        );
    }

}
