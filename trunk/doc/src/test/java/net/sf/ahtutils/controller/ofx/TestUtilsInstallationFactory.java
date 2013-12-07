package net.sf.ahtutils.controller.ofx;


import net.sf.ahtutils.controller.doc.UtilsInstallationLatexFactory;
import net.sf.ahtutils.controller.doc.UtilsInstallationLatexFactory.Type;
import net.sf.ahtutils.test.AbstractUtilsDocTest;
import net.sf.ahtutils.test.AhtUtilsDocBootstrap;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtilsInstallationFactory extends AbstractUtilsDocTest
{
	final static Logger logger = LoggerFactory.getLogger(TestUtilsInstallationFactory.class);
	
	private MultiResourceLoader mrl;
	
	@Before
	public void init()
	{	
		mrl = new MultiResourceLoader();
	}
	
	@Test
	public void installationR()
	{
		Assert.assertTrue(mrl.isAvailable(UtilsInstallationLatexFactory.resourceR));
	}
	
	public static void main(String args[]) throws Exception
	{
		AhtUtilsDocBootstrap.init();
		
		UtilsInstallationLatexFactory test = new UtilsInstallationLatexFactory("de");
		test.renderLatex(Type.r);
	}
}