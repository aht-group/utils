package net.sf.ahtutils.web.demo;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("DemoConverter")
public class DemoConverter implements Converter {
	
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	return value;
    }
    

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	DemoDataType obj = (DemoDataType) value;
    	return obj.getValue();
    }

}
