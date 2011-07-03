package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.Media;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMedia extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestMedia.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"media.xml");
	}
    
    @Test
    public void testMedia() throws FileNotFoundException
    {
    	Media test = createMedia();
    	Media ref = (Media)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Media.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Media media = createMedia();
    	JaxbUtil.debug2(this.getClass(),media, nsPrefixMapper);
    	JaxbUtil.save(fXml, media, nsPrefixMapper, true);
    	
    }
    
    public static Media createMedia()
    {
    	Media media = new Media();
    	media.setDir("testDir");
    	media.getJr().add(TestJr.createJr());
    	media.setType("pdf");
    	return media;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestMedia.initPrefixMapper();
		TestMedia.initFiles();	
		TestMedia test = new TestMedia();
		test.save();
    }
}