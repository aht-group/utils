package net.sf.ahtutils.factory.word;

import java.io.File;

import net.sf.ahtutils.xml.project.Responsibilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordResponsibilityMatrixFactory
{
	final static Logger logger = LoggerFactory.getLogger(WordResponsibilityMatrixFactory.class);
	
	public static enum Status {primary,secondary}
	
	public WordResponsibilityMatrixFactory()
	{

	}
	
	public void buildWord(File fDst, Responsibilities responsibilites)
	{
		logger.info("Building for "+responsibilites.getUser().size()+" users file: "+fDst.getAbsolutePath());
	}
}