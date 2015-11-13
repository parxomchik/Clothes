package com.vasax.clothes.service;

import com.vasax.clothes.dao.component.PageComponentDao;
import com.vasax.clothes.entities.component.PageComponent;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
public class PageComponentService {
    @Inject
    private PageComponentDao pageComponentDao;

    @Transactional
    public PageComponent require(String identifier, String title){
        PageComponent pageComponent = pageComponentDao.selectByIdentifier(identifier);
        if(pageComponent == null){
            pageComponent = new PageComponent();
            pageComponent.setIdentifier(identifier);
            pageComponent.setTitle(title);
         pageComponent= pageComponentDao.insert(pageComponent);
        }
        return pageComponent;
    }

    @Transactional
    public PageComponent require(String identifier){
        return require(identifier, null);
    }

    @Transactional
    public List<PageComponent> getAll() {
        return pageComponentDao.selectAll();
    }

    @Transactional
    public void update(List<PageComponent> pageComponents) {
        for (PageComponent pageComponent : pageComponents) {
            pageComponentDao.update(pageComponent);
        }
    }

    @Transactional
    public PageComponent getById(int idSelectedPageComponent) {
        return pageComponentDao.selectById(idSelectedPageComponent);
    }
}
