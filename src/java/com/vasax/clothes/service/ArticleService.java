package com.vasax.clothes.service;

import com.vasax.clothes.dao.ArticleDao;
import com.vasax.clothes.entities.Article;
import com.vasax.clothes.entities.Property;
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
public class ArticleService {
    @Inject
    private ArticleDao articleDao;
    @Inject
    private GlobalSettingService globalSettingService;

    @Transactional
    public List<Article> getAllOrderedByLastModifiedDate() {
        Boolean showNotActive = globalSettingService.requireBoolean("showNotActive");
        return articleDao.selectAllOrderedByLastModifiedDate(showNotActive);
    }

    @Transactional
    public List<Article> update(List<Article> newses) {
        List<Article> ret = new ArrayList<>();
        for (Article article : newses) {
            ret.add(update(article));
        }
        return ret;
    }

    @Transactional
    public byte[] getImageById(Integer newsId) {
        return articleDao.selectById(newsId).getImage();
    }

    public List<Article> getAllActiveOrderedByLastModifiedDate() {
        return articleDao.selectAllActiveOrderedByLastModifiedDate();
    }

    @Transactional
    public Article getById(int articleId) {
        return articleDao.selectById(articleId);
    }

    @Transactional
    public Article update(Article article) {
        if(article.getOrderId() == 0){
            article.setOrderId(article.getId());
            article = articleDao.update(article);
        }
        Article oldArticle = articleDao.selectById(article.getId());
        if( article.equals(oldArticle)){
            return article;
        }
        article.setLastModified(new Timestamp(new Date().getTime()));
        return articleDao.update(article);
    }
}
