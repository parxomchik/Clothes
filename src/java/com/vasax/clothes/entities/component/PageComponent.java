package com.vasax.clothes.entities.component;

import javax.persistence.*;

/**
 * Created by Vasax on 04.05.2015.
 */
@Entity
@Table(name = "PageComponent")
public class PageComponent extends AbstractComponent{

    private String title;
    private String metaDesc;
    private String metaKeywords;

    public PageComponent() {
        componentType = ComponentType.page;
    }

    @Override
    public String getValue() {
        return title;
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.page;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetaDesc() {
        return metaDesc;
    }

    public void setMetaDesc(String metaDesc) {
        this.metaDesc = metaDesc;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageComponent that = (PageComponent) o;

        if (id != that.id) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (metaDesc != null ? !metaDesc.equals(that.metaDesc) : that.metaDesc != null) return false;
        if (metaKeywords != null ? !metaKeywords.equals(that.metaKeywords) : that.metaKeywords != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (metaDesc != null ? metaDesc.hashCode() : 0);
        result = 31 * result + (metaKeywords != null ? metaKeywords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PageComponent{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", title='" + title + '\'' +
                ", metaDesc='" + metaDesc + '\'' +
                ", metaKeywords='" + metaKeywords + '\'' +
                '}';
    }
}
