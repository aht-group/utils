package net.sf.ahtutils.xml.security;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlViews extends AbstractXmlSecurityTest<Views>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlViews.class);
	
	public TestXmlViews(){super(Views.class);}
	public static Views create(boolean withChildren){return (new TestXmlViews()).build(withChildren);}
    
    public Views build(boolean withChilds)
    {
    	Views xml = new Views();

    	if(withChilds)
    	{
    		xml.getView().add(TestXmlView.create(false));xml.getView().add(TestXmlView.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlViews test = new TestXmlViews();
		test.saveReferenceXml();
    }
}