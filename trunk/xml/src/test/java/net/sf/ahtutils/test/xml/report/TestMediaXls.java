package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.MediaXls;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMediaXls extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestMediaXls.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"mediaXls.xml");
	}
    
    @Test
    public void testMediaXls() throws FileNotFoundException
    {
    	MediaXls test = createMediaXls();
    	MediaXls ref = (MediaXls)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), MediaXls.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	MediaXls mediaXls = createMediaXls();
    	JaxbUtil.debug2(this.getClass(),mediaXls, nsPrefixMapper);
    	JaxbUtil.save(fXml, mediaXls, nsPrefixMapper, true);
    }
    
    public static MediaXls createMediaXls()
    {
    	MediaXls mediaXls = new MediaXls();
    	mediaXls.setDir("testDir");
    	mediaXls.getJr().add(TestJr.createJr());
    	return mediaXls;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestMediaXls.initPrefixMapper();
		TestMediaXls.initFiles();	
		TestMediaXls test = new TestMediaXls();
		test.save();
    }
}