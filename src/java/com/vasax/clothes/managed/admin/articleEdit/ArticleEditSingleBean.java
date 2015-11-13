package com.vasax.clothes.managed.admin.articleEdit;

import com.vasax.clothes.entities.Article;
import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.ArticleService;
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
public class ArticleEditSingleBean {
    private int articleId;
    private Article article;

    @Inject
    private ArticleService articleService;

    public void init(){
        article = articleService.getById(articleId);
    }

    public void save(){
        articleService.update(article);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getArticleId() {

        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
