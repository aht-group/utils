package net.sf.ahtutils.xml.sync;

import org.jeesl.UtilsXmlTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMapper extends AbstractXmlSyncTest<Mapper>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMapper.class);
    
	public TestXmlMapper(){super(Mapper.class);}
	public static Mapper create(boolean withChildren){return (new TestXmlMapper()).build(withChildren);}
    
    public Mapper build(boolean withChilds)
    {
    	Mapper xml = new Mapper();
    	xml.setClazz(TestXmlMapper.class.getName());
    	xml.setOldId(123);
    	xml.setNewId(345);
    	xml.setOldCode("mc1");
    	xml.setNewCode("mc2");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		TestXmlMapper test = new TestXmlMapper();
		test.saveReferenceXml();
    }
}