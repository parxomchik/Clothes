package com.vasax.clothes;

import com.vasax.clothes.service.WatermarkService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Vasax on 01.06.2015.
 */
public class WatermarkTest {
    static WatermarkService watermarkService = new WatermarkService();

    public static void main(String[] args) throws IOException {
        Path sourceImageFile = Paths.get("C:\\Users\\Vasax\\Documents\\IdeaProjects\\clothes\\src\\webapp\\resources\\img\\odegda\\IMG_8708.jpg");
        byte[] sourceData = Files.readAllBytes(sourceImageFile);
        File destImageFile = new File("text_watermarked.png");
        byte[] bytes = watermarkService.addTextWatermark("zermon.com.ua", sourceData);
        FileOutputStream fileOutputStream = new FileOutputStream(destImageFile);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

}
