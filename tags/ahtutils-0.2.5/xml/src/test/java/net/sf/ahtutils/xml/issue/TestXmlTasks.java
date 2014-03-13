package net.sf.ahtutils.xml.issue;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTasks extends AbstractXmlIssueTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlTasks.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Tasks.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Tasks actual = create(true);
    	Tasks expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Tasks.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Tasks create(boolean withChilds)
    {
    	Tasks xml = new Tasks();
    	
    	if(withChilds)
    	{
    		xml.getTask().add(TestXmlTask.create(false));xml.getTask().add(TestXmlTask.create(false));
    	}
    	    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlTasks.initJaxb();
		TestXmlTasks.initFiles();	
		TestXmlTasks test = new TestXmlTasks();
		test.save();
    }
}