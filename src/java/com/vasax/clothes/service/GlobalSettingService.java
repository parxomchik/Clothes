package com.vasax.clothes.service;

import com.vasax.clothes.dao.GlobalSettingDao;
import com.vasax.clothes.entities.GlobalSetting;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.beans.Transient;
import java.util.List;

/**
 * Created by Vasax on 03.05.2015.
 */
@Named
public class GlobalSettingService {
    @Inject
    private GlobalSettingDao globalSettingDao;

    @Transactional
    public String require(String key, String defaultValue){
        //try to get wanted value
        String value = globalSettingDao.selectByKey(key);
        if(value == null){
            //need to create value
            GlobalSetting globalSetting = new GlobalSetting(key, defaultValue);
            globalSettingDao.insert(globalSetting);
            return defaultValue;
        } else {
            return value; //got actual value and return it
        }
    }

    @Transactional
    public String require(String key){
        return require(key, "");
    }

    @Transactional
    public Double requireDouble(String key, Double value){
        try {
            return Double.valueOf(require(key, Double.toString(value)));
        } catch (NumberFormatException e){
            return 0.0;
        }
    }

    @Transactional
    public Double requireDouble(String key){
        return Double.valueOf(require(key, "1.0"));
    }

    @Transactional
    public List<GlobalSetting> getAll(){
        return globalSettingDao.selectAll();
    }

    @Transactional
    public void update(List<GlobalSetting> globalSettingList) {
        for (GlobalSetting globalSetting : globalSettingList) {
            globalSettingDao.update(globalSetting);
        }
    }

    @Transactional
    public Boolean requireBoolean(String key, Boolean value) {
        try {
            return Boolean.valueOf(require(key, Boolean.toString(value)));
        } catch (NumberFormatException e){
            return false;
        }
    }

    @Transactional
    public Boolean requireBoolean(String key) {
        return requireBoolean(key, false);
    }
}
