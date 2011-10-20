package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;

public interface AhtDbXmlInit
{
	public static enum Priority{statics,required,mandatory,optional}
	
	void initFromXml(Priority priority) throws FileNotFoundException,AhtUtilsIntegrityException,AhtUtilsConfigurationException;
}
