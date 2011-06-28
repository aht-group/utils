package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;
import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.report.MediaPdf;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMediaPdf extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestMediaPdf.class);
	
	private static final String rootDir = "src/test/resources/data/xml/report/mediaPdf";
	
	private static File fMediaPdf;
	
	@BeforeClass
	public static void initFiles()
	{
		fMediaPdf = new File(rootDir,"mediaPdf.xml");
	}
    
    @Test
    public void testMediaPdf() throws FileNotFoundException
    {
    	MediaPdf mediaPdf = createMediaPdf();
    	MediaPdf ref = (MediaPdf)JaxbUtil.loadJAXB(fMediaPdf.getAbsolutePath(), MediaPdf.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(mediaPdf));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	MediaPdf mediaPdf = createMediaPdf();
    	JaxbUtil.debug2(this.getClass(),mediaPdf, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fMediaPdf, mediaPdf, new AhtUtilsNsPrefixMapper(), true);
    	
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
			
		TestMediaPdf.initFiles();	
		TestMediaPdf test = new TestMediaPdf();
		test.save();
    }
}