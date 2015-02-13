package net.sf.ahtutils.xml.sync;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMapper extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMapper.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Mapper.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Mapper actual = create(true);
    	Mapper expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Mapper.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Mapper create(boolean withChilds)
    {
    	Mapper xml = new Mapper();
    	xml.setClazz(TestXmlMapper.class.getName());
    	xml.setOldId(123);
    	xml.setNewId(345);
    	xml.setOldCode("mc1");
    	xml.setNewCode("mc2");
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlMapper.initJaxb();
		TestXmlMapper.initFiles();	
		TestXmlMapper test = new TestXmlMapper();
		test.save();
    }
}