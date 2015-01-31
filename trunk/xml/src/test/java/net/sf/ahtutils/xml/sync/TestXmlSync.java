package net.sf.ahtutils.xml.sync;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.ahtutils.xml.status.TestXmlStatus;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSync extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlSync.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix,Sync.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Sync actual = create(true);
    	Sync expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Sync.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Sync create(boolean withChilds)
    {
    	Sync xml = new Sync();
    	xml.setClientId(123);
    	xml.setServerId(345);
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    		xml.setResult(net.sf.ahtutils.xml.status.TestXmlResult.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlSync.initJaxb();
		TestXmlSync.initFiles();	
		TestXmlSync test = new TestXmlSync();
		test.save();
    }
}