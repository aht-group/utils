package org.jeesl.interfaces.model.with.system.graphic;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithImage extends EjbWithId
{
	String getImage();
	void setImage(String image);
}