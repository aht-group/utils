package net.sf.ahtutils.interfaces.model.db;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface UtilsDbDumpFile extends EjbWithId,EjbWithRecord,EjbWithName
{
	long getSize();
	void setSize(long size);
}