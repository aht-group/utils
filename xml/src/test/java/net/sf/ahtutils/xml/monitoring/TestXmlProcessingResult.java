package net.sf.ahtutils.xml.monitoring;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlProcessingResult extends AbstractXmlMonitoringTest<ProcessingResult>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlProcessingResult.class);
	
	public TestXmlProcessingResult(){super(ProcessingResult.class);}
	public static ProcessingResult create(boolean withChildren){return (new TestXmlProcessingResult()).build(withChildren);}
    
    public ProcessingResult build(boolean withChilds)
    {
    	ProcessingResult xml = new ProcessingResult();
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	if(withChilds){}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlProcessingResult test = new TestXmlProcessingResult();
		test.saveReferenceXml();
    }
}