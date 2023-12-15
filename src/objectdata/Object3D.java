package objectdata;

import transforms.Mat4;
import transforms.Point3D;

import java.util.List;

public abstract class Object3D {

    private final List<Point3D> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final Mat4 modelMatrix;
    // private final int color;

    public Object3D(final List<Point3D> vertexBuffer,
                    final List<Integer> indexBuffer,
                    final Mat4 modelMatrix) {
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.modelMatrix = modelMatrix;
    }

    // getters
    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public Mat4 getModelMatrix() {
        return modelMatrix;
    }

    /**
     * Returns a new Object3D with its model matrix set to the given one
     * @param modelMatrix new model matrix
     * @return new Object3D with the new model matrix
     */
    abstract Object3D withModelMatrix(final Mat4 modelMatrix);

}
