package org.jeesl.model.xml.navigation;

import org.jeesl.JeeslXmlTestBootstrap;
import org.jeesl.model.xml.system.navigation.UrlMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUrlMapping extends AbstractXmlNavigationTest<UrlMapping>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUrlMapping.class);
	
	public TestXmlUrlMapping(){super(UrlMapping.class);}
	public static UrlMapping create(boolean withChildren){return (new TestXmlUrlMapping()).build(withChildren);}
    
    public UrlMapping build(boolean withChilds)
    {
    	UrlMapping xml = new UrlMapping();
    	xml.setValue("myUrlMapping");
    	xml.setUrl("myUrl");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlUrlMapping test = new TestXmlUrlMapping();
		test.saveReferenceXml();
    }
}