package com.vasax.clothes.managed.pageFront.infoAboutMakingOfOrderPage;

import com.vasax.clothes.entities.component.ComponentType;
import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.managed.pageFront.PageConfig;

import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
 * Created by Vasax on 10.05.2015.
 */
@Named
public class InfoAboutMakingOfOrderPageConfig extends PageConfig{

    @PostConstruct
    @Override
    public void pageConfig(){
        PageComponent cooperation = pageComponentService.require("infoAboutMakingOfOrder", "Оформление заказа");
        componentService.require("textBlock", "Text block", ComponentType.richTextField, cooperation.getId());
    }
}
