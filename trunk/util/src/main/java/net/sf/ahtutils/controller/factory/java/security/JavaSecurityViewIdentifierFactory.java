package net.sf.ahtutils.controller.factory.java.security;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.dir.DirChecker;
import net.sf.exlp.util.io.dir.RecursiveFileFinder;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class JavaSecurityViewIdentifierFactory extends AbstractJavaSecurityFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(JavaSecurityViewIdentifierFactory.class);
		
	private File fPackage;
	private String viewQualifierBasePackage;
	
	
	public JavaSecurityViewIdentifierFactory(File fTmpDir, File fPackage, String viewQualifierBasePackage, String classPrefix)
	{
		super(fTmpDir,classPrefix);
		this.fPackage=fPackage;
		this.viewQualifierBasePackage=viewQualifierBasePackage;
	}

	@Override
	protected void processCategories(List<Category> lCategory) throws AhtUtilsConfigurationException
	{
		try{DirChecker.checkFileIsDirectory(fPackage);}
		catch (ExlpConfigurationException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
		for(Category category : lCategory)
		{
			try {create(category);}
			catch (IOException e) {e.printStackTrace();}
			catch (TemplateException e) {e.printStackTrace();}
		}
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
		
		RecursiveFileFinder finder = new RecursiveFileFinder(FileFilterUtils.suffixFileFilter(".java"));
    	List<File> list = finder.find(fCategory);
		Set<String> sPrevious = new HashSet<String>();
		for(File f : list){sPrevious.add(f.getAbsolutePath());}
		
		if(category.isSetViews())
		{
			for(View view : category.getViews().getView())
			{
				File fProcessed = createIdentifier(fCategory,view,category.getCode());
				if(sPrevious.contains(fProcessed.getAbsolutePath())){sPrevious.remove(fProcessed.getAbsolutePath());}
			}
		}
		if(sPrevious.size()>0)
		{
			logger.debug("Deleting unused Identifier");
			Iterator<String> iterator = sPrevious.iterator();
			while(iterator.hasNext())
			{
				String key = iterator.next();
				File f = new File(key);
				f.delete();
				logger.warn("\t"+key);
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private File createIdentifier(File fSub, View view,String subPackage) throws IOException, TemplateException
	{	
		freemarkerNodeModel.clear();
		freemarkerNodeModel.put("packageName", viewQualifierBasePackage+"."+subPackage);
		freemarkerNodeModel.put("className", createClassName(view.getCode()));
		
		File fJava = new File(fSub,createFileName(view.getCode()));
		fJava.createNewFile();
		
		this.createFile(fJava, "security.ahtutils-util/identifier.ftl");
		return fJava;
	}
}