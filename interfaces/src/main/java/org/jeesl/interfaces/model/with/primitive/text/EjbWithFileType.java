package org.jeesl.interfaces.model.with.primitive.text;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface EjbWithFileType extends EjbWithId
{
	String getFileType();
	void setFileType(String fileType);
}
