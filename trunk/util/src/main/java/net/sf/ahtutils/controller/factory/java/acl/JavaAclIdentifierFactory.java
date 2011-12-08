package net.sf.ahtutils.controller.factory.java.acl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JavaAclIdentifierFactory
{
	final static Logger logger = LoggerFactory.getLogger(JavaAclIdentifierFactory.class);
		
	private File fPackage;
	private String basePackage,classPrefix;
	private Configuration freemarkerConfiguration;
	
	public JavaAclIdentifierFactory(File fPackage, String basePackage, String classPrefix)
	{
		this.fPackage=fPackage;
		this.basePackage=basePackage;
		this.classPrefix=classPrefix;
		freemarkerConfiguration = new Configuration();
		freemarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
	}
	
	public void create(String fXml) throws FileNotFoundException, AhtUtilsConfigurationException
	{
		Access access = JaxbUtil.loadJAXB(fXml, Access.class);
		create(access.getCategory());
	}

	public void create(List<Category> lCategory) throws AhtUtilsConfigurationException
	{
		checkBaseDir();
		for(Category category : lCategory)
		{
			try {
				create(category);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void checkBaseDir() throws AhtUtilsConfigurationException
	{
		if(!fPackage.exists()){throw new AhtUtilsConfigurationException("Directory "+fPackage.getAbsolutePath()+" does not exist");}
		if(!fPackage.isDirectory()){throw new AhtUtilsConfigurationException(fPackage.getAbsolutePath()+" is not a directory");}
	}
	
	protected void create(Category category) throws AhtUtilsConfigurationException, IOException, TemplateException
	{
		File fCategory = new File(fPackage,category.getCode());
		if(fCategory.exists())
		{
			if(fCategory.isDirectory()){logger.debug("Skipping. Directory exists "+fCategory.getAbsolutePath());}
			else{throw new AhtUtilsConfigurationException(fCategory.getAbsolutePath()+" is not a directory");}
		}
		else
		{
			fCategory.mkdir();
			logger.debug("Create directory "+fCategory.getAbsolutePath());
		}
		cleanCategoryDir(fCategory);
		
		if(category.isSetViews())
		{
			for(View view : category.getViews().getView())
			{
				createIdentifier(fCategory,view,category.getCode());
			}
		}
	}
	
	private void createIdentifier(File fSub, View view,String subPackage) throws IOException, TemplateException
	{
		File fJava = new File(fSub,createFileName(view.getCode()));
		fJava.createNewFile();
		
		Map<String,String> root = new HashMap<String,String>();
        root.put("packageName", basePackage+"."+subPackage);
        root.put("className", createClassName(view.getCode()));
		
		Template ftl = freemarkerConfiguration.getTemplate("acl.ahtutils-util/securityIdentifier.ftl","UTF-8");
		ftl.setEncoding("UTF-8");
		
		StringWriter sw = new StringWriter();
		ftl.process(root, sw);
		sw.flush();
		
		StringIO.writeTxt(fJava, sw.toString());
	}
	
	protected String createFileName(String code)
	{
		return createClassName(code)+".java";
	}
	
	protected String createClassName(String code)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(classPrefix);
		sb.append(code.subSequence(0, 1).toString().toUpperCase());
		sb.append(code.substring(1, code.length()));
		return sb.toString();
	}
	
	protected void cleanCategoryDir(File fCategory) throws IOException
	{		
		JavaFileCleaner cleaner = new JavaFileCleaner(FileFilterUtils.suffixFileFilter(".java"));
		cleaner.clean(fCategory);
	}
	
	private class JavaFileCleaner extends DirectoryWalker<File>
	{
		public JavaFileCleaner(FileFilter filter)
	    {
			super(filter, -1);
	    }

		public List<File> clean(File startDirectory) throws IOException
		{
			List<File> results = new ArrayList<File>();
			walk(startDirectory, results);
			return results;
	    }

	    protected void handleFile(File file, int depth, Collection<File> results)
	    {
	    	file.delete();
	    	results.add(file);
	    }
	}
}