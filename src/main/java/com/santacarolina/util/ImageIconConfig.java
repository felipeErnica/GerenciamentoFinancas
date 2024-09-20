package com.santacarolina.util;

import com.formdev.flatlaf.util.UIScale;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageIconConfig {

    private String location;
    private BufferedImage bufferedImage;

    public ImageIconConfig(String location) {
        this.location = location;
        try {
            bufferedImage = ImageIO.read(new File(location));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            bufferedImage = new BufferedImage(20,20,BufferedImage.TYPE_BYTE_GRAY);
        }
    }

    public  ImageIcon getResizedIcon(int width, int height)  {
        BufferedImage resizeBuffer = new BufferedImage(width,height,bufferedImage.getType());
        Graphics2D imageGraphics = resizeBuffer.createGraphics();
        imageGraphics.setComposite(AlphaComposite.Src);
        imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imageGraphics.drawImage(bufferedImage,0,0,width,height,null);
        imageGraphics.dispose();
        return new ImageIcon(resizeBuffer);
    }

    public  ImageIcon getIcon()  {
        Graphics2D imageGraphics = bufferedImage.createGraphics();
        UIScale.scaleGraphics(imageGraphics);
        return new ImageIcon(bufferedImage);
    }

    public  Graphics2D getGraphics()  {
        Graphics2D imageGraphics = bufferedImage.createGraphics();
        imageGraphics.setComposite(AlphaComposite.Src);
        imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imageGraphics.dispose();
        return imageGraphics;
    }

    public BufferedImage getBufferedImage() {
        Graphics2D imageGraphics = bufferedImage.createGraphics();
        imageGraphics.setComposite(AlphaComposite.Src);
        imageGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imageGraphics.dispose();
        return bufferedImage;
    }

}
