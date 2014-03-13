package net.sf.ahtutils.interfaces.model.with;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface EjbWithFileType extends EjbWithId
{
	String getFileType();
	void setFileType(String fileType);
}
