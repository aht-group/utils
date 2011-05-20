package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilIntegrityException;
import net.sf.ahtutils.db.xml.AhtDbXmlInit.Priority;

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
	
	public void initFromXml(Priority priority) throws FileNotFoundException,AhtUtilIntegrityException
	{
		switch(priority)
		{
			case required: initRequired();break;
			case mandatory: initMandatory();break;
			case optional: initOptional();break;
		}
	}
	
	protected void initRequired() throws FileNotFoundException,AhtUtilIntegrityException{}
	protected void initMandatory() throws FileNotFoundException,AhtUtilIntegrityException{}
	protected void initOptional() throws FileNotFoundException,AhtUtilIntegrityException{}
}