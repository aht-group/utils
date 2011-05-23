package net.sf.ahtutils.test.controller.factory;

import net.sf.ahtutils.controller.exception.AhtUtilIntegrityException;
import net.sf.ahtutils.controller.factory.UtilsStatusEjbFactory;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.ejb.status.AhtUtilsStatus;
import net.sf.ahtutils.test.AbstractAhtUtilTest;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUtilsStatusEjbFactory extends AbstractAhtUtilTest
{
	static Log logger = LogFactory.getLog(TestUtilsStatusEjbFactory.class);
	
	private UtilsStatusEjbFactory<AhtUtilsStatus,AhtUtilsLang> facStatus;
	private Status status;
	
	@Before
	public void init()
	{
//		facStatus = new UtilsStatusEjbFactory<AhtUtilsStatus,AhtUtilsLang>(AhtUtilsStatus.class, AhtUtilsLang.class);
		facStatus = UtilsStatusEjbFactory.createFactory(AhtUtilsStatus.class, AhtUtilsLang.class);
		status = createStatus();
	}
    
    @After
    public void close()
    {
    	facStatus = null;
    	status = null;
    }
 
    @Test
    public void testClass() throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
    {
    	Object o = facStatus.create(status);
    	Assert.assertTrue(o instanceof AhtUtilsStatus);
    }
    
    @Test
    public void testCode() throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
    {
    	AhtUtilsStatus ejb = (AhtUtilsStatus)facStatus.create(status);
    	Assert.assertEquals(status.getCode(), ejb.getCode());
    }
    
    @Test
    public void testMapSize() throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
    {
    	AhtUtilsStatus ejb = (AhtUtilsStatus)facStatus.create(status);
    	Assert.assertEquals(status.getLangs().getLang().size(), ejb.getName().size());
    }
    
    @Test
    public void testTranslation() throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
    {
    	Lang lang = status.getLangs().getLang().get(0);
    	AhtUtilsStatus ejb = (AhtUtilsStatus)facStatus.create(status);
    	Assert.assertEquals(lang.getTranslation(), ejb.getName().get(lang.getKey()).getLang());
    }
    
    @Test(expected=AhtUtilIntegrityException.class)
    public void testMissingKey() throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
    {
    	status.getLangs().getLang().get(0).setKey(null);
    	facStatus.create(status);
    }
    
    @Test(expected=AhtUtilIntegrityException.class)
    public void testMissingTranslation() throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
    {
    	status.getLangs().getLang().get(0).setTranslation(null);
    	facStatus.create(status);
    }
    
    private Status createStatus()
    {
    	Status status = new Status();
    	status.setCode("testCode");
    	status.setLangs(getLangs());
    	return status;
    }
    
    private Langs getLangs()
    {
    	Langs langs = new Langs();
    	Lang l1 = new Lang();l1.setKey("en");l1.setTranslation("t1");langs.getLang().add(l1);
    	Lang l2 = new Lang();l2.setKey("de");l2.setTranslation("t2");langs.getLang().add(l2);
    	return langs;
    }
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, AhtUtilIntegrityException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestUtilsStatusEjbFactory test = new TestUtilsStatusEjbFactory();
		test.init();
		test.testClass();
		test.testCode();
		test.testMapSize();
//		test.testMissingKey();
//		test.testMissingTranslation();
		test.testTranslation();
		test.close();
    }
}