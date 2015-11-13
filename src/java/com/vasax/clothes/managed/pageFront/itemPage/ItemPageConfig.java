package com.vasax.clothes.managed.pageFront.itemPage;

import com.vasax.clothes.entities.component.ComponentType;
import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.managed.pageFront.PageConfig;

import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
* Created by Vasax on 05.05.2015.
*/
@Named
public class ItemPageConfig extends PageConfig{

   @PostConstruct
   @Override
   public void pageConfig(){
       PageComponent item = pageComponentService.require("item", "Товар");
       componentService.require("deliveryAndPayment", "Доставка и оплата", ComponentType.richTextField, item.getId());
   }
}
