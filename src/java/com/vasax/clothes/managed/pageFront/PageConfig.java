package com.vasax.clothes.managed.pageFront;

import com.vasax.clothes.service.ComponentService;
import com.vasax.clothes.service.PageComponentService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Vasax on 10.05.2015.
 */
@Component
public abstract class PageConfig {
    @Inject
    protected PageComponentService pageComponentService;
    @Inject
    protected ComponentService componentService;

    public abstract void pageConfig();
}
