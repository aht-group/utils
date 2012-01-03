package net.sf.ahtutils.controller.factory.latex;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.model.pojo.status.TranslationStatistic;
import net.sf.ahtutils.test.AbstractAhtUtilTest;
import net.sf.ahtutils.xml.status.Langs;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexStatusFactory extends AbstractAhtUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(TestLatexStatusFactory.class);
	
	private LatexStatusFactory factory;
	private Document doc;

	private Namespace nsLang;
	private Element langs;
	
	public static TestLatexStatusFactory factory()
	{
		TestLatexStatusFactory factory = new TestLatexStatusFactory();
		factory.init();
		
		return factory;
	}
	
	@Before
	public void init()
	{	
		factory = new LatexStatusFactory(null,null,null);
		
		nsLang = Namespace.getNamespace("s", "http://ahtutils.aht-group.com/status");
		
		langs = createLangs();

		doc = new Document();
		doc.setRootElement(new Element("root"));
	}
	
	public List<TranslationStatistic> createStatistic()
	{
		Langs langs = new Langs();
		List<Langs> listLangs = new ArrayList<Langs>();
		
		List<TranslationStatistic> stats = new ArrayList<TranslationStatistic>();
		stats.add(factory.createStatistic(listLangs));
		return stats;
	}
	
	private Element createLangs()
	{
		return new Element("langs",nsLang);
	}
	
	@Test
	public void noLangs()
	{
		Assert.assertFalse(factory.hasLangs(doc));
	}
	
	@Test
	public void hasOneLangs()
	{
		doc.getRootElement().addContent(langs);
		Assert.assertTrue(factory.hasLangs(doc));
	}
	
	@Test
	public void hasTwoLangs()
	{
		doc.getRootElement().addContent(createLangs());
		doc.getRootElement().addContent(createLangs());
		Assert.assertTrue(factory.hasLangs(doc));
		Assert.assertEquals(2, factory.getLangsElements(doc).size());
	}
	
	@Test
	public void jdomLangs()
	{
		doc.getRootElement().addContent(langs);
		List<Element> actual = factory.getLangsElements(doc);
		Assert.assertEquals(1, actual.size());
		Assert.assertEquals(langs, actual.get(0));
	}
	
	@Test
	public void xmlLangs()
	{
		doc.getRootElement().addContent(langs);
		List<Langs> actual = factory.getLangs(doc);
		Assert.assertEquals(1, actual.size());
	}
}