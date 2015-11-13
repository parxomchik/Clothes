package com.vasax.clothes.managed.admin.frontSiteSetting;

import com.vasax.clothes.entities.GlobalSetting;
import com.vasax.clothes.entities.enums.CurrencyType;
import com.vasax.clothes.managed.LoginBean;
import com.vasax.clothes.service.GlobalSettingService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Vasax on 03.05.2015.
 */
@Named
@Scope("view")
public class FrontSiteSettings {

    private double grnInUsd;

    @Inject
    private GlobalSettingService globalSettingService;
    @Inject
    private LoginBean loginBean;

    @PostConstruct
    public void init(){
        grnInUsd = globalSettingService.requireDouble("exchangeRate");
    }

    public String formatPrice(double price){
        CurrencyType currencyType = loginBean.getCurrencyType();
        switch (currencyType){
            case grn: return round(price * grnInUsd, 2) + " грн";
            case usd: return "$" + round(price, 2);
        }
        return Double.toString(price);
    }

    public Double getPriceInCurrentCurrency(double price){
        CurrencyType currencyType = loginBean.getCurrencyType();
        switch (currencyType){
            case grn: return (price * grnInUsd);
            default: usd: return price;
        }
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Double getPriceInNativeCurrency(Integer price) {
        CurrencyType currencyType = loginBean.getCurrencyType();
        switch (currencyType){
            case grn: return (((double)price) / grnInUsd);
            default: usd: return (double)price;
        }
    }
}
