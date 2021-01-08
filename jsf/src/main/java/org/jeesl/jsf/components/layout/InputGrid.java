package org.jeesl.jsf.components.layout;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import net.sf.ahtutils.jsf.util.ComponentAttribute;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.primefaces.component.outputlabel.OutputLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent("org.jeesl.jsf.components.layout.InputGrid")
public class InputGrid extends UIPanel
{
	final static Logger logger = LoggerFactory.getLogger(InputGrid.class);
	private static enum Properties {id,inputFields,styleClass,renderChildren,renderChildrenIfEjb,renderChildrenIfEjbPersisted}
	
	@Override public boolean getRendersChildren(){return true;}
	
	private Boolean renderChilds;
	public Boolean getRenderChilds() {return renderChilds;}
	public void setRenderChilds(Boolean renderChilds) {this.renderChilds = renderChilds;}
	
	private AtomicInteger counter = new AtomicInteger(0);
	private int columnCount = 2;
	
	private int classifyChildGroup()
	{
		final int i = counter.getAndIncrement();
		int remainder = i % columnCount;
		return (remainder == 0) ? i : i - remainder;
	}

	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		Map<String,Object> map = getAttributes();
		
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", this);
		responseWriter.writeAttribute("id",getClientId(context),"id");
		
		StringBuffer sbStyleClass = new StringBuffer();
		sbStyleClass.append("ui-fluid");
		
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
		String inputCount = ComponentAttribute.get(Properties.inputFields, "1", context, this);
		try
		{
			columnCount = Integer.parseInt(inputCount) + 1;
		}
		catch(NumberFormatException e) {}
		
		boolean rChildren = ComponentAttribute.getBoolean(Properties.renderChildren, true, context, this);
		
		if(rChildren && ComponentAttribute.available(Properties.renderChildrenIfEjb,context,this))
		{
			EjbWithId ejb = ComponentAttribute.getObject(EjbWithId.class,Properties.renderChildrenIfEjb.toString(),context,this);
			if(ejb==null){rChildren=false;}
		}
		
		if(rChildren && ComponentAttribute.available(Properties.renderChildrenIfEjbPersisted,context,this))
		{
			EjbWithId ejb = ComponentAttribute.getObject(EjbWithId.class,Properties.renderChildrenIfEjbPersisted.toString(),context,this);
			if(ejb==null){rChildren=false;}
			else if(ejb.getId()==0){rChildren=false;}
		}
		
		if(rChildren)
		{
			counter = new AtomicInteger(0);
			List<List<UIComponent>> childGroup = this.getChildren().stream().collect(Collectors.groupingBy(child -> classifyChildGroup())).values().stream().collect(Collectors.toList());
			
			float inputWidth = 9 / (columnCount - 1);
			
			for (List<UIComponent> group : childGroup)
			{
				UIPanel groupChild = new UIPanel();
				ResponseWriter responseWriter = context.getResponseWriter();
				responseWriter.startElement("div", groupChild);
				responseWriter.writeAttribute("class","p-field p-grid",null);
				
				for (UIComponent child : group)
				{
					if (child instanceof OutputLabel)
					{
						Map<String, Object> attributes = child.getAttributes();
						String styleClass = attributes.containsKey("styleClass") ? (String)attributes.get("styleClass") + " p-col p-md-3" : "p-col p-md-3";
						attributes.put("styleClass", styleClass);
						
						child.encodeAll(context);
					}
					else
					{
						
        				UIPanel inputChild = new UIPanel();
        				responseWriter.startElement("div", inputChild);
        				responseWriter.writeAttribute("class", "p-col p-md-" + (int)inputWidth, null);
        				responseWriter.writeAttribute("style", "width: " + ((100f/12) * inputWidth) + "%;", null);
						
        				child.encodeAll(context);
				
        				responseWriter.endElement("div");
					}
				}
				
				responseWriter.endElement("div");
			}
			
//			for(UIComponent uic : this.getChildren())
//			{
//				uic.encodeAll(context);
//			}
		}
	}
}