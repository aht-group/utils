package net.sf.ahtutils.jsf.components.layout;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import net.sf.ahtutils.jsf.util.ComponentAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent("net.sf.ahtutils.jsf.components.layout.Slot")
public class Slot extends UIPanel
{
	final static Logger logger = LoggerFactory.getLogger(Slot.class);
	private static enum Properties {id,width,styleClass,renderChildren}
	
	@Override public boolean getRendersChildren(){return true;}
	
	private Boolean renderChilds;
	
	public Boolean getRenderChilds() {return renderChilds;}
	public void setRenderChilds(Boolean renderChilds) {this.renderChilds = renderChilds;}

	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		Map<String,Object> map = getAttributes();
		
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", this);
		responseWriter.writeAttribute("id",getClientId(context),"id");
		
		StringBuffer sbStyleClass = new StringBuffer();
		sbStyleClass.append("auGrid_");
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