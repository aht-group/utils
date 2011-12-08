package net.sf.ahtutils.test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.controller.factory.java.AbstractJavaFactoryTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	protected File f;
	private boolean saveReference=false;

	protected static File fTarget;
	
	protected static void setfTarget(File fTarget) {AbstractJavaFactoryTest.fTarget = fTarget;}

	@BeforeClass
	public static void initFile()
	{
		setfTarget(new File(System.getProperty("targetDir")));
		logger.debug(fTarget.getAbsolutePath());
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("actual XML differes from expected XML",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected NsPrefixMapperInterface getPrefixMapper()
	{
		if(nsPrefixMapper==null){nsPrefixMapper = new AhtUtilsNsPrefixMapper();}
		return nsPrefixMapper;
	}
	
	protected void saveXml(Object xml, File f, boolean formatted)
	{
		if(saveReference)
		{
			logger.debug("Saving Reference XML");
			JaxbUtil.debug2(this.getClass(),xml, getPrefixMapper());
			JaxbUtil.save(f, xml, getPrefixMapper(), formatted);
		}
	}
	
	protected void debug(OfxLatexRenderer renderer)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("Debugging "+renderer.getClass().getSimpleName());
			System.out.println("************************************");
			for(String s : renderer.getContent())
			{
				System.out.println(s);
			}
			System.out.println("************************************");
		}
	}
	
	protected void save(OfxLatexRenderer renderer, File f) throws IOException
	{
		if(saveReference)
		{
			RelativePathFactory rpf = new RelativePathFactory(new File("src/test/resources"),RelativePathFactory.PathSeparator.CURRENT);
			logger.debug("Saving Reference to "+rpf.relativate(f));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
		}
	}
	
	protected void assertText(OfxLatexRenderer renderer, File f) throws IOException
	{
		StringWriter actual = new StringWriter();
		renderer.write(actual);
		
		String expected = StringIO.loadTxt(f);
		Assert.assertEquals(expected, actual.toString());
	}
	
	public void setSaveReference(boolean saveReference) {this.saveReference = saveReference;}
}