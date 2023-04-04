package com.br.processamentoimagem;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Piaia
 */
public class Imagem implements Serializable {

    private BufferedImage img;
    private int[][] red;
    private int[][] green;
    private int[][] blue;
    private int[][] alpha;

    public Imagem() {
    }

    public Imagem(BufferedImage img) {
        this.img = img;

        int cols = img.getWidth();
        int rows = img.getHeight();

        this.red = new int[cols][rows];
        this.green = new int[cols][rows];
        this.blue = new int[cols][rows];
        this.alpha = new int[cols][rows];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int clr = img.getRGB(x, y);
                this.alpha[x][y] = (clr >> 24) & 255;
                this.red[x][y] = (clr >> 16) & 255;
                this.green[x][y] = (clr >> 8) & 255;
                this.blue[x][y] = clr & 255;
            }
        }
    }

    public BufferedImage getImg() {
        return img;
    }

    public int[][] getRed() {
        return red;
    }

    public int[][] getGreen() {
        return green;
    }

    public int[][] getBlue() {
        return blue;
    }

    public int[][] getAlpha() {
        return alpha;
    }

    public BufferedImage getMatrixImage() {
        int width = this.red.length;
        int height = this.red[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = (alpha[x][y] << 24) | (red[x][y] << 16) | (green[x][y] << 8) | blue[x][y];
                image.setRGB(x, y, argb);
            }
        }
        return image;
    }
}
