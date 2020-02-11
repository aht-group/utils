package org.jeesl.interfaces.model.with.text;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithFileType extends EjbWithId
{
	String getFileType();
	void setFileType(String fileType);
}
