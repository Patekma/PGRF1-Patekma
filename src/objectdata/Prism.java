package objectdata;

import transforms.Point3D;

public class Prism extends Solid{
    public Prism() {
        // Vertex buffers
        vb.add(new Point3D(0, 1, 0));   // Vertex 0 (top)
        vb.add(new Point3D(1, 0, 0));   // Vertex 1 (right)
        vb.add(new Point3D(0, -1, 0));  // Vertex 2 (bottom)
        vb.add(new Point3D(-1, 0, 0));  // Vertex 3 (left)

        vb.add(new Point3D(0, 0, 1));   // Vertex 4 (front)
        vb.add(new Point3D(0, 0, -1));  // Vertex 5 (back)

        // Index buffer
        addIndices(
                0, 1,  // Top to right
                1, 2,  // Right to bottom
                2, 3,  // Bottom to left
                3, 0,  // Left to top

                0, 4,  // Top to front
                1, 4,  // Right to front
                2, 4,  // Bottom to front
                3, 4,  // Left to front

                0, 5,  // Top to back
                1, 5,  // Right to back
                2, 5,  // Bottom to back
                3, 5   // Left to back
        );
    }
}
