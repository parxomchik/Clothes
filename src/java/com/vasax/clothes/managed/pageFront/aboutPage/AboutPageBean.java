package com.vasax.clothes.managed.pageFront.aboutPage;

import com.vasax.clothes.managed.pageFront.Page;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
 * Created by Vasax on 10.05.2015.
 */
@Named
@Scope("view")
public class AboutPageBean extends Page{

    @PostConstruct
    public void init(){
        pageComponent = pageComponentService.require("about");
        components = componentService.getAllByPageComponentAsMap(pageComponent.getId());
    }

}
