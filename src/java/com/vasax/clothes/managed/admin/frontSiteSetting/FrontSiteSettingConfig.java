package com.vasax.clothes.managed.admin.frontSiteSetting;

import com.vasax.clothes.service.GlobalSettingService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Vasax on 05.05.2015.
 */
@Named
public class FrontSiteSettingConfig {

    @Inject
    private GlobalSettingService globalSettingService;

    @PostConstruct
    public void config(){
        globalSettingService.requireDouble("exchangeRate", 23.50);
        globalSettingService.requireBoolean("showNotActive", false);
        globalSettingService.require("siteTitle", "zermon.ua");
    }
}
