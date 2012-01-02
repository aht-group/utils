package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.db.xml.AhtDbXmlInit.Priority;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.xml.dbseed.Db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAhtDbXmlInit extends AhtDbXmlSeedUtil
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtDbXmlInit.class);

	protected AhtXmlInitIdMapper idMapper;
	protected AhtStatusDbInit asdi;
	
	public AbstractAhtDbXmlInit(Db dbSeed, DataSource datasource, AhtXmlInitIdMapper idMapper, AhtStatusDbInit asdi)
	{
		super(dbSeed, datasource);
		this.idMapper=idMapper;
		this.asdi=asdi;
	}
	
	public void initFromXml(Priority priority) throws FileNotFoundException,UtilsIntegrityException, AhtUtilsConfigurationException
	{
		switch(priority)
		{
			case statics: initStatics();break;
			case required: initRequired();break;
			case mandatory: initMandatory();break;
			case optional: initOptional();break;
		}
	}
	
	protected void initStatics() throws FileNotFoundException,UtilsIntegrityException,AhtUtilsConfigurationException{}
	protected void initRequired() throws FileNotFoundException,UtilsIntegrityException,AhtUtilsConfigurationException{}
	protected void initMandatory() throws FileNotFoundException,UtilsIntegrityException,AhtUtilsConfigurationException{}
	protected void initOptional() throws FileNotFoundException,UtilsIntegrityException,AhtUtilsConfigurationException{}
}