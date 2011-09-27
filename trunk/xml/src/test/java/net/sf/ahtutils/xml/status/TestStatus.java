package net.sf.ahtutils.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStatus extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestStatus.class);
	
	private static final String rootDir = "src/test/resources/data/xml/status/status";
	
	private static File fStatus,fAht;
	
	@BeforeClass
	public static void initFiles()
	{
		fStatus = new File(rootDir,"status.xml");
		fAht = new File(rootDir,"statusAht.xml");
	}
    
    @Test
    public void testStatus() throws FileNotFoundException
    {
    	Status test = createStatus();
    	Status ref = (Status)JaxbUtil.loadJAXB(fStatus.getAbsolutePath(), Status.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
    
    @Test
    public void testAhtStatus() throws FileNotFoundException
    {
    	Aht test = new Aht();
    	test.getStatus().add(createStatus());
    	Aht ref = (Aht)JaxbUtil.loadJAXB(fAht.getAbsolutePath(), Aht.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Status status = createStatus();
    	JaxbUtil.debug2(this.getClass(),status, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fStatus, status, new AhtUtilsNsPrefixMapper(), true);
    	
    	Aht aht = new Aht();
    	aht.getStatus().add(status);
    	JaxbUtil.debug2(this.getClass(),aht, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fAht, aht, new AhtUtilsNsPrefixMapper(), true);
    }
    
    public static Status createStatus()
    {
    	Status status = new Status();
    	status.setCode("myCode");
    	status.setGroup("myGroup");
    	status.setLangs(TestLang.createLangs());
    	return status;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestStatus.initFiles();	
		TestStatus test = new TestStatus();
		test.save();
    }
}