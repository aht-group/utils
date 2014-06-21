package net.sf.ahtutils.xml.qa;

import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTestBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlComment extends AbstractXmlQaTest
{
	final static Logger logger = LoggerFactory.getLogger(net.sf.ahtutils.xml.qa.Test.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix,Comment.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Comment actual = create();
    	Comment expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Comment.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    public static Comment create()
    {
    	Comment xml = new Comment();
    	xml.setValue("myComment");
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTestBootstrap.init();
			
		TestXmlComment.initJaxb();
		TestXmlComment.initFiles();	
		TestXmlComment test = new TestXmlComment();
		test.save();
    }
}