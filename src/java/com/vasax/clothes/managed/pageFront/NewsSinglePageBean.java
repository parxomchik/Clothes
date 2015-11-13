package com.vasax.clothes.managed.pageFront;

import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.NewsService;
import org.springframework.context.annotation.Scope;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Created by Vasax on 14.05.2015.
 */
@Named
@Scope("view")
public class NewsSinglePageBean {

    @Inject
    private NewsService newsService;

    private int newsId = -1;
    private News news;

    public void init() throws IOException {
        if(newsId == -1){
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
        news = newsService.getById(newsId);
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
