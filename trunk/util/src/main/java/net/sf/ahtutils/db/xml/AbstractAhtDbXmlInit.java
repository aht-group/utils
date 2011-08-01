package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.db.xml.AhtDbXmlInit.Priority;
import net.sf.ahtutils.xml.dbseed.Db;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractAhtDbXmlInit extends AbstractAhtDbXmlUtil
{
	static Log logger = LogFactory.getLog(AbstractAhtDbXmlInit.class);

	public AbstractAhtDbXmlInit(Configuration config, DataSource datasource)
	{
		super(config, datasource);
	}
	
	public AbstractAhtDbXmlInit(Db dbSeed, DataSource datasource)
	{
		super(dbSeed, datasource);
	}
	
	public void initFromXml(Priority priority) throws FileNotFoundException,AhtUtilsIntegrityException
	{
		switch(priority)
		{
			case statics: initStatics();break;
			case required: initRequired();break;
			case mandatory: initMandatory();break;
			case optional: initOptional();break;
		}
	}
	
	protected void initStatics() throws FileNotFoundException,AhtUtilsIntegrityException{}
	protected void initRequired() throws FileNotFoundException,AhtUtilsIntegrityException{}
	protected void initMandatory() throws FileNotFoundException,AhtUtilsIntegrityException{}
	protected void initOptional() throws FileNotFoundException,AhtUtilsIntegrityException{}
}