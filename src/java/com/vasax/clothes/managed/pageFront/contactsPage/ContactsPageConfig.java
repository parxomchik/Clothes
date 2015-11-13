package com.vasax.clothes.managed.pageFront.contactsPage;

import com.vasax.clothes.entities.component.ComponentType;
import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.managed.pageFront.PageConfig;
import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.PageComponentService;

 import javax.annotation.PostConstruct;
 import javax.inject.Inject;
 import javax.inject.Named;

 /**
 * Created by Vasax on 05.05.2015.
 */
@Named
public class ContactsPageConfig extends PageConfig{

    @PostConstruct
    @Override
    public void pageConfig(){
        PageComponent contacts = pageComponentService.require("contacts", "Контакты");
        componentService.require("mainPhone", "Main phones", ComponentType.richTextField, contacts.getId());
        componentService.require("hotLinePhone", "Hot line phones", ComponentType.richTextField, contacts.getId());
        componentService.require("onlineEmailSkype", "We online", ComponentType.richTextField, contacts.getId());
    }
}
