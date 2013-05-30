package net.sf.ahtutils.jsf.components.layout;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent("net.sf.ahtutils.jsf.components.layout.Grid")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Grid extends UIPanel
{	
	final static Logger logger = LoggerFactory.getLogger(Grid.class);
	private static enum Properties {width,gutter,styleClass}
	
	 @Override
	 public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	 {
//		 logger.info("processEvent "+event.getClass().getName());
		 if(event instanceof PostAddToViewEvent)
		 {
			 Map<String,Object> map = getAttributes();
			
			 int width=70;
			 int gutter=5;
				if(map.containsKey(Properties.width.toString())) {width = new Integer(map.get(Properties.width.toString()).toString());}
				if(map.containsKey(Properties.gutter.toString())){gutter = new Integer(map.get(Properties.gutter.toString()).toString());}
				
				StringBuffer sbCss = new StringBuffer();
				sbCss.append("grid-");
				sbCss.append(width).append("-").append(gutter);
				sbCss.append(".css");
				
				UIOutput css = new UIOutput();
				css.setRendererType("javax.faces.resource.Stylesheet");
				css.getAttributes().put("library", "ahtutilsCss");
				css.getAttributes().put("name", sbCss.toString());
				
				FacesContext context = this.getFacesContext();
				context.getViewRoot().addComponentResource(context, css, "head");
//				logger.info("Adding "+sbCss.toString());
		 }
		super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{
		Map<String,Object> map = getAttributes();
		
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("div", this);
		
		
		StringBuffer sbStyleClass = new StringBuffer();
		sbStyleClass.append("container_12");
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
