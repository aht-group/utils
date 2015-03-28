package net.sf.ahtutils.interfaces.db;

import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;

public interface UtilsDbXmlInit
{
	public static enum Priority{statics,required,mandatory,optional,A,B,C,D,E}
	
	void initFromXml(Priority priority) throws FileNotFoundException,UtilsContraintViolationException,UtilsConfigurationException;
}
