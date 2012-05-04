package net.sf.ahtutils.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import net.sf.ahtutils.model.interfaces.EjbWithId;

@FacesConverter("TgComTypeConverter")
public class AbstractEjbIdConverter <I extends EjbWithId> implements Converter
{      
	final Class<I> clEjb;
	
	public AbstractEjbIdConverter(final Class<I> clEjb)
	{
		this.clEjb=clEjb;
	}
	
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue)
    {
        if (submittedValue.trim().equals("")) {return null;}
        else
        {  
            try
            {   
                long id = Long.valueOf(submittedValue.trim());
                I ejb = clEjb.newInstance();
                ejb.setId(id);
                return ejb;
            }
            catch(NumberFormatException exception) {throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid id"));}
            catch (InstantiationException e) {throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", e.getMessage()));}
            catch (IllegalAccessException e) {throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", e.getMessage()));}
       }
    }  
  
    public String getAsString(FacesContext facesContext, UIComponent component, Object value)
    { 
        if (value == null || value.equals("")) {return "";}
        else
        {
        	EjbWithId ejb = (EjbWithId)value;
        	return ""+ejb.getId(); 
        }  
    }  
}  