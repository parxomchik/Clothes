package com.vasax.clothes.entities.component;

import javax.persistence.*;

/**
 * Created by Vasax on 04.05.2015.
 */
@MappedSuperclass
public abstract class AbstractComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String identifier;
    @Enumerated(EnumType.STRING)
    protected ComponentType componentType;

    public <T> T getAs(Class<T> type){
        switch (componentType){
            case page: return type.cast(this);
            default: return null;
        }
    }

    public boolean isPage(){
        return componentType == ComponentType.page;
    }

    public boolean isRichTextField(){
        return getComponentType() == ComponentType.richTextField;
    }

    public boolean isImageLink(){
        return getComponentType() == ComponentType.imageLink;
    }

    public boolean isSlider(){
        return getComponentType() == ComponentType.slider;
    }

    public abstract String getValue();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }
}
