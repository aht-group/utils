package org.jeesl.interfaces.model.with.text;

import java.util.Map;

import org.jeesl.interfaces.model.system.locale.JeeslMarkup;

public interface EjbWithMarkup <M extends JeeslMarkup<?>>
{
	Map<String,M> getMarkup();
	void setMarkup(Map<String,M> translation);
}