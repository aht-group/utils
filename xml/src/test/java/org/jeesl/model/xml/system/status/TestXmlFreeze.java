package org.jeesl.model.xml.system.status;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Freeze;

public class TestXmlFreeze extends AbstractXmlStatusTest<Freeze>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFreeze.class);
	
	public TestXmlFreeze(){super(Freeze.class);}
	public static Freeze create(boolean withChildren){return (new TestXmlFreeze()).build(withChildren);} 
    
    public Freeze build(boolean withChilds)
    {
    	Freeze xml = new Freeze();
    	xml.setCode("myCode");
    	xml.setVisible(true);
    	xml.setGroup("myGroup");
    	xml.setLabel("myLabel");
    	xml.setImage("test/green.png");
    	xml.setPosition(1);
    	
    	if(withChilds)
    	{
    		xml.setLangs(TestXmlLangs.create(false));
    		xml.setDescriptions(TestXmlDescriptions.create(false));
    		
    		xml.getTracked().add(TestXmlTracked.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlFreeze test = new TestXmlFreeze();
		test.saveReferenceXml();
    }
}