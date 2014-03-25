package net.sf.ahtutils.jsf.components.html;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import net.sf.ahtutils.jsf.util.ComponentAttribute;

@FacesComponent("net.sf.ahtutils.jsf.components.html.Li")
public class Li extends UIPanel
{	
	private static enum Properties {styleClass}
	
	@Override public boolean getRendersChildren(){return true;}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("li", this);
		
		responseWriter.writeAttribute("class",ComponentAttribute.get(Properties.styleClass.toString(), "", context, this),null);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("li");
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException
	{
		for(UIComponent uic : this.getChildren())
		{
			uic.encodeAll(context);
		}
	}
}