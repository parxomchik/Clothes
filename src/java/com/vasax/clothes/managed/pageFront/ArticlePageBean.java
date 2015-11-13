package com.vasax.clothes.managed.pageFront;

import com.vasax.clothes.entities.Article;
import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.ArticleService;
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
public class ArticlePageBean {
    @Inject
    private ArticleService articleService;

    private List<Article> articles;

    @PostConstruct
    public void init(){
        articles = articleService.getAllActiveOrderedByLastModifiedDate();
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
