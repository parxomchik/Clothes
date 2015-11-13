package com.vasax.clothes.util;

import com.vasax.clothes.entities.Item;
import com.vasax.clothes.managed.CartBean;
import com.vasax.clothes.managed.LoginBean;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Vasax on 15.05.2015.
 */
@Named
@Scope("session")
public class ItemUtil {
    @Inject
    private LoginBean loginBean;


    public boolean isSale(int saleValue, Timestamp start, Timestamp end) {
//        if(loginBean.isLinked() && loginBean.getUser().getDiscount() > 0){
//            return true;
//        }
        if (saleValue == 0)
            return false;
        start = getDateWithoutTime(start);
        end = getDateWithoutTime(end);
        if (start == null && end == null)
            return false;
        else {
            boolean isSale = false;
            if (start != null) {

                if (start.compareTo(new Date()) < 0) {
                    //start date valid
                    isSale = true;
                }
            } else {
                isSale = true;
            }
            if (end != null) {
                if (start != null && start.compareTo(end) > 0)
                    return false;
                //endless sale
                return end.compareTo(new Date()) > 0 && isSale;
            } else {
                return isSale;
            }
        }
    }

    private Timestamp getDateWithoutTime(Timestamp timestamp) {
        if (timestamp == null)
            return null;
        Date date = new Date(timestamp.getTime());                    // timestamp now
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
        return new Timestamp(cal.getTime().getTime());             // actually computes the new Date
    }
}
