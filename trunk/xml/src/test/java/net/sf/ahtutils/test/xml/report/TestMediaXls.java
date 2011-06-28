package net.sf.ahtutils.test.xml.report;

import java.io.File;
import java.io.FileNotFoundException;
import net.sf.ahtutils.test.AbstractTgXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.report.MediaXls;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMediaXls extends AbstractTgXmlTest
{
	static Log logger = LogFactory.getLog(TestMediaXls.class);
	
	private static final String rootDir = "src/test/resources/data/xml/report/mediaXls";
	
	private static File fMediaXls;
	
	@BeforeClass
	public static void initFiles()
	{
		fMediaXls = new File(rootDir,"mediaXls.xml");
	}
    
    @Test
    public void testMediaXls() throws FileNotFoundException
    {
    	MediaXls mediaXls = createMediaXls();
    	MediaXls ref = (MediaXls)JaxbUtil.loadJAXB(fMediaXls.getAbsolutePath(), MediaXls.class);
    	Assert.assertEquals(JaxbUtil.toString(ref),JaxbUtil.toString(mediaXls));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	MediaXls mediaXls = createMediaXls();
    	JaxbUtil.debug2(this.getClass(),mediaXls, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fMediaXls, mediaXls, new AhtUtilsNsPrefixMapper(), true);
    	
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
			
		TestMediaXls.initFiles();	
		TestMediaXls test = new TestMediaXls();
		test.save();
    }
}