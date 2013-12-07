package net.sf.ahtutils.controller.ofx;


import java.io.File;

import net.sf.ahtutils.controller.ofx.UtilsInstallationLatexFactory.Type;
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
    private UtilsInstallationLatexFactory uilf;
	
	@Before
	public void init()
	{	
		mrl = new MultiResourceLoader();
        uilf = new UtilsInstallationLatexFactory("");
	}

    @Test
    public void installationJava()
    {
        Assert.assertTrue(mrl.isAvailable(uilf.getResource(Type.JAVA)));
    }

    @Test
    public void installationJBoss()
    {
        Assert.assertTrue(mrl.isAvailable(uilf.getResource(Type.JBOSS)));
    }

    @Test
    public void installationMaven()
    {
        Assert.assertTrue(mrl.isAvailable(uilf.getResource(Type.MAVEN)));
    }

    @Test
    public void installationMysql()
    {
        Assert.assertTrue(mrl.isAvailable(uilf.getResource(Type.MYSQL)));
    }

    @Test
    public void installationOwnCloud()
    {
        Assert.assertTrue(mrl.isAvailable(uilf.getResource(Type.OWNCLOUD)));
    }

    @Test
    public void installationPostgres()
    {
        Assert.assertTrue(mrl.isAvailable(uilf.getResource(Type.POSTGRES)));
    }

	@Test
	public void installationR()
	{
        Assert.assertTrue(mrl.isAvailable(uilf.getResource(Type.R)));
	}

    @Test
    public void installationResource()
    {
        for(Type t : Type.values())
        {
            Assert.assertTrue(mrl.isAvailable(uilf.getResource(t)));
        }
    }
	
	public static void main(String args[]) throws Exception
	{
		AhtUtilsDocBootstrap.init();

        File f = new File("target","latex.tex");

		UtilsInstallationLatexFactory test = new UtilsInstallationLatexFactory("de");
		test.renderLatex(Type.R,f);
	}
}