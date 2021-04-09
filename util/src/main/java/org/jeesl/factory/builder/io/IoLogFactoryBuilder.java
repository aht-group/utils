package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.io.log.EjbIoLogMilestoneFactory;
import org.jeesl.interfaces.model.io.logging.JeeslIoLog;
import org.jeesl.interfaces.model.io.logging.JeeslIoLogMilestone;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoLogFactoryBuilder<L extends JeeslLang,D extends JeeslDescription,
								LOG extends JeeslIoLog<L,D,?,?,?>,

								MILESTONE extends JeeslIoLogMilestone<LOG>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoLogFactoryBuilder.class);
	
	private final Class<MILESTONE> cMileStone;  public Class<MILESTONE> getClassRetention(){return cMileStone;}
	
	public IoLogFactoryBuilder(final Class<L> cL, final Class<D> cD,
								final Class<MILESTONE> cMileStone)
	{
		super(cL,cD);
		this.cMileStone=cMileStone;
	}
	
	public EjbIoLogMilestoneFactory<LOG,MILESTONE> ejbMilestone()
	{
		return new EjbIoLogMilestoneFactory<>(cMileStone);
	}
}