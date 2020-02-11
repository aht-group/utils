package org.jeesl.interfaces.model.system.locale;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;

public interface JeeslMarkup <T extends JeeslIoCmsMarkupType<?,?,T,?>> 
		extends Serializable,EjbSaveable 
{	
	T getType();
	void setType(T type);
	
	String getLkey();
	public void setLkey(String lkey);
	
	String getContent();
	void setContent(String content);
}