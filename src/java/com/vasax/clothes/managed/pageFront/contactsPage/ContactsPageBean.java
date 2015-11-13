package com.vasax.clothes.managed.pageFront.contactsPage;

import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.entities.component.PlainComponent;
import com.vasax.clothes.managed.pageFront.Page;
import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.PageComponentService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
@Scope("view")
public class ContactsPageBean extends Page{

    @PostConstruct
    public void init(){
        pageComponent = pageComponentService.require("contacts");
        components = componentService.getAllByPageComponentAsMap(pageComponent.getId());
    }
}
