package org.jeesl.interfaces.controller.db;

import java.io.FileNotFoundException;

import org.jeesl.exception.processing.UtilsConfigurationException;

public interface UtilsDbXmlExtract
{
	void extract() throws FileNotFoundException,UtilsConfigurationException; 
	void ideUpdate() throws UtilsConfigurationException;
}
