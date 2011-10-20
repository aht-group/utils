package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;

public interface AhtDbXmlExtract
{
	void extract() throws FileNotFoundException,AhtUtilsConfigurationException; 
	void ideUpdate() throws AhtUtilsConfigurationException;
}
