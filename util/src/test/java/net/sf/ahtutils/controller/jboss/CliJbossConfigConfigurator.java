package net.sf.ahtutils.controller.jboss;


import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Element;
import org.jeesl.JeeslUtilTestBootstrap;
import org.jeesl.controller.config.jboss.JbossConfigConfigurator;
import org.jeesl.controller.config.jboss.JbossModuleConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliJbossConfigConfigurator
{
	final static Logger logger = LoggerFactory.getLogger(CliJbossConfigConfigurator.class);
			
	public static void main (String[] args) throws Exception
	{
		JeeslUtilTestBootstrap.init();
	
		String jbo = "/Volumes/ramdisk/jboss-eap-6.3";
		JbossConfigConfigurator jboss = new JbossConfigConfigurator(JbossModuleConfigurator.Product.eap,"6.3",jbo);
        jboss.addDs(getDummyElement("testDatasource"));
        jboss.addDbDriver(getDummyElement("testDriver"));
        jboss.changePublicInterface();
        jboss.changeTimeout(25);

        JDomUtil.debug(jboss.getDocument());
	}
	
    static Element getDummyElement(String test)
    {
        Element element = new Element(test);
        return element;
    }
}