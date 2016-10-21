package org.jeesl.model.xml.srs;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.srs.Srs;

public class TestXmlSrs extends AbstractXmlSrsTest<Srs>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSrs.class);
	
	public TestXmlSrs(){super(Srs.class);}
	public static Srs create(boolean withChildren){return (new TestXmlSrs()).build(withChildren);}
    
    public Srs build(boolean withChildren)
    {
    	Srs xml = new Srs();
    	xml.setId(123);
    
    	
    	if(withChildren)
    	{
    		xml.getFrGroup().add(TestXmlFrGroup.create(false));
    		xml.getFrGroup().add(TestXmlFrGroup.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlSrs test = new TestXmlSrs();
		test.saveReferenceXml();
    }
}