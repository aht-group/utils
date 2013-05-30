package net.sf.ahtutils.jsf.components.layout;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

@FacesComponent("net.sf.ahtutils.jsf.components.layout.Slot")
public class Slot extends UIPanel
{
	private static enum Properties {width,styleClass}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", this);
		
		Map<String,Object> map = getAttributes();
		StringBuffer sbStyleClass = new StringBuffer();
		sbStyleClass.append("grid_");
		if(map.containsKey(Properties.width.toString()))
		{
			sbStyleClass.append(map.get(Properties.width.toString()));
		}
		else {sbStyleClass.append(12);}
		if(map.containsKey(Properties.styleClass.toString()))
		{
			sbStyleClass.append(" ").append(map.get(Properties.styleClass.toString()));
		}
		responseWriter.writeAttribute("class",sbStyleClass.toString(),null);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("div");
	}
}
