package com.vasax.clothes.entities.component;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Vasax on 04.05.2015.
 */
@Entity
@Table(name = "PlainComponent")
public class PlainComponent extends AbstractComponent{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPageComponent")
    protected PageComponent pageComponent;
    protected String fieldDesc;

//    @OneToMany(mappedBy = "plainComponent")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<PropertyForPlainComponent> propertyForPlainComponents;
    @ElementCollection
    @MapKeyColumn(name="keyR")
    @Column(name="valueR")
    @CollectionTable(name="propertyForPlainComponent", joinColumns=@JoinColumn(name="idPlainComponent"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, String> propertyForPlainComponents = new HashMap<>();

    @ElementCollection
    @MapKeyColumn(name="keyR")
    @Column(name="valueR")
    @CollectionTable(name="blobPropertyForPlainComponent", joinColumns=@JoinColumn(name="idPlainComponent"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, byte[]> blobPropertyForPlainComponents = new HashMap<>();

    public PageComponent getPageComponent() {
        return pageComponent;
    }

    public void setPageComponent(PageComponent pageComponent) {
        this.pageComponent = pageComponent;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }



    public Map<String, String> getPropertyForPlainComponents() {
        return propertyForPlainComponents;
    }

    public void setPropertyForPlainComponents(Map<String, String> propertyForPlainComponents) {
        this.propertyForPlainComponents = propertyForPlainComponents;
    }

    @Override
     public String getValue() {
        switch (componentType){
            case richTextField:{
                return propertyForPlainComponents.get("content");
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlainComponent that = (PlainComponent) o;

        if (pageComponent != null ? !pageComponent.equals(that.pageComponent) : that.pageComponent != null)
            return false;
        if (fieldDesc != null ? !fieldDesc.equals(that.fieldDesc) : that.fieldDesc != null) return false;
        return !(propertyForPlainComponents != null ? !propertyForPlainComponents.equals(that.propertyForPlainComponents) : that.propertyForPlainComponents != null);

    }

    @Override
    public int hashCode() {
        int result = pageComponent != null ? pageComponent.hashCode() : 0;
        result = 31 * result + (fieldDesc != null ? fieldDesc.hashCode() : 0);
        result = 31 * result + (propertyForPlainComponents != null ? propertyForPlainComponents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlainComponent{" +
                "pageComponent=" + pageComponent +
                ", fieldDesc='" + fieldDesc + '\'' +
                ", propertyForPlainComponents=" + propertyForPlainComponents +
                '}';
    }

    public Map<String, byte[]> getBlobPropertyForPlainComponents() {
        return blobPropertyForPlainComponents;
    }

    public List<String> getBlobPropertyForPlainComponentsKeysAsList() {
        return new ArrayList<>(blobPropertyForPlainComponents.keySet());
    }

    public void setBlobPropertyForPlainComponents(Map<String, byte[]> blobPropertyForPlainComponents) {
        this.blobPropertyForPlainComponents = blobPropertyForPlainComponents;
    }
}
