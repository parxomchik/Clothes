package com.vasax.clothes.managed.admin;

import com.vasax.clothes.entities.GlobalSetting;
import com.vasax.clothes.service.GlobalSettingService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Vasax on 03.05.2015.
 */
@Named
@Scope("view")
public class SiteSettingBean {
    @Inject
    private GlobalSettingService globalSettingService;
    private List<GlobalSetting> globalSettingList;

    @PostConstruct
     public void init(){
        globalSettingList = globalSettingService.getAll();
    }

    public List<GlobalSetting> getGlobalSettingList() {
        return globalSettingList;
    }

    public void setGlobalSettingList(List<GlobalSetting> globalSettingList) {
        this.globalSettingList = globalSettingList;
    }

    public String save(){
        globalSettingService.update(globalSettingList);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "";
    }
}
