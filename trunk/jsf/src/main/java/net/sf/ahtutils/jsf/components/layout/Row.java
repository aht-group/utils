package net.sf.ahtutils.jsf.components.layout;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import net.sf.ahtutils.jsf.util.ComponentAttribute;

@FacesComponent("net.sf.ahtutils.jsf.components.layout.Row")
public class Row extends UIPanel
{	
	private static enum Properties {renderChildren}
	
	@Override public boolean getRendersChildren(){return true;}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", this);
		responseWriter.writeAttribute("id",getClientId(context),"id");
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("div");
		
		responseWriter.startElement("div", this);
		responseWriter.writeAttribute("class","clear",null);
		responseWriter.endElement("div");
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException
	{
		if(ComponentAttribute.getBoolean(Properties.renderChildren.toString(), true, context, this))
		{
			for(UIComponent uic : this.getChildren())
			{
				uic.encodeAll(context);
			}
		}
	}
}