package net.sf.ahtutils.jsf.components;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent(value="net.sf.ahtutils.jsf.components.GenericListener")
public class GenericListener extends UINamingContainer
{
    public void listener()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression ajaxEventListener = (MethodExpression) getAttributes().get("listener");
        ajaxEventListener.invoke(context.getELContext(), new Object[] {});
    }
}