package objectdata;

import transforms.Point3D;

public class Axes extends Solid{
    public Axes() {
        // Vertex buffers
        vb.add(new Point3D(0, 0, 0));
        vb.add(new Point3D(1, 0, 0));
        vb.add(new Point3D(0, 1, 0));
        vb.add(new Point3D(0, 0, 1));

        // Index buffer
        addIndices(
                0, 1,
                0, 2,
                0, 3
        );
    }
}
