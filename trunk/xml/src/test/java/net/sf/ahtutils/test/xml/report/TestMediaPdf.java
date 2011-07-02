package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.report.MediaPdf;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMediaPdf extends AbstractXmlReportTest
{
	static Log logger = LogFactory.getLog(TestMediaPdf.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"mediaPdf.xml");
	}
    
    @Test
    public void testMediaPdf() throws FileNotFoundException
    {
    	MediaPdf test = createMediaPdf();
    	MediaPdf ref = (MediaPdf)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), MediaPdf.class);
    	assertJaxbEquals(ref, test);
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	MediaPdf mediaPdf = createMediaPdf();
    	JaxbUtil.debug2(this.getClass(),mediaPdf, nsPrefixMapper);
    	JaxbUtil.save(fXml, mediaPdf, nsPrefixMapper, true);
    	
    }
    
    public static MediaPdf createMediaPdf()
    {
    	MediaPdf mediaPdf = new MediaPdf();
    	mediaPdf.setDir("testDir");
    	mediaPdf.getJr().add(TestJr.createJr());
    	return mediaPdf;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestMediaPdf.initPrefixMapper();
		TestMediaPdf.initFiles();	
		TestMediaPdf test = new TestMediaPdf();
		test.save();
    }
}