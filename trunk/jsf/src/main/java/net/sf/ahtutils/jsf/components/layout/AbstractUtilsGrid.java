package net.sf.ahtutils.jsf.components.layout;

import javax.faces.component.UIOutput;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractUtilsGrid extends UIPanel
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsGrid.class);
	protected static enum Properties {width,gutter,styleClass}
	
	public void useCss(int width, int gutter) throws AbortProcessingException
	{
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
	}
}
