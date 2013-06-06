package net.sf.ahtutils.controller.factory.ofx.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.factory.ofx.status.OfxStatusTableFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlDescriptionFactory;
import net.sf.ahtutils.controller.factory.xml.status.XmlLangFactory;
import net.sf.ahtutils.test.AhtUtilsDocBootstrap;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.table.LatexGridTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxStatusTableFactory extends AbstractOfxStatusFactoryTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxStatusTableFactory.class);
	
	private OfxStatusTableFactory fOfx;
	private final String lang ="de";
	private static List<Status> lStatus;
	private static Translations translations;
	private String[] headerKeys = {"key1","key2","key3"};
	
	@BeforeClass
	public static void initFiles() throws FileNotFoundException
	{
		fXml = new File(rootDir,"tableStatus.xml");
		fTxt = new File(rootDir,"tableStatus.tex");
		translations = JaxbUtil.loadJAXB("src/test/resources/data/xml/dummyTranslations.xml", Translations.class);
	}
	
	@Before
	public void init()
	{			
		Status status = new Status();
		status.setCode("myCode");
		status.setLangs(new Langs());
		status.setDescriptions(new Descriptions());
		
		status.getLangs().getLang().add(XmlLangFactory.create(lang, "myLang"));
		status.getDescriptions().getDescription().add(XmlDescriptionFactory.create(lang, "myDescription"));
		
		lStatus = new ArrayList<Status>();
		lStatus.add(status);
		
		fOfx = new OfxStatusTableFactory(lStatus,headerKeys,translations);
	}
	
	@Test
	public void testOfx() throws FileNotFoundException
	{	
		Table actual = fOfx.toOfx(lang);
		saveXml(actual,fXml,false);
		Table expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Table.class);
		assertJaxbEquals(expected, actual);
	}
	
	@Test
	public void testLatex() throws OfxAuthoringException, IOException
	{
		Table actual = fOfx.toOfx(lang);
		LatexGridTableRenderer renderer = new LatexGridTableRenderer();
		renderer.render(actual);
    	debug(renderer);
    	save(renderer,fTxt);
    	assertText(renderer,fTxt);
	}
	
	public static void main(String[] args) throws Exception
    {
		AhtUtilsDocBootstrap.init();
		
		TestOfxStatusTableFactory.initFiles();
		TestOfxStatusTableFactory test = new TestOfxStatusTableFactory();
		test.setSaveReference(true);
		test.init();
	
		test.testOfx();
		test.testLatex();
    }
}