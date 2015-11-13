package com.vasax.clothes.managed.pageFront;

import com.vasax.clothes.entities.component.PageComponent;
import com.vasax.clothes.entities.component.PlainComponent;
import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.PageComponentService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

/**
 * Created by Vasax on 10.05.2015.
 */
@Component
public abstract class Page {
    @Inject
    protected PageComponentService pageComponentService;
    @Inject
    protected ComponentService componentService;

    protected PageComponent pageComponent;
    protected Map<String, PlainComponent> components;


    public PageComponent getPageComponent() {
        return pageComponent;
    }

    public void setPageComponent(PageComponent pageComponent) {
        this.pageComponent = pageComponent;
    }

    public Map<String, PlainComponent> getComponents() {
        return components;
    }

    public void setComponents(Map<String, PlainComponent> components) {
        this.components = components;
    }
}
