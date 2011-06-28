package net.sf.ahtutils.test.xml.status;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.test.AbstractXmlTest;
import net.sf.ahtutils.xml.ns.AhtUtilsNsPrefixMapper;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestLang extends AbstractXmlTest
{
	static Log logger = LogFactory.getLog(TestLang.class);
	
	private static final String rootDir = "src/test/resources/data/xml/status/lang";
	
	private static File fLang,fLangs;
	
	@BeforeClass
	public static void initFiles()
	{
		fLang = new File(rootDir,"lang.xml");
		fLangs = new File(rootDir,"langs.xml");
	}
    
    @Test
    public void testLang() throws FileNotFoundException
    {
    	Lang test = createLang();
    	Lang ref = (Lang)JaxbUtil.loadJAXB(fLang.getAbsolutePath(), Lang.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
    
    @Test
    public void testLangs() throws FileNotFoundException
    {
    	Langs test = createLangs();
    	Langs ref = (Langs)JaxbUtil.loadJAXB(fLangs.getAbsolutePath(), Langs.class);
    	Assert.assertEquals(JaxbUtil.toString(test),JaxbUtil.toString(ref));
    }
 
    public void save()
    {
    	logger.debug("Saving Reference XML");
    	Lang lang = createLang();
    	JaxbUtil.debug2(this.getClass(),lang, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fLang, lang, new AhtUtilsNsPrefixMapper(), true);
    	
    	Langs langs = createLangs();
    	JaxbUtil.debug2(this.getClass(),langs, new AhtUtilsNsPrefixMapper());
    	JaxbUtil.save(fLangs, langs, new AhtUtilsNsPrefixMapper(), true);
    }
    
    public static Langs createLangs()
    {
    	Langs langs = new Langs();
    	langs.getLang().add(createLang("de", "t1"));
    	langs.getLang().add(createLang("en", "t2"));
    	return langs;
    }
    
    public static Lang createLang(){return createLang("key", "translation");}
    
    public static Lang createLang(String key, String translation)
    {
    	Lang lang = new Lang();
    	lang.setKey(key);
    	lang.setTranslation(translation);
    	return lang;
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();		
			
		TestLang.initFiles();	
		TestLang test = new TestLang();
		test.save();
    }
}