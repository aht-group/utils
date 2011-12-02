package net.sf.ahtutils.test.controller.factory;

import net.sf.ahtutils.controller.exception.AhtUtilsIntegrityException;
import net.sf.ahtutils.controller.factory.ejb.status.EjbStatusFactory;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.ejb.status.AhtUtilsStatus;
import net.sf.ahtutils.test.AbstractAhtUtilTest;
import net.sf.ahtutils.test.controller.util.TestRankComparator;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Status;
import net.sf.exlp.util.io.LoggerInit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUtilsStatusEjbFactory extends AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestRankComparator.class);
	
	private EjbStatusFactory<AhtUtilsStatus,AhtUtilsLang,AhtUtilsDescription> facStatus;
	private Status status;
	
	@Before
	public void init()
	{
		facStatus = EjbStatusFactory.createFactory(AhtUtilsStatus.class, AhtUtilsLang.class,AhtUtilsDescription.class);
		status = createStatus();
	}
    
    @After
    public void close()
    {
    	facStatus = null;
    	status = null;
    }
 
    @Test
    public void testClass() throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
    {
    	Object o = facStatus.create(status);
    	Assert.assertTrue(o instanceof AhtUtilsStatus);
    }
    
    @Test
    public void testCode() throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
    {
    	AhtUtilsStatus ejb = (AhtUtilsStatus)facStatus.create(status);
    	Assert.assertEquals(status.getCode(), ejb.getCode());
    }
    
    @Test
    public void testMapSize() throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
    {
    	AhtUtilsStatus ejb = (AhtUtilsStatus)facStatus.create(status);
    	Assert.assertEquals(status.getLangs().getLang().size(), ejb.getName().size());
    }
    
    @Test
    public void testTranslation() throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
    {
    	Lang lang = status.getLangs().getLang().get(0);
    	AhtUtilsStatus ejb = (AhtUtilsStatus)facStatus.create(status);
    	Assert.assertEquals(lang.getTranslation(), ejb.getName().get(lang.getKey()).getLang());
    }
    
    @Test(expected=AhtUtilsIntegrityException.class)
    public void testMissingKey() throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
    {
    	status.getLangs().getLang().get(0).setKey(null);
    	facStatus.create(status);
    }
    
    @Test(expected=AhtUtilsIntegrityException.class)
    public void testMissingTranslation() throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
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
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, AhtUtilsIntegrityException
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