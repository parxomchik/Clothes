package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.ItemImage;
import com.vasax.clothes.service.ImageService;
import com.vasax.clothes.service.ItemService;
import org.omg.CORBA.portable.ApplicationException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oper4 on 17.11.2014.
 */
@Named
@Scope("request")
public class ImageDishBean {

    @Inject
    private ImageService imageService;

    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            Integer dishId = Integer.valueOf(context.getExternalContext().getRequestParameterMap().get("dishId"));
            String imageIdStr = context.getExternalContext().getRequestParameterMap().get("imageId");
            String smallStr = context.getExternalContext().getRequestParameterMap().get("small");
            boolean isSmall = false;
            if(smallStr != null)
                isSmall = Boolean.valueOf(smallStr);
            if(imageIdStr!= null) {
                Integer imageId = Integer.parseInt(imageIdStr);

                ItemImage byId = imageService.getById(imageId);
                if(isSmall)
                    return new DefaultStreamedContent(new ByteArrayInputStream(byId.getSmallData()));
                else
                    return new DefaultStreamedContent(new ByteArrayInputStream(byId.getData()));
            } else {
                List<Integer> idsOfImages = imageService.getIdsOfImages(dishId);
                if(idsOfImages.size() > 0) {
                    ItemImage byId = imageService.getById(idsOfImages.get(0));
//                    return new DefaultStreamedContent(new ByteArrayInputStream(scale(byId.getData(), 400, 400)));
                    if(isSmall)
                        return new DefaultStreamedContent(new ByteArrayInputStream(byId.getSmallData()));// need to add image resizing
                    else
                        return new DefaultStreamedContent(new ByteArrayInputStream(byId.getData())); // need to add image resizing
                }
            }
            return null;
        }
    }

    public static byte[] scale(byte[] fileData, int width, int height) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if(height == 0) {
                height = (width * img.getHeight())/ img.getWidth();
            }
            if(width == 0) {
                width = (height * img.getWidth())/ img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
//            throw new ApplicationException("IOException in scale");
            System.out.println("IOException in scale");
        }
        return null;
    }
}
