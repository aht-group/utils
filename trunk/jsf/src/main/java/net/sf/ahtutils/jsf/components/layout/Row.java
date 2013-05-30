package net.sf.ahtutils.jsf.components.layout;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent("net.sf.ahtutils.jsf.components.layout.Row")
public class Row extends UIPanel
{	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", this);
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
}
