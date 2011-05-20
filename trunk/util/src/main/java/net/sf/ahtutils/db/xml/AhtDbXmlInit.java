package net.sf.ahtutils.db.xml;

import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilIntegrityException;

public interface AhtDbXmlInit
{
	public static enum Priority{statics,required,mandatory,optional}
	
	void initFromXml(Priority priority) throws FileNotFoundException,AhtUtilIntegrityException;
}
