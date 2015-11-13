package com.vasax.clothes.util;

/**
 * Created by root on 30.11.14.
 */

import com.sun.faces.renderkit.html_basic.FileRenderer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;


@FacesRenderer(componentFamily = "javax.faces.Input",
        rendererType = "com.sun.faces.renderkit.html_basic.FileRenderer")
public class FileUploadRenderer extends FileRenderer {

    @Override
    public void decode(FacesContext context, UIComponent component) {
        rendererParamsNotNull(context, component);

        if (!shouldDecode(component)) {
            return;
        }

        String clientId = decodeBehaviors(context, component);

        if (clientId == null) {
            clientId = component.getClientId(context);
        }

        assert(clientId != null);
        ExternalContext externalContext = context.getExternalContext();
        Map<String, String> requestMap = externalContext.getRequestParameterMap();

        if (requestMap.containsKey(clientId)) {
            setSubmittedValue(component, requestMap.get(clientId));
        }

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            Collection<Part> parts = request.getParts();
            for (Part cur : parts) {
                if (clientId.equals(cur.getName())) {
                    component.setTransient(true);
                    setSubmittedValue(component, cur);
                }
            }
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        } catch (ServletException se) {
            throw new FacesException(se);
        }
    }
}
