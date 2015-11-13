package com.vasax.clothes.managed.pageFront.cooperationPage;

import com.vasax.clothes.entities.component.ComponentType;
import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.managed.pageFront.Page;
import com.vasax.clothes.managed.pageFront.PageConfig;
import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.PageComponentService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Vasax on 10.05.2015.
 */
@Named
public class CooperationPageConfig extends PageConfig{

    @PostConstruct
    @Override
    public void pageConfig(){
        PageComponent cooperation = pageComponentService.require("cooperation", "Сотрудничество");
        componentService.require("textBlock", "Text block", ComponentType.richTextField, cooperation.getId());
    }
}
