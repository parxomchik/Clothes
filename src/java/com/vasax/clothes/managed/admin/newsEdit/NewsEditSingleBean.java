package com.vasax.clothes.managed.admin.newsEdit;

import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.NewsService;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Vasax on 01.06.2015.
 */
@Named
@Scope("view")
public class NewsEditSingleBean {
    private int newsId;
    private News news;

    @Inject
    private NewsService newsService;

    public void init(){
        news = newsService.getById(newsId);
    }

    public void save(){
        newsService.update(news);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
