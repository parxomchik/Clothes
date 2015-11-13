package com.vasax.clothes.managed.admin.newsEdit;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.entities.News;
import com.vasax.clothes.service.NewsService;
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
public class NewsEditBean {

    @Inject
    private NewsService newsService;

    private List<News> newses;
    private Map<Integer, News> newsMap = new HashMap<>();


    @PostConstruct
    public void init(){
        newses = newsService.getAllOrderedByLastModifiedDate();
        for (News news : newses) {
            newsMap.put(news.getId(), news);
        }
    }


    public String save(){
        newses = newsService.update(newses);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        reload();
        return null;
    }

    public void upload(FileUploadEvent event) throws IOException {
        int id = (Integer) event.getComponent().getAttributes().get("newsId");
        UploadedFile file = event.getFile();
        byte[] contents = IOUtils.toByteArray(file.getInputstream());

        News news = newsMap.get(id);
        newses.remove(news);
        id = newsService.update(news).getId();
        save();
        news = newsMap.get(id);
        news.setImage(contents);
        news.setImageName(file.getFileName());
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image uploaded","File: " + file.getFileName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void removeImage(int id){
        News news = newsMap.get(id);
        news.setImage(null);
        news.setImageName("");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image removed","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String reload(){
        newsMap.clear();
        init();
        return "";
    }

    public String addNew(){
        News news = new News();
        news.setId(getRandomId());
        news.setLastModified(new Timestamp(new Date().getTime()));
        newses.add(0, news);
        newsMap.put(news.getId(), news);
        return "";
    }

    public void onRowReorder(ReorderEvent event) {
        News getFrom = newses.get(event.getFromIndex());
        News getTo = newses.get(event.getToIndex());
        int temp = getFrom.getOrderId() != 0 ? getFrom.getOrderId() : getFrom.getId();
        getFrom.setOrderId(getTo.getOrderId() != 0 ? getTo.getOrderId() : getTo.getId());
        getTo.setOrderId(temp);
        Collections.swap(newses, event.getFromIndex(), event.getToIndex());
        //add message
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "News Moved", "From: " + getFrom.getId() + ", To:" + getTo.getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }



    private int getRandomId(){
        Random r = new Random();
        int x = 0;
        while (true) {
            x = r.nextInt(newses.size() + 100);
            boolean notIn = true;
            for(News news : newses)
                if(news.getId() == x)
                    notIn = false;
            if(notIn)
                break;

        }
        return x;
    }

    public List<News> getNewses() {
        return newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }
}
