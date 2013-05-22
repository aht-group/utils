package net.sf.ahtutils.jsf.components;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent(value="net.sf.ahtutils.jsf.components.ComponentActionListener")
public class ComponentActionListener extends UINamingContainer
{
    public void actionListener()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpression ajaxEventListener = (MethodExpression) getAttributes().get("actionListener");
        ajaxEventListener.invoke(context.getELContext(), new Object[] {});
    }
}