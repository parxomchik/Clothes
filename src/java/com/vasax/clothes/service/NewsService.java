package com.vasax.clothes.service;

import com.vasax.clothes.dao.NewsDao;
import com.vasax.clothes.entities.News;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vasax on 11.05.2015.
 */
@Named
public class NewsService {
    @Inject
    private NewsDao newsDao;
    @Inject
    private GlobalSettingService globalSettingService;

    @Transactional
    public List<News> getAllOrderedByLastModifiedDate() {
        Boolean showNotActive = globalSettingService.requireBoolean("showNotActive");
        return newsDao.selectAllOrderedByLastModifiedDate(showNotActive);
    }

    @Transactional
    public List<News> update(List<News> newses) {
        List<News> ret = new ArrayList<>();
        for (News news : newses) {
            ret.add(update(news));
        }
        return ret;
    }

    @Transactional
    public byte[] getImageById(Integer newsId) {
        return newsDao.selectById(newsId).getImage();
    }

    public List<News> getAllActiveOrderedByLastModifiedDate() {
        return newsDao.selectAllActiveOrderedByLastModifiedDate();
    }

    @Transactional
    public News getById(int newsId) {
        return newsDao.selectById(newsId);
    }

    @Transactional
    public News update(News news) {
        if(news.getOrderId() == 0){
            news.setOrderId(news.getId());
            news = newsDao.update(news);
        }
        News oldNews = newsDao.selectById(news.getId());
        if( news.equals(oldNews)){
            return news;
        }
        news.setLastModified(new Timestamp(new Date().getTime()));
        return newsDao.update(news);
    }
}
