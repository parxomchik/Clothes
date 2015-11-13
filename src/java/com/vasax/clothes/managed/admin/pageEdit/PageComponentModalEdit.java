package com.vasax.clothes.managed.admin.pageEdit;

import com.vasax.clothes.entities.ItemImage;
import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.entities.component.PlainComponent;
import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.PageComponentService;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
@Scope("view")
public class PageComponentModalEdit {

    @Inject
    private ComponentService componentService;
    @Inject
    private PageComponentService pageComponentService;

    private PageComponent selectedPageComponent;
    private List<PlainComponent> components;
    private Map<Integer, PlainComponent> componentMap;
    private int idSelectedPageComponent;

    public PageComponent getSelectedPageComponent() {
        return selectedPageComponent;
    }

    public void setSelectedPageComponent(int idSelectedPageComponent) {
        this.idSelectedPageComponent = idSelectedPageComponent;
        this.selectedPageComponent = pageComponentService.getById(idSelectedPageComponent);
        components = componentService.getAllByPageComponent(idSelectedPageComponent);
        componentMap = new HashMap<>();
        for (PlainComponent component : components) {
            componentMap.put(component.getId(), component);
        }
    }

    public String save(){
        componentService.update(components);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "pageEdit";
    }

    public String cancel(){
        setSelectedPageComponent(idSelectedPageComponent);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Canceled","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "pageEdit";
    }

    public void upload(FileUploadEvent event) throws IOException {
        int componentId = (Integer) event.getComponent().getAttributes().get("componentId");
        boolean isFileName = Boolean.valueOf((String)event.getComponent().getAttributes().get("fileName"));
        String uploadKey = (String) event.getComponent().getAttributes().get("uploadKey");
        UploadedFile file = event.getFile();
        byte[] contents = IOUtils.toByteArray(file.getInputstream());

        PlainComponent plainComponent = componentMap.get(componentId);
        int indexOf = components.indexOf(plainComponent);
        components.remove(indexOf);
        if(isFileName)
            plainComponent.getPropertyForPlainComponents().put("fileName", file.getFileName());
        plainComponent.getBlobPropertyForPlainComponents().put(uploadKey, contents);
        plainComponent = componentService.update(plainComponent);
        components.add(indexOf, plainComponent);
        componentMap.put(indexOf, plainComponent);


        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Image uploaded","File: " + file.getFileName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public long getNow(){
        return new Date().getTime();
    }

    public String getTextAsPlainText(String content){
        return content.replaceAll("<[^>]+>", "");
    }

    public List<PlainComponent> getComponents() {
        return components;
    }

    public void setComponents(List<PlainComponent> components) {
        this.components = components;
    }
}
