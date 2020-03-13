package org.jeesl.factory.ejb.io.fr;

import org.jeesl.factory.builder.io.IoFileRepositoryFactoryBuilder;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.io.fr.JeeslFileReplication;
import org.jeesl.interfaces.model.io.fr.JeeslFileReplicationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbIoFrReplicationFactory<REPLICATION extends JeeslFileReplication<?,?,?,?,RTYPE>,
										RTYPE extends JeeslFileReplicationType<?,?,RTYPE,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoFrReplicationFactory.class);
	
	private final IoFileRepositoryFactoryBuilder<?,?,?,?,?,?,?,?,?,?,REPLICATION,RTYPE,?> fbFr;
    
	public EjbIoFrReplicationFactory(final IoFileRepositoryFactoryBuilder<?,?,?,?,?,?,?,?,?,?,REPLICATION,RTYPE,?> fbFr)
	{       
        this.fbFr = fbFr;
	}
	
	public REPLICATION build(RTYPE type)
	{
		REPLICATION ejb = null;
		try
		{
			 ejb = fbFr.getClassReplication().newInstance();
			 ejb.setType(type);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public void converter(JeeslFacade facade, REPLICATION replication)
	{
		
	}
}