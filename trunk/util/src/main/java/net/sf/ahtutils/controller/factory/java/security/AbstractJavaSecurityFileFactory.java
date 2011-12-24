package net.sf.ahtutils.controller.factory.java.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("rawtypes")
public class AbstractJavaSecurityFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJavaSecurityFileFactory.class);
		
	protected String classPrefix;
	
	protected Map freemarkerNodeModel;
	protected Configuration freemarkerConfiguration;
	
	public AbstractJavaSecurityFileFactory(String classPrefix)
	{
		this.classPrefix=classPrefix;
		
		freemarkerNodeModel = new HashMap();
		freemarkerConfiguration = new Configuration();
		freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
	}
	
	protected String createFileName(String code)
	{
		return createClassName(code)+".java";
	}
	
	public void processViews(String fXml) throws FileNotFoundException, AhtUtilsConfigurationException
	{
		Access access = JaxbUtil.loadJAXB(fXml, Access.class);
		processCategories(access.getCategory());
	}
	
	protected void processCategories(List<Category> lCategory) throws AhtUtilsConfigurationException {}
	
	protected String createClassName(String code)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(classPrefix);
		sb.append(code.subSequence(0, 1).toString().toUpperCase());
		sb.append(code.substring(1, code.length()));
		return sb.toString();
	}
	
	protected void createFile(File f, String template) throws IOException, TemplateException
	{
		if(f.exists()){f.delete();}
		
		Template ftl = freemarkerConfiguration.getTemplate(template,"UTF-8");
		ftl.setEncoding("UTF-8");
		
		StringWriter sw = new StringWriter();
		ftl.process(freemarkerNodeModel, sw);
		sw.flush();
		
		StringIO.writeTxt(f, sw.toString());
	}
}