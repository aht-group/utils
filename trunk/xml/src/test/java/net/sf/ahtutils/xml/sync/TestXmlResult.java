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

public class TestXmlResult extends AbstractXmlSyncTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlResult.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix,Result.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Result actual = create(true);
    	Result expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Result.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Result create(boolean withChilds)
    {
    	Result xml = new Result();
    	xml.setTotal(3);
    	xml.setSuccess(2);
    	xml.setFail(1);
    	
    	if(withChilds)
    	{
    		xml.setStatus(TestXmlStatus.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
		DateUtil.ignoreTimeZone=true;
		
		TestXmlResult.initJaxb();
		TestXmlResult.initFiles();	
		TestXmlResult test = new TestXmlResult();
		test.save();
    }
}