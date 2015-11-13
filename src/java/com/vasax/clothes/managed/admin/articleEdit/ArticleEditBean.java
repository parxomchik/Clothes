package com.vasax.clothes.managed.admin.articleEdit;

import com.vasax.clothes.entities.Article;
import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.ArticleService;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by root on 16.11.14.
 */
@Named
@Scope("view")
public class ArticleEditBean {

    @Inject
    private ArticleService articleService;

    private List<Article> articles;
    private Map<Integer, Article> articleMap = new HashMap<>();


    @PostConstruct
    public void init(){
        articles = articleService.getAllOrderedByLastModifiedDate();
        for (Article article : articles) {
            articleMap.put(article.getId(), article);
        }
    }


    public String save(){
        articles = articleService.update(articles);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reload();
        return null;
    }

    public void upload(FileUploadEvent event) throws IOException {
        int id = (Integer) event.getComponent().getAttributes().get("newsId");
        UploadedFile file = event.getFile();
        byte[] contents = IOUtils.toByteArray(file.getInputstream());

        Article article = articleMap.get(id);
        articles.remove(article);
        id = articleService.update(article).getId();
        save();

        article = articleMap.get(id);
        articles.remove(article);
        id = articleService.update(article).getId();
        save();
        article = articleMap.get(id);
        article.setImage(contents);
        article.setImageName(file.getFileName());
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image uploaded","File: " + file.getFileName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void removeImage(int id){
        Article article = articleMap.get(id);
        article.setImage(null);
        article.setImageName("");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image removed","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String reload(){
        articleMap.clear();
        init();
        return "";
    }

    public String addNew(){
        Article article = new Article();
        article.setId(getRandomId());
        article.setLastModified(new Timestamp(new Date().getTime()));
        articles.add(0, article);
        articleMap.put(article.getId(), article);
        return "";
    }

    public void onRowReorder(ReorderEvent event) {
        Article getFrom = articles.get(event.getFromIndex());
        Article getTo = articles.get(event.getToIndex());
        int temp = getFrom.getOrderId() != 0 ? getFrom.getOrderId() : getFrom.getId();
        getFrom.setOrderId(getTo.getOrderId() != 0 ? getTo.getOrderId() : getTo.getId());
        getTo.setOrderId(temp);
        Collections.swap(articles, event.getFromIndex(), event.getToIndex());
        //add message
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Article Moved", "From: " + getFrom.getId() + ", To:" + getTo.getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private int getRandomId(){
        Random r = new Random();
        int x = 0;
        while (true) {
            x = r.nextInt(articles.size() + 100);
            boolean notIn = true;
            for(Article article : articles)
                if(article.getId() == x)
                    notIn = false;
            if(notIn)
                break;

        }
        return x;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
