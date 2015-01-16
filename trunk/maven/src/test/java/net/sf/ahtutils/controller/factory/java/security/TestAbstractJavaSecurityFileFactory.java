package net.sf.ahtutils.controller.factory.java.security;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.test.UtilsMavenTstBootstrap;
import net.sf.ahtutils.xml.access.View;
import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.dir.DirChecker;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

public class TestAbstractJavaSecurityFileFactory extends AbstractJavaSecurityFactoryTst
{
	final static Logger logger = LoggerFactory.getLogger(TestAbstractJavaSecurityFileFactory.class);
	
	private JavaSecurityViewIdentifierFactory idFactory;
	
	private File fPackage;
	private String classPrefix;
	

	
	@Test
	public void buildPackage() throws ExlpConfigurationException
	{
		String actual = AbstractJavaSecurityFileFactory.buildPackage("adminSecurity");
		String expected = "admin.security";
		Assert.assertEquals(expected,actual);
	}
}