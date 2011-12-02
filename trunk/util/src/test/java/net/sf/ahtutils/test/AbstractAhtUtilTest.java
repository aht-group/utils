package net.sf.ahtutils.test;

import java.io.File;

import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractAhtUtilTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	protected File f;
	private boolean saveReference=false;

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
	
	public void setSaveReference(boolean saveReference) {this.saveReference = saveReference;}
}