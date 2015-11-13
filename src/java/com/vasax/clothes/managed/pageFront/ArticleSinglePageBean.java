package com.vasax.clothes.managed.pageFront;

import com.vasax.clothes.entities.Article;
import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.ArticleService;
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
public class ArticleSinglePageBean {

    @Inject
    private ArticleService articleService;

    private int articleId = -1;
    private Article article;

    public void init() throws IOException {
        if(articleId == -1){
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
        article = articleService.getById(articleId);
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
