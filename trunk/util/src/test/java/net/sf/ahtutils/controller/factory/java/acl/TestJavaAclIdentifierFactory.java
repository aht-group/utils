package net.sf.ahtutils.controller.factory.java.acl;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.factory.java.AbstractJavaFactoryTest;
import net.sf.ahtutils.test.AhtUtilsTstBootstrap;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJavaAclIdentifierFactory extends AbstractJavaFactoryTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJavaAclIdentifierFactory.class);
	
	private JavaAclIdentifierFactory idFactory;
	
	private Category c1;
	private View v1;
	private File fPackage;
	
	@Before
	public void init()
	{	
		fPackage = new File(fTarget,"idFactory");
		fPackage.mkdir();
		idFactory = new JavaAclIdentifierFactory(fPackage,"net.sf");
		
		v1 = new View();
		v1.setCode("myCode");
		
		c1 = new Category();c1.setCode("xx");
		c1.setViews(new Views());
		c1.getViews().getView().add(v1);
	}
	
	@After
	public void clean() throws IOException
	{	
		if(fPackage.isDirectory()){FileUtils.deleteDirectory(fPackage);}
		else if(fPackage.isFile()){fPackage.delete();}
		idFactory = null;
	}
	
	@Test(expected=AhtUtilsConfigurationException.class)
	public void noDir() throws AhtUtilsConfigurationException
	{
		fPackage.delete();
		idFactory.checkBaseDir();
	}
	
	@Test(expected=AhtUtilsConfigurationException.class)
	public void isFile() throws AhtUtilsConfigurationException, IOException
	{
		FileUtils.deleteDirectory(fPackage);
		fPackage.createNewFile();
		idFactory.checkBaseDir();
	}
	
	@Test(expected=AhtUtilsConfigurationException.class)
	public void categoryDirIsFile() throws AhtUtilsConfigurationException, IOException
	{
		File actual = new File(fPackage,c1.getCode());
		actual.createNewFile();
		idFactory.create(c1);
	}
	
	@Test
	public void categoryDir() throws AhtUtilsConfigurationException, IOException
	{
		idFactory.create(c1);
		File actual = new File(fPackage,c1.getCode());
		Assert.assertTrue(actual.exists());
		Assert.assertTrue(actual.isDirectory());
	}
	
	@Test
	public void createIdentifier() throws AhtUtilsConfigurationException, IOException
	{
		idFactory.create(c1);
		File fSub = new File(fPackage,c1.getCode());
		for(View v : c1.getViews().getView())
		{
			File actual = new File(fSub,idFactory.createFileName(v.getCode()));
			Assert.assertTrue("File should exist: "+actual.getAbsolutePath(),actual.exists());
			Assert.assertTrue(actual.isFile());
		}
	}
	
	public static void main(String[] args) throws Exception
    {
		AhtUtilsTstBootstrap.init();
		
		TestJavaAclIdentifierFactory test = new TestJavaAclIdentifierFactory();
		test.setSaveReference(true);
		test.init();


    }
}