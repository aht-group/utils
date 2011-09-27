package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDescription extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestDescription.class);
	
	private static final String rootDir = "src/test/resources/data/xml/status/description";
	
	private static File fDescription,fDescriptions;
	
	@BeforeClass
	public static void initFiles()
	{
		fDescription = new File(rootDir,"description.xml");
		fDescriptions = new File(rootDir,"descriptions.xml");
	}
    
    @Test
    public void testDescription() throws FileNotFoundException
    {
    	Description test = createDescription();
    	Description ref = (Description)JaxbUtil.loadJAXB(fDescription.getAbsolutePath(), Description.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
    
    @Test
    public void testDescriptions() throws FileNotFoundException
    {
    	Descriptions test = createDescriptions();
    	Descriptions ref = (Descriptions)JaxbUtil.loadJAXB(fDescriptions.getAbsolutePath(), Descriptions.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Description description = createDescription();
    	JaxbUtil.debug2(this.getClass(),description, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fDescription, description, new AhtUtilsNsPrefixMapper(), true);
    	
    	Descriptions descriptions = createDescriptions();
    	JaxbUtil.debug2(this.getClass(),descriptions, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fDescriptions, descriptions, new AhtUtilsNsPrefixMapper(), true);
    }
    
    public static Descriptions createDescriptions()
    {
    	Descriptions xml = new Descriptions();
    	xml.getDescription().add(createDescription("de", "kadfnsg kjnadfkg nadkfjng kjandfb"));
    	xml.getDescription().add(createDescription("en", "xovoadfv oearogaonfv dafgkno adfokgnb"));
    	return xml;
    }
    
    public static Description createDescription(){return createDescription("key", "adkfjng kadnfgkjnadkfjgn kajdnfgk jnadfkljgn kadjfgk jnadfg");}
    
    public static Description createDescription(String key, String text)
    {
    	Description xml = new Description();
    	xml.setKey(key);
    	xml.setValue(text);
    	return xml;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestDescription.initFiles();	
		TestDescription test = new TestDescription();
		test.save();
    }
}