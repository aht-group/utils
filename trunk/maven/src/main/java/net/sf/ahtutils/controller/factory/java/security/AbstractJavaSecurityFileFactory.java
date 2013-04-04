package net.sf.ahtutils.controller.factory.java.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import net.sf.ahtutils.controller.factory.AbstractFreemarkerFileFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJavaSecurityFileFactory extends AbstractFreemarkerFileFactory 
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJavaSecurityFileFactory.class);
		
	protected String classPrefix;
	
	public AbstractJavaSecurityFileFactory(File fTmpDir, String classPrefix)
	{
		super(fTmpDir);
		this.classPrefix=classPrefix;
	}
	
	protected String createFileName(String code)
	{
		return createClassName(code)+".java";
	}
	
	public void processViews(String fXml) throws FileNotFoundException, UtilsConfigurationException
	{
		Access access = JaxbUtil.loadJAXB(fXml, Access.class);
		processCategories(access.getCategory());
	}
	
	protected void processCategories(List<Category> lCategory) throws UtilsConfigurationException {}
	
	protected String createClassName(String code)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(classPrefix);
		sb.append(code.subSequence(0, 1).toString().toUpperCase());
		sb.append(code.substring(1, code.length()));
		return sb.toString();
	}
}