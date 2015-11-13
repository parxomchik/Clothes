package com.vasax.clothes.service;

import com.vasax.clothes.dao.component.BlobPropertyForPlainComponentDao;
import com.vasax.clothes.dao.component.PageComponentDao;
import com.vasax.clothes.dao.component.PlainComponentDao;
import com.vasax.clothes.dao.component.PropertyForPlainComponentDao;
import com.vasax.clothes.entities.component.PropertyForPlainComponent;
import com.vasax.clothes.entities.component.*;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Created by Vasax on 04.05.2015.
 */
@Named
public class ComponentService {

    @Inject
    private PlainComponentDao plainComponentDao;
    @Inject
    private PageComponentDao pageComponentDao;
    @Inject
    private PropertyForPlainComponentDao propertyForPlainComponentDao;
    @Inject
    private BlobPropertyForPlainComponentDao blobPropertyForPlainComponentDao;


    @Transactional
    public PlainComponent require(String identifier, String fieldDesc, ComponentType componentType, int idPageComponent){
        PageComponent pageComponent = pageComponentDao.selectById(idPageComponent);
        PlainComponent component = plainComponentDao.selectByIdentifierAndPageComponent(identifier, idPageComponent);
        if(component == null){
            component = buildPlainComponent(componentType);
            component.setIdentifier(identifier);
            component.setFieldDesc(fieldDesc);
            component.setPageComponent(pageComponent);
            component = plainComponentDao.update(component);
        }
        if(fieldDesc != null && !fieldDesc.equals(component.getFieldDesc())){
            component.setFieldDesc(fieldDesc);
            plainComponentDao.update(component);
        }

        return component;
    }

    @Transactional
    public PlainComponent buildPlainComponent(ComponentType componentType){
        PlainComponent plainComponent = new PlainComponent();
        plainComponent.setComponentType(componentType);
        plainComponent = plainComponentDao.insert(plainComponent);
        switch (componentType){
            case richTextField: {
                PropertyForPlainComponent content = new PropertyForPlainComponent("content", "");
                content.setPlainComponent(plainComponent);
                propertyForPlainComponentDao.insert(content);
                break;
            }
            case imageLink:{
                BlobPropertyForPlainComponent content = new BlobPropertyForPlainComponent("content", new byte[]{});
                content.setPlainComponent(plainComponent);
                blobPropertyForPlainComponentDao.insert(content);

                PropertyForPlainComponent imageName = new PropertyForPlainComponent("fileName", "");
                imageName.setPlainComponent(plainComponent);
                propertyForPlainComponentDao.insert(imageName);

                PropertyForPlainComponent link = new PropertyForPlainComponent("link", "");
                link.setPlainComponent(plainComponent);
                propertyForPlainComponentDao.insert(link);
                break;
            }
            case slider:{
                //3 images to slider
                for(int i = 0; i < 3; i++){
                    BlobPropertyForPlainComponent content = new BlobPropertyForPlainComponent("image" + i, new byte[]{});
                    content.setPlainComponent(plainComponent);
                    blobPropertyForPlainComponentDao.insert(content);

                    //link on any image form slider
                    PropertyForPlainComponent link = new PropertyForPlainComponent("link" + i, "");
                    link.setPlainComponent(plainComponent);
                    propertyForPlainComponentDao.insert(link);
                }
            }
        }
        return plainComponent;
    }

    @Transactional
    public List<PlainComponent> getAllByPageComponent(int idPageComponent){
        List<PlainComponent> components = plainComponentDao.selectAllByPageComponent(idPageComponent);
        return components;
    }

    @Transactional
    public Map<String, PlainComponent> getAllByPageComponentAsMap(int idPageComponent){
        List<PlainComponent> components = plainComponentDao.selectAllByPageComponent(idPageComponent);
        Map<String, PlainComponent> componentMap = new HashMap<>();
        for (PlainComponent component : components) {
            componentMap.put(component.getIdentifier(), component);
        }
        return componentMap;
    }

    @Transactional
    public void update(List<PlainComponent> components) {
        for (PlainComponent component : components) {
            plainComponentDao.update(component);
//            for (PropertyForPlainComponent propertyForPlainComponent : component.getPropertyForPlainComponents()) {
//                propertyForPlainComponentDao.update(propertyForPlainComponent);
//            }
        }
    }

    @Transactional
    public PlainComponent update(PlainComponent plainComponent) {
        return plainComponentDao.update(plainComponent);
    }
}
