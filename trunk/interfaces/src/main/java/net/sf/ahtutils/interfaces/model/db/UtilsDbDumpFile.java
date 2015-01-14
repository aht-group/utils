package net.sf.ahtutils.interfaces.model.db;

import net.sf.ahtutils.interfaces.model.date.EjbWithDateRange;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsDbDumpFile extends EjbWithId,EjbWithDateRange,EjbWithName
{
	long getSize();
	void setSize(long size);
}