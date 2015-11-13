package com.vasax.clothes.managed.pageFront.indexPage;

import com.vasax.clothes.entities.component.ComponentType;
import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.managed.pageFront.PageConfig;
import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.PageComponentService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;

/**
 * Created by Vasax on 08.05.2015.
 */
@Named
public class IndexPageConfig extends PageConfig{

    @PostConstruct
    @Override
    public void pageConfig(){
        PageComponent index = pageComponentService.require("index", "Главная");
        componentService.require("banner1", "Banner 1", ComponentType.imageLink, index.getId());
        componentService.require("banner2", "Banner 2", ComponentType.imageLink, index.getId());
        componentService.require("slider", "Slider", ComponentType.slider, index.getId());
        componentService.require("textBlock1", "Text block 1", ComponentType.richTextField, index.getId());
        componentService.require("textBlock2", "Text block 2", ComponentType.richTextField, index.getId());
        componentService.require("textBlock3", "Text block 3", ComponentType.richTextField, index.getId());

    }
}
