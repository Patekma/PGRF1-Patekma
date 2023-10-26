package rasterops.fill;

import rasterdata.Raster;

import java.util.function.Predicate;

public interface SeedFill {

    void fill(Raster img, int c, int r, int fillColor, Predicate<Integer> isInArea);
}
