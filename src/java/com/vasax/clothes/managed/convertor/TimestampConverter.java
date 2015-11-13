package com.vasax.clothes.managed.convertor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vasax on 15.05.2015.
 */
@FacesConverter("timestampConverter")
public class TimestampConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext,
                              UIComponent uIComponent,
                              String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(string);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, now.get(Calendar.SECOND));
        Timestamp result = new Timestamp(calendar.getTime().getTime());
        return result;
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent uIComponent,
                              Object object) {
        if (object == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        return sdf.format(object);
    }
}
