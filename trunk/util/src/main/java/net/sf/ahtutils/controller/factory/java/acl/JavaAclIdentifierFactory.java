package net.sf.ahtutils.controller.factory.java.acl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaAclIdentifierFactory
{
	final static Logger logger = LoggerFactory.getLogger(JavaAclIdentifierFactory.class);
		
	private File fPackage;
	
	public JavaAclIdentifierFactory(File fPackage, String basePackage)
	{
		this.fPackage=fPackage;
	}

	public void create(List<Category> lCategory) throws AhtUtilsConfigurationException, IOException
	{
		checkBaseDir();
		for(Category category : lCategory){create(category);}
	}
	
	protected void checkBaseDir() throws AhtUtilsConfigurationException
	{
		if(!fPackage.exists()){throw new AhtUtilsConfigurationException("Directory "+fPackage.getAbsolutePath()+" does not exist");}
		if(!fPackage.isDirectory()){throw new AhtUtilsConfigurationException(fPackage.getAbsolutePath()+" is not a directory");}
	}
	
	protected void create(Category category) throws AhtUtilsConfigurationException, IOException
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
		if(category.isSetViews())
		{
			for(View view : category.getViews().getView())
			{
				createIdentifier(fCategory,view);
			}
		}
	}
	
	private void createIdentifier(File fSub, View view) throws IOException
	{
		File fJava = new File(fSub,createFileName(view.getCode()));
		fJava.createNewFile();
	}
	
	protected String createFileName(String code)
	{
		return code;
	}
}