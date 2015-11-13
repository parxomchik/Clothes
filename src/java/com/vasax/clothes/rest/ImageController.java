package com.vasax.clothes.rest;

import com.vasax.clothes.entities.ItemImage;
import com.vasax.clothes.entities.component.BlobPropertyForPlainComponent;
import com.vasax.clothes.service.BlobPropertyForPlainComponentService;
import com.vasax.clothes.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Created by vasax32 on 27.04.15.
 */
@Controller
@RequestMapping(value = "/image")
public class ImageController {

    @Inject
    private ImageService imageService;
    @Inject
    private BlobPropertyForPlainComponentService blobPropertyForPlainComponentService;

    @RequestMapping(method = RequestMethod.GET, value = "/item/{itemId}")
    public @ResponseBody byte[] getByItemId(@PathVariable("itemId") Integer itemId) {
        return imageService.getByItem(itemId).get(0).getData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/item/{itemId}/watermark")
    public @ResponseBody byte[] getByItemIdWithWatermark(@PathVariable("itemId") Integer itemId) {
        return imageService.getByItem(itemId).get(0).getWithWatermarkData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/item/{itemId}/small")
    public @ResponseBody byte[] getByItemIdSmall(@PathVariable("itemId") Integer itemId) {
        return imageService.getByItem(itemId).get(0).getSmallData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/{categoryId}")
    public @ResponseBody byte[] getByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        return imageService.getByCategory(categoryId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/news/{newsId}")
    public @ResponseBody byte[] getByNewsId(@PathVariable("newsId") Integer newsId) {
        return imageService.getByNews(newsId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/article/{articleId}")
    public @ResponseBody byte[] getByArticleId(@PathVariable("articleId") Integer articleId) {
        return imageService.getByArticle(articleId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{imageId}")
    public @ResponseBody byte[] getByImageId(@PathVariable(value = "imageId") Integer imageId) {
        ItemImage byId = imageService.getById(imageId);
        return byId.getData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{imageId}/watermark")
    public @ResponseBody byte[] getByImageIdWithWatermark(@PathVariable(value = "imageId") Integer imageId) {
        ItemImage byId = imageService.getById(imageId);
        return byId.getWithWatermarkData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{imageId}/small")
    public @ResponseBody byte[] getByImageIdSmall(@PathVariable(value = "imageId") Integer imageId) {
        ItemImage byId = imageService.getById(imageId);
        return byId.getSmallData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/plainComponent/{componentId}/blob/{key}/{time}")
    public @ResponseBody byte[] getPlainComponent(@PathVariable("componentId") Integer componentId, @PathVariable("key") String key) {
        BlobPropertyForPlainComponent property = blobPropertyForPlainComponentService.getPropertyByComponentAndKey(componentId, key);
        if(property != null)
            return property.getValueR();
        return null;
    }

}
