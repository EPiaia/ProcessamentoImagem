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

    public Imagem(int width, int hegiht) {
        int cols = width;
        int rows = hegiht;

        this.red = new int[cols][rows];
        this.green = new int[cols][rows];
        this.blue = new int[cols][rows];
        this.alpha = new int[cols][rows];
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int[][] getRed() {
        return red;
    }

    public void setRed(int[][] red) {
        this.red = red;
    }

    public int[][] getGreen() {
        return green;
    }

    public void setGreen(int[][] green) {
        this.green = green;
    }

    public int[][] getBlue() {
        return blue;
    }

    public void setBlue(int[][] blue) {
        this.blue = blue;
    }

    public int[][] getAlpha() {
        return alpha;
    }

    public void setAlpha(int[][] alpha) {
        this.alpha = alpha;
    }

    public int getWidth() {
        return this.red.length;
    }

    public int getHeight() {
        return this.red[0].length;
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

    public BufferedImage getGrayImage() {
        int[][] grayMatrix = Tecnicas.rgbToGray(red, green, blue);
        int width = getWidth();
        int height = getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = grayMatrix[x][y];
                // Setando valores do RED (16) GREEN (8) e BLUE
                int rgbValue = (grayValue << 16) | (grayValue << 8) | grayValue;
                image.setRGB(x, y, rgbValue);
            }
        }
        return image;
    }

    public BufferedImage getBinaryImage() {
        int[][] binaryMatrix = Tecnicas.rgbToBinary(red, green, blue, 128);
        int width = getWidth();
        int height = getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int binaryValue = binaryMatrix[x][y];
                int rgbValue = binaryValue == 0 ? 0x000000 : 0xFFFFFF;
                image.setRGB(x, y, rgbValue);
            }
        }
        return image;
    }

    public BufferedImage getBrightenedImage(double bright) {
        bright = (bright / 100) + 1;
        return Tecnicas.rgbBrigthness(this, bright).getMatrixImage();
    }

    public BufferedImage getShadowedImage(double bright) {
        bright = 1 - (bright / 100);
        return Tecnicas.rgbBrigthness(this, bright).getMatrixImage();
    }
}
