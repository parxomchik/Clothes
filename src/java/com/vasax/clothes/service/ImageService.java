package com.vasax.clothes.service;

import com.vasax.clothes.dao.ItemImageDao;
import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.ItemImage;
import com.vasax.clothes.managed.ConstantsBean;
import com.vasax.clothes.managed.admin.ImageDishBean;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasax32 on 12.04.15.
 */
@Named
public class ImageService {
    @Inject
    private ItemImageDao itemImageDao;
    @Inject
    private ItemService itemService;
    @Inject
    private CategoryService categoryService;
    @Inject
    private NewsService newsService;
    @Inject
    private ArticleService articleService;
    @Inject
    private WatermarkService watermarkService;
    @Inject
    private ConstantsBean constantsBean;
//    @Inject
//    private ImageDishBean imageDishBean;


    @Transactional
    public List<ItemImage> getByItem(int id){
        return itemImageDao.selectByItemId(id);
    }

    public long getCountOfImagesByItem(int id){
        return itemImageDao.selectCountOfImagesByItemId(id);
    }

    public List<Integer> getIdsOfImages(int itemId){
        return itemImageDao.selectIdsByItemId(itemId);
    }

    @Transactional
    public ItemImage add(ItemImage itemImage, int itemId) {
        Item item = itemService.getItemById(itemId);
        itemImage.setItem(item);
        ItemImage insert = itemImageDao.insert(itemImage);
        insert.setOrderId(insert.getId());
        insert = itemImageDao.update(insert);
        return insert;
    }

    @Transactional
    public void remove(ItemImage itemImage){
        itemImage = itemImageDao.update(itemImage);
        itemImageDao.remove(itemImage);
    }

    @Transactional
    public List<ItemImage> update(List<ItemImage> images) {
        List<ItemImage> ret = new ArrayList<>();
        for (ItemImage image : images) {
            ret.add(update(image));
        }
        return ret;
    }

    @Transactional
    public ItemImage update(ItemImage image){
        if(image.getWithWatermarkData() == null || image.getWithWatermarkData().length == 0 || image.isNeedToBeUpdated()){
            if(image.getData() != null) {
                image.setWithWatermarkData(watermarkService.addTextWatermark(constantsBean.getSiteTitle(), image.getData()));
                image.setNeedToBeUpdated(false);
            }
        }
        return itemImageDao.update(image);
    }

    public ItemImage getById(Integer imageId) {
        return itemImageDao.selectById(imageId);
    }

    public List<String> getColorsWithNotNullIdByItemId(int itemId) {
        return itemImageDao.selectColorsWithNotNullIdByIteId(itemId);
    }

    public int getColorIdByItemIdAndColor(int itemId, String color) {
        return itemImageDao.selectColorIdByIteIdAndColor(itemId, color);
    }

    @Transactional
    public List<String> getAllColors(){
        return new ArrayList<>(itemImageDao.selectAllColors());
    }

    @Transactional
    public List<String> getAllColorsByCategoryId(int categoryId){
        return new ArrayList<>(itemImageDao.selectAllActiveColorsByCategoryId(categoryId));
    }

    @Transactional
    public void makeResize(){
        List<ItemImage> itemImages = itemImageDao.selectAll();
        for (ItemImage itemImage : itemImages) {
//            if(itemImage.getSmallData() == null || itemImage.getSmallData().length == 0){
                itemImage.setSmallData(ImageDishBean.scale(itemImage.getData(), 0,  500));
                itemImageDao.update(itemImage);
//            }
        }
    }

    @Transactional(readOnly = true)
    public byte[] getByCategory(Integer categoryId) {
        return categoryService.getCategoryById(categoryId).getImage();
    }

    @Transactional
    public byte[] getByNews(Integer newsId) {
        return newsService.getImageById(newsId);
    }

    @Transactional
    public byte[] getByArticle(Integer articleId) {
        return articleService.getImageById(articleId);
    }

    @Transactional
     public List<ItemImage> getAll() {
        return itemImageDao.selectAll();
    }

    @Transactional
    public List<Integer> AllIdsWhereWaterMarkIsNull() {
        return itemImageDao.AllIdsWhereWatermarkIsNull();
    }
}
