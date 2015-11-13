package com.vasax.clothes.service;

import javax.imageio.ImageIO;
import javax.inject.Named;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Vasax on 01.06.2015.
 */
@Named
public class WatermarkService {
    public byte[] addTextWatermark(String text, byte[] sourceImageData) {
        try {
            InputStream sourceImageStream = new ByteArrayInputStream(sourceImageData);
            BufferedImage sourceImage = ImageIO.read(sourceImageStream);
            sourceImageStream.close();
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics(); // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.GRAY);
            int font = 12;
            Rectangle2D rect;
            do{
                font +=2;
                g2d.setFont(new Font("Arial", Font.BOLD, font));
                FontMetrics fontMetrics = g2d.getFontMetrics();
                rect = fontMetrics.getStringBounds(text, g2d); // calculates the coordinate where the String is painted

            } while (rect.getWidth() < sourceImage.getWidth() * 0.75);
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2; // paints the textual watermark
            g2d.drawString(text, centerX, centerY);
            ByteArrayOutputStream outputImageStream = new ByteArrayOutputStream ();
            ImageIO.write(sourceImage, "png", outputImageStream);
            g2d.dispose();
//            System.out.println("The text watermark is added to the image.");
            byte[] outputBytes = outputImageStream.toByteArray();
            outputImageStream.close();
            return outputBytes;
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
