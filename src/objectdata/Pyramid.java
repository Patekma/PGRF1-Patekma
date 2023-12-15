package objectdata;

import transforms.Point3D;

public class Pyramid extends Solid{
    public Pyramid() {
        vb.add(new Point3D(1, -1, -1)); // Vertex 0 (base)
        vb.add(new Point3D(-1, -1, -1)); // Vertex 1 (base)
        vb.add(new Point3D(-1, -1, 1)); // Vertex 2 (base)
        vb.add(new Point3D(1, -1, 1)); // Vertex 3 (base)
        vb.add(new Point3D(0, 1, 0)); // Vertex 4 (apex)

        //base
        addIndices(
                0, 1,
                1, 2,
                2, 3,
                3, 0
        );

        //sides
        addIndices(
                0, 4,
                1, 4,
                2, 4,
                3, 4
        );
    }
}
