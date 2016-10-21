package org.jeesl.model.xml.security;

import org.jeesl.JeeslXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.security.Staffs;
import net.sf.ahtutils.xml.status.TestXmlDomain;

public class TestXmlStaffs extends AbstractXmlSecurityTest<Staffs>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStaffs.class);
	
	public TestXmlStaffs(){super(Staffs.class);}
	public static Staffs create(boolean withChildren){return (new TestXmlStaffs()).build(withChildren);}
    
    public Staffs build(boolean withChilds)
    {
    	Staffs xml = new Staffs();

    	if(withChilds)
    	{
    		xml.setDomain(TestXmlDomain.create(false));
    		xml.getStaff().add(TestXmlStaff.create(false));xml.getStaff().add(TestXmlStaff.create(false));
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		JeeslXmlTestBootstrap.init();
		TestXmlStaffs test = new TestXmlStaffs();
		test.saveReferenceXml();
    }
}