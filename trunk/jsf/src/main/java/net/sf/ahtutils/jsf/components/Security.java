package net.sf.ahtutils.jsf.components;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

import net.sf.ahtutils.interfaces.web.UtilsJsfSecurityHandler;
import net.sf.ahtutils.jsf.util.ComponentAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent("net.sf.ahtutils.jsf.components.Security")
public class Security extends UIPanel
{
	final static Logger logger = LoggerFactory.getLogger(Security.class);
	private static enum Properties {action,handler}
	private static enum Facets {denied}
	
	@Override public boolean getRendersChildren(){return true;}
	
	private Boolean renderChilds;
	
	public Boolean getRenderChilds() {return renderChilds;}
	public void setRenderChilds(Boolean renderChilds) {this.renderChilds = renderChilds;}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException
	{
		ValueExpression ve = this.getValueExpression(Properties.handler.toString());
		UtilsJsfSecurityHandler handler = (UtilsJsfSecurityHandler)ve.getValue(context.getELContext());
		
		String action = ComponentAttribute.get("null",Properties.action.toString(),context,this);

		if(handler.allow())
		{
			for(UIComponent uic : this.getChildren())
			{
				uic.encodeAll(context);
			}
		}
		else if(this.getFacets().containsKey(Facets.denied.toString()))
		{
			this.getFacet(Facets.denied.toString()).encodeAll(context);
		}
	}
}