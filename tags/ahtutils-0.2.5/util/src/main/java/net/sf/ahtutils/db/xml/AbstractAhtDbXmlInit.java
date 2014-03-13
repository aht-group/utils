package net.sf.ahtutils.db.xml;

import net.sf.ahtutils.xml.dbseed.Db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAhtDbXmlInit extends AbstractDbRestInit
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtDbXmlInit.class);

	protected AhtXmlInitIdMapper idMapper;
	protected AhtStatusDbInit asdi;
	
	public AbstractAhtDbXmlInit(Db dbSeed, DataSource datasource, AhtXmlInitIdMapper idMapper, AhtStatusDbInit asdi)
	{
		super(dbSeed, datasource,null);
		this.idMapper=idMapper;
		this.asdi=asdi;
	}
}