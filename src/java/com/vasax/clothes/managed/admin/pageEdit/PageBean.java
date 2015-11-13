package com.vasax.clothes.managed.admin.pageEdit;

import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.managed.pageFront.contactsPage.ContactsPageBean;
import com.vasax.clothes.service.PageComponentService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
@Scope("view")
public class PageBean {
    @Inject
    private PageComponentService pageComponentService;
    @Inject
    private PageComponentModalEdit pageComponentModalEdit;

    private List<PageComponent> pageComponents;
    private Map<Integer, PageComponent> pageComponentMap;

    @PostConstruct
    public void init(){
        pageComponents = pageComponentService.getAll();
        pageComponentMap = new HashMap<>();
        for (PageComponent pageComponent : pageComponents) {
            pageComponentMap.put(pageComponent.getId(), pageComponent);
        }
    }

    public String save(){
        pageComponentService.update(pageComponents);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

    public void selectPageForEdit(int id){
        pageComponentModalEdit.setSelectedPageComponent(id);
    }

    public List<PageComponent> getPageComponents() {
        return pageComponents;
    }

    public void setPageComponents(List<PageComponent> pageComponents) {
        this.pageComponents = pageComponents;
    }
}
