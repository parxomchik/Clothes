package com.vasax.clothes.util;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.inputtext.InputTextRenderer;

/**
 * Created by vasax32 on 28.04.15.
 */
public class MyInputTextRenderer extends InputTextRenderer {

    @Override
    protected void renderPassThruAttributes(FacesContext facesContext, UIComponent component, String[] attrs) throws IOException {
        String[] newAttrs = new String[attrs.length + 2];
        System.arraycopy(attrs, 0, newAttrs, 0, attrs.length);
        newAttrs[newAttrs.length - 2] = "min";
        newAttrs[newAttrs.length - 1] = "max";
        super.renderPassThruAttributes(facesContext, component, newAttrs);
    }

}
