package rasterops.fill;

import rasterdata.Raster;

import java.util.function.Predicate;

public class SeedFill4 implements SeedFill{
    @Override
    public void fill(Raster img, int c, int r, int fillColor, Predicate<Integer> isInArea) {
        img.getColor(c, r).ifPresent(color -> {
          if (isInArea.test(color)){
              img.setColor(c,r,fillColor);
              fill(img, c + 1, r, fillColor, isInArea);
            fill(img, c, r + 1, fillColor, isInArea);
            fill(img, c - 1, r, fillColor, isInArea);
            fill(img, c , r - 1, fillColor, isInArea);
        }
        });
    }
}
