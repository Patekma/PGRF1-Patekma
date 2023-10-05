package rasterdata;

import java.util.Optional;

public interface Raster {

    int getWidth();
    int getHeight();
    boolean setColor(int c, int r, int color);
    Optional<Integer> getColor(int c, int r);

}
