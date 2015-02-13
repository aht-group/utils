package net.sf.ahtutils.xml.sync;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMappings extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMappings.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Mappings.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Mappings actual = create(true);
    	Mappings expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Mappings.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Mappings create(boolean withChilds)
    {
    	Mappings xml = new Mappings();
    	
    	if(withChilds)
    	{
    		xml.getMapper().add(TestXmlMapper.create(false));
    		xml.getMapper().add(TestXmlMapper.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlMappings.initJaxb();
		TestXmlMappings.initFiles();	
		TestXmlMappings test = new TestXmlMappings();
		test.save();
    }
}