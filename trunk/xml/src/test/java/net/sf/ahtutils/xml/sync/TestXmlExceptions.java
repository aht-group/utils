package net.sf.ahtutils.xml.sync;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlExceptions extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlExceptions.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Exceptions.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Exceptions actual = create(true);
    	Exceptions expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Exceptions.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Exceptions create(boolean withChilds)
    {
    	Exceptions xml = new Exceptions();

    	if(withChilds)
    	{
    		xml.getException().add(TestXmlException.create(false));
    		xml.getException().add(TestXmlException.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlExceptions.initJaxb();
		TestXmlExceptions.initFiles();	
		TestXmlExceptions test = new TestXmlExceptions();
		test.save();
    }
}