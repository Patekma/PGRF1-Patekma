package objectdata;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final List<Object3D> objects;

    public Scene() {
        this.objects = new ArrayList<>();
    }

    public void addObject(final Object3D object) {
        objects.add(object);
    }

    // TODO HW
    // getter, setter for objects

    public List<Object3D> getObjects() {
        return objects;
    }


    // set(i, object)
    // get(i)
    // remove(i)

}
