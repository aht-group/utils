package net.sf.ahtutils.xml.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.xml.addon.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMedia extends AbstractXmlReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestMedia.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"media.xml");
	}
    
    @Test
    public void testMedia() throws FileNotFoundException
    {
    	Media test = create();
    	Media ref = (Media)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Media.class);
    	assertJaxbEquals(ref, test);
    }
    
    public static Media create(){return create("pdf");}
    public static Media create(String type)
    {
    	Media media = new Media();
    	media.setDir("testDir");
    	media.getJr().add(TestJr.create());
    	media.setType(type);
    	media.setChart(new Chart());
    	return media;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestMedia.initJaxb();
		TestMedia.initFiles();	
		TestMedia test = new TestMedia();
		test.save();
    }
}