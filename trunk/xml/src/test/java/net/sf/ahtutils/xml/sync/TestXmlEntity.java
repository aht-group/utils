package net.sf.ahtutils.xml.sync;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEntity extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlEntity.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Entity.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Entity actual = create(true);
    	Entity expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Entity.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Entity create(boolean withChilds)
    {
    	Entity xml = new Entity();
    	xml.setVersion(0);
    	xml.setValue("123");
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlEntity.initJaxb();
		TestXmlEntity.initFiles();	
		TestXmlEntity test = new TestXmlEntity();
		test.save();
    }
}