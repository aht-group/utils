package org.jeesl.model.xml.security;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Action;
import net.sf.ahtutils.xml.status.TestXmlDescriptions;
import net.sf.ahtutils.xml.status.TestXmlLangs;

public class TestXmlAction extends AbstractXmlSecurityTest<Action>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAction.class);
	
	public TestXmlAction(){super(Action.class);}
	public static Action create(boolean withChildren){return (new TestXmlAction()).build(withChildren);}
    
    public Action build(boolean withChilds)
    {
    	Action xml = new Action();
    	xml.setId(123);
    	xml.setPosition(1);
    	xml.setVisible(true);
    	xml.setDocumentation(true);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		xml.setView(TestXmlView.create(false));
    		xml.setTemplate(TestXmlTemplate.create(false));
    	}
    	return xml;
    }

	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlAction test = new TestXmlAction();
		test.saveReferenceXml();
    }
}