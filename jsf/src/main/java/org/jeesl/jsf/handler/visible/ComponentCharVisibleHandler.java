package org.jeesl.jsf.handler.visible;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentCharVisibleHandler
{
	final static Logger logger = LoggerFactory.getLogger(ComponentCharVisibleHandler.class);
	
	private boolean showA; public boolean isShowA() {return showA;}
	private boolean showB; public boolean isShowB() {return showB;}

	private final int size;

	public ComponentCharVisibleHandler(int size)
	{
		this.size=size;
	}
	
	public void set(boolean showA)
	{
		if(size!=1) {warning(1);}
		this.showA=showA;
	}
	
	public void set(boolean showA, boolean showB)
	{
		if(size!=2) {warning(1);}
		this.showA=showA;
		this.showB=showB;
	}
	
	private void warning(int i)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Involing set(..").append(i).append("..) Method");
		sb.append(" does not match the defined size of ").append(size);
		logger.warn(sb.toString());
	}
}