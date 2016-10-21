package org.jeesl.model.xml.report;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.Media;

public class TestXmlMedia extends AbstractXmlReportTest<Media>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMedia.class);
	
	public TestXmlMedia(){super(Media.class);}
	public static Media create(boolean withChildren){return (new TestXmlMedia()).build(withChildren);}    
    
    public Media build(boolean withChildren)
    {
    	Media media = new Media();
    	media.setDir("testDir");
    	media.getJr().add(TestXmlJr.create(false));
    	media.setType("myType");
    	return media;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlMedia test = new TestXmlMedia();
		test.saveReferenceXml();
    }
}