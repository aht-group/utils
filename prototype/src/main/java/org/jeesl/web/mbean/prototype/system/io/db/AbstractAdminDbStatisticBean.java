package org.jeesl.web.mbean.prototype.system.io.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpHost;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminDbStatisticBean <L extends JeeslLang, D extends JeeslDescription,
											SYSTEM extends JeeslIoSsiSystem,
											DUMP extends JeeslDbDump<SYSTEM,FILE>,
											FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
											HOST extends JeeslDbDumpHost<L,D,HOST,?>,
											STATUS extends JeeslDbDumpStatus<L,D,STATUS,?>> 
implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminDbStatisticBean.class);
	
	protected JeeslIoDbFacade<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fDb;
	
	protected List<Class<?>> list = new ArrayList<Class<?>>();
	public List<Class<?>> getList(){return list;}
	
	private Map<Class<?>,Long> map;
	public Map<Class<?>, Long> getMap(){return map;}

	public AbstractAdminDbStatisticBean()
	{
		list = new ArrayList<Class<?>>();
	}
	
	public void refresh()
	{
		map = fDb.count(list);
		for(Class<?> cl : list)
		{
			logger.info(cl.getSimpleName()+": "+map.get(cl));
		}
	}
}