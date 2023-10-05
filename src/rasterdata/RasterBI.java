package rasterdata;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterBI implements Raster, Presentable{

    private final BufferedImage img;

    public RasterBI(int width, int height){
        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

    @Override
    public boolean setColor(int c, int r, int color) {
        if (c >= 0 && r >= 0 && c < img.getHeight()-1 && r < img.getWidth()-1){
            img.setRGB(r, c, color);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Integer> getColor(int c, int r) {
        if (c >= 0 && r >= 0 && c < img.getHeight()-1 && r < img.getWidth()-1){
            return Optional.of(img.getRGB(r,c));
        }
        return Optional.empty();
    }

    @Override
    public void clear(int background) {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(background));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    @Override
    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }
}
