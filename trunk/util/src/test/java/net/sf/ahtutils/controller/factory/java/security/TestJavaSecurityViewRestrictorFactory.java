package net.sf.ahtutils.controller.factory.java.security;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.test.AhtUtilsTstBootstrap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class TestJavaSecurityViewRestrictorFactory extends AbstractJavaAclFactoryTest
{
	final static Logger logger = LoggerFactory.getLogger(TestJavaSecurityViewRestrictorFactory.class);
	
	private JavaSecurityViewRestrictorFactory restrictorFactory;
	
	private File fRestrictorClass;
	private String classRestrictor,classAbstractRestrictor;
	
	@Before
	public void init()
	{	
		String viewQualifierBasePackage = "my.package";
		classRestrictor = "util.Restrictor";
		classAbstractRestrictor = "util.AbstractRestrictor";
		fRestrictorClass = new File(fTarget,classRestrictor+".java");
		restrictorFactory = new JavaSecurityViewRestrictorFactory(fRestrictorClass,classRestrictor,classAbstractRestrictor,viewQualifierBasePackage,"Utils");
	}
	
	@After
	public void clean() throws IOException
	{	
		if(fRestrictorClass.isFile() && fRestrictorClass.exists()){fRestrictorClass.delete();}
		restrictorFactory = null;
	}
	
	@Test
	public void createIdentifier() throws AhtUtilsConfigurationException, IOException, TemplateException
	{
		restrictorFactory.create(lC);

		Assert.assertTrue("File should exist: "+fRestrictorClass.getAbsolutePath(),fRestrictorClass.exists());
		Assert.assertTrue(fRestrictorClass.isFile());
			
		File expected = new File(rootDir,"SecurityRestrictor.java");
		assertText(expected, fRestrictorClass);
	}
	
	public static void main(String[] args) throws Exception
    {
		AhtUtilsTstBootstrap.init();
		
		TestJavaSecurityViewRestrictorFactory test = new TestJavaSecurityViewRestrictorFactory();
		test.setSaveReference(true);
		test.initViews();
		test.init();
		test.createIdentifier();
    }
}