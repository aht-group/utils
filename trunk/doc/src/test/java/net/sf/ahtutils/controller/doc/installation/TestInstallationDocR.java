package net.sf.ahtutils.controller.doc.installation;


import net.sf.ahtutils.test.AbstractUtilsDocTest;
import net.sf.ahtutils.test.AhtUtilsDocBootstrap;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestInstallationDocR extends AbstractUtilsDocTest
{
	final static Logger logger = LoggerFactory.getLogger(TestInstallationDocR.class);
	
	@Before
	public void init()
	{	
	
	}
	
	@Test
	public void exists()
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		Assert.assertTrue(mrl.isAvailable(BuildDocR.resourceName));
	}
	
	public static void main(String args[]) throws Exception
	{
		AhtUtilsDocBootstrap.init();
		
		BuildDocR build = new BuildDocR();
		build.renderLatex(null);
	}
}