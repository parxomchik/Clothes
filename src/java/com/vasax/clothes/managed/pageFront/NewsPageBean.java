package com.vasax.clothes.managed.pageFront;

import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.NewsService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Vasax on 11.05.2015.
 */
@Named
@Scope("view")
public class NewsPageBean {
    @Inject
    private NewsService newsService;

    private List<News> newses;

    @PostConstruct
    public void init(){
        newses = newsService.getAllActiveOrderedByLastModifiedDate();
    }

    public List<News> getNewses() {
        return newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }
}
