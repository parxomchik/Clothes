package com.vasax.clothes.service;

import com.vasax.clothes.dao.ItemDao;
import com.vasax.clothes.dao.RelativeItemDao;
import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.RelativeItem;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by vasax32 on 18.04.15.
 */
@Named
public class RelativeItemService {

    @Inject
    private RelativeItemDao relativeItemDao;

    @Inject
    private ItemDao itemDao;

    @Inject
    private GlobalSettingService globalSettingService;

    @Transactional
    public RelativeItem add(RelativeItem relativeItem) {
        return relativeItemDao.insert(relativeItem);
    }

    @Transactional
    public List<Integer> getRelativeIdsByItem(int itemId){
        return relativeItemDao.selectIdsByItem(itemId);
    }

    @Transactional
    public List<Integer> getActiveRelativeIdsByItem(int itemId){
        Boolean showNotActive = globalSettingService.requireBoolean("showNotActive");
        return relativeItemDao.selectActiveIdsByItem(itemId, showNotActive);
    }

    @Transactional
    public void removeByIds(int itemId, int relativeItemId) {
        RelativeItem relativeItem = relativeItemDao.selectByItemIdAndRelativeId(itemId, relativeItemId);
        relativeItemDao.delete(relativeItem);
    }

    public List<Item> getRelativesByItem(int itemId) {
        return relativeItemDao.selectByItem(itemId);
    }

    @Transactional
    public void copyRelativeItems(int fromItemId, int toItemId) {
        List<Item> relativeItems = getRelativesByItem(fromItemId);
        Item item = itemDao.selectById(toItemId);
        for (Item relItem : relativeItems) {
            RelativeItem rel = new RelativeItem();
            rel.setRelative(relItem);
            rel.setItem(item);
            add(rel);
        }
    }
}
