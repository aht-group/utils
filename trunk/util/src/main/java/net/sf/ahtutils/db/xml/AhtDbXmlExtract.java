package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;

public interface AhtDbXmlExtract
{
	void extract() throws FileNotFoundException,UtilsConfigurationException; 
	void ideUpdate() throws UtilsConfigurationException;
}
