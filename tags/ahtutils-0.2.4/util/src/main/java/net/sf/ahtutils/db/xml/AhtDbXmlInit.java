package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;

public interface AhtDbXmlInit
{
	public static enum Priority{statics,required,mandatory,optional}
	
	void initFromXml(Priority priority) throws FileNotFoundException,UtilsIntegrityException,UtilsConfigurationException;
}
