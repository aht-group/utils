package net.sf.ahtutils.xml.aht;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.UtilsXmlTstBootstrap;
import net.sf.ahtutils.xml.access.TestXmlRole;
import net.sf.ahtutils.xml.status.TestXmlLangs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestQuery extends AbstractXmlAhtTest
{
	final static Logger logger = LoggerFactory.getLogger(TestQuery.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"query.xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Query actual = create();
    	Query expected = (Query)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Query.class);
    	assertJaxbEquals(expected, actual);
    }  
    
    private static Query create() {return create(true);}
    public static Query create(boolean withChilds)
    {
    	Query xml = new Query();
    	xml.setLang("myLang");
    	
    	if(withChilds)
    	{
    		xml.setRole(TestXmlRole.create(false));
    		xml.setLangs(TestXmlLangs.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml);}
	
	public static void main(String[] args)
    {
		UtilsXmlTstBootstrap.init();
			
		TestQuery.initPrefixMapper();
		TestQuery.initFiles();	
		TestQuery test = new TestQuery();
		test.save();
    }
}