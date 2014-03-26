package net.sf.ahtutils.db.xml;

import net.sf.ahtutils.xml.dbseed.Db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAhtDbXmlInit extends AbstractDbRestInit
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtDbXmlInit.class);

	protected UtilsIdMapper idMapper;
	protected AhtStatusDbInit asdi;
	
	public AbstractAhtDbXmlInit(Db dbSeed, DataSource datasource, UtilsIdMapper idMapper, AhtStatusDbInit asdi)
	{
		super(dbSeed, datasource,null,idMapper);
		this.idMapper=idMapper;
		this.asdi=asdi;
	}
}