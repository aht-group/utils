package net.sf.ahtutils.interfaces.db;

import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;

public interface UtilsDbXmlExtract
{
	void extract() throws FileNotFoundException,UtilsConfigurationException; 
	void ideUpdate() throws UtilsConfigurationException;
}
