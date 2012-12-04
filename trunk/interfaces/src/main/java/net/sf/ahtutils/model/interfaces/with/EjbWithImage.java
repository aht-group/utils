package net.sf.ahtutils.model.interfaces.with;

import net.sf.ahtutils.model.interfaces.EjbWithId;

public interface EjbWithImage extends EjbWithId
{
	String getImage();
	void setImage(String image);
}
