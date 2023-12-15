package objectdata;

import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Solid> solids;
    private List<Mat4> modelMats;
    public Scene() {
        this.solids = new ArrayList<>();
        this.modelMats = new ArrayList<>();
    }

    public List<Solid> getSolids() {
        return solids;
    }

    public List<Mat4> getModelMats() {
        return modelMats;
    }

    public void addSolid(Solid solid) {
        this.solids.add(solid);
        this.modelMats.add(new Mat4Identity());
    }

    public void addSolid(Solid solid, Mat4 modelMat) {
        this.solids.add(solid);
        this.modelMats.add(modelMat);
    }

    public void clearScene(){
        this.solids = new ArrayList<>();
        this.modelMats = new ArrayList<>();
    }

}
