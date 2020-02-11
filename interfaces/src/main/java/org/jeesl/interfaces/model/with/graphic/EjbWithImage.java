package org.jeesl.interfaces.model.with.graphic;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithImage extends EjbWithId
{
	String getImage();
	void setImage(String image);
}