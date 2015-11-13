package com.vasax.clothes.managed;

import javax.inject.Named;

/**
 * Created by vasax32 on 03.11.14.
 */
@Named
public class ConstantsBean {

    private String projectName = "Zermon";
    private String prefix = "app";
    private String siteTitle = "zermon.ua";

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSiteTitle() {
        return siteTitle;
    }
}
