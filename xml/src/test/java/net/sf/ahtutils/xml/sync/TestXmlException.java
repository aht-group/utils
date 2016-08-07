package net.sf.ahtutils.xml.sync;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;

public class TestXmlException extends AbstractXmlSyncTest<Exception>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlException.class);
	
	public TestXmlException(){super(Exception.class);}
	public static Exception create(boolean withChildren){return (new TestXmlException()).build(withChildren);}
    
    public Exception build(boolean withChilds)
    {
    	Exception xml = new Exception();
    	xml.setRecord(AbstractAhtUtilsXmlTest.getDefaultXmlDate());
    	xml.setType("myType");
    	xml.setClassName("myClassName");
    	xml.setLine(123);
    	xml.setMessage("myDescription");

    	if(withChilds)
    	{
    		xml.setException(TestXmlException.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlException test = new TestXmlException();
		test.saveReferenceXml();
    }
}