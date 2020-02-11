package org.jeesl.interfaces.model.system.io.db;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslDbDumpFile<DUMP extends JeeslDbDump<?,?>,
								HOST extends JeeslDbDumpHost<?,?,HOST,?>,
								STATUS extends JeeslDbDumpStatus<?,?,STATUS,?>>
					extends Serializable,EjbSaveable,EjbRemoveable,EjbWithId
{
	public static enum Attributes{dump,host,status}
	public static enum Status{stored,flagged,deleted};
	
	DUMP getDump();
	void setDump(DUMP dump);
	
	HOST getHost();
	void setHost(HOST host);
	
	STATUS getStatus();
	void setStatus(STATUS status);
}