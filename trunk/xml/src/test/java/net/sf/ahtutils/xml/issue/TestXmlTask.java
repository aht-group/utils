package net.sf.ahtutils.xml.issue;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTask extends AbstractXmlIssueTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTask.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Task.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Task actual = create(true);
    	Task expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Task.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Task create(boolean withChilds)
    {
    	Task xml = new Task();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.setTasks(TestXmlTasks.create(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTask.initJaxb();
		TestXmlTask.initFiles();	
		TestXmlTask test = new TestXmlTask();
		test.save();
    }
}