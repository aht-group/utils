package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Translation;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestTranslation extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestTranslation.class);
	
	private static final String rootDir = "src/test/resources/data/xml/status/translation";
	
	private static File fTranslation;
	
	@BeforeClass
	public static void initFiles()
	{
		fTranslation = new File(rootDir,"translation.xml");
	}
    
	 @Ignore
    @Test
    public void testTranslation() throws FileNotFoundException
    {
    	Translation test = createTranslation();
    	Status ref = (Status)JaxbUtil.loadJAXB(fTranslation.getAbsolutePath(), Status.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
    
    @Ignore
    @Test
    public void testTranslations() throws FileNotFoundException
    {
    	Translation test = createTranslation();
    	Status ref = (Status)JaxbUtil.loadJAXB(fTranslation.getAbsolutePath(), Status.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(test));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Translation translation = createTranslation();
    	JaxbUtil.debug2(this.getClass(),translation, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fTranslation, translation, new AhtUtilsNsPrefixMapper(), true);
    }
    
    public static Translation createTranslation()
    {
    	Translation xml = new Translation();
    	xml.setKey("myKey");
    	xml.setLangs(TestLang.createLangs());
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestTranslation.initFiles();	
		TestTranslation test = new TestTranslation();
		test.save();
    }
}