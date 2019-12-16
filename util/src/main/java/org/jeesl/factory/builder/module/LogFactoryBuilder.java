package org.jeesl.factory.builder.module;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.log.EjbLogBookFactory;
import org.jeesl.factory.ejb.module.log.EjbLogItemFactory;
import org.jeesl.interfaces.model.module.log.JeeslLogBook;
import org.jeesl.interfaces.model.module.log.JeeslLogConfidentiality;
import org.jeesl.interfaces.model.module.log.JeeslLogImpact;
import org.jeesl.interfaces.model.module.log.JeeslLogItem;
import org.jeesl.interfaces.model.module.log.JeeslLogScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class LogFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
								LOG extends JeeslLogBook<SCOPE,ITEM>,
								SCOPE extends JeeslLogScope<L,D,SCOPE,?>,
								ITEM extends JeeslLogItem<L,D,?,?,LOG,IMPACT,CONF,USER>,
								IMPACT extends JeeslLogImpact<L,D,IMPACT,?>,
								CONF extends JeeslLogConfidentiality<L,D,CONF,?>,
								USER extends EjbWithId
								>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(LogFactoryBuilder.class);
	
	private final Class<LOG> cLog;
	private final Class<ITEM> cItem; public Class<ITEM> getClassItem() {return cItem;}
	private final Class<IMPACT> cImpact;
	private final Class<USER> cUser;

	public LogFactoryBuilder(final Class<L> cL,final Class<D> cD,
							 final Class<LOG> cLog,
							 final Class<ITEM> cItem,
							 final Class<IMPACT> cImpact,
							 final Class<USER> cUser)
	{       
		super(cL,cD);
        this.cLog = cLog;
        this.cItem = cItem;
        this.cImpact = cImpact;
        this.cUser = cUser;
	}
	
	public EjbLogBookFactory<LOG,SCOPE> log(){return new EjbLogBookFactory<>(cLog);}
	public EjbLogItemFactory<LOG,ITEM,IMPACT,CONF,USER> item(){return new EjbLogItemFactory<>(cItem,cImpact,cUser);}
}