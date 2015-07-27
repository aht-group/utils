package net.sf.ahtutils.interfaces.web;

import java.util.Map;

public interface UtilsJsfOptionTableBean
{
	boolean getSupportsCodeChange();
	boolean getSupportsSymbol();
	boolean getSupportsGraphic();
	
	Map<Long, Boolean> getAllowAdditionalElements();
	
	void changeGraphicType();
}