package net.sf.ahtutils.controller.factory.ofx.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.ahtutils.test.AhtUtilsTstBootstrap;
import net.sf.ahtutils.xml.access.TestXmlView;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.access.Views;
import net.sf.ahtutils.xml.status.TestXmlDescription;
import net.sf.ahtutils.xml.status.TestXmlLang;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.table.LatexGridTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxViewTableFactory extends AbstractOfxSecurityFactoryTest
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxViewTableFactory.class);
	
	private OfxViewTableFactory fOfx;
	private final String lang ="de";
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"tableView.xml");
		fTxt = new File(rootDir,"tableView.tex");
	}
	
	@Before
	public void init()
	{	
		fOfx = new OfxViewTableFactory(lang);
	}
	
	private Views createViews()
	{
		Views views = new Views();
		
		View view = TestXmlView.create(true);
		view.getLangs().getLang().add(TestXmlLang.create(false,lang,"View xyz"));
		view.getDescriptions().getDescription().add(TestXmlDescription.create(false,lang,"This view is for testing purposes only."));
		
		views.getView().add(view);
		return views;
	}
	
	@Test
	public void testOfx() throws FileNotFoundException
	{
		Views views = createViews();
		
		Table actual = fOfx.toOfx(views.getView());
		saveXml(actual,fXml,false);
		Table expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Table.class);
		assertJaxbEquals(expected, actual);
	}
	
	@Test
	public void testLatex() throws OfxAuthoringException, IOException
	{
		Table actual = fOfx.toOfx(createViews().getView());
		LatexGridTableFactory renderer = new LatexGridTableFactory();
		renderer.render(actual);
    	debug(renderer);
    	save(renderer,fTxt);
    	assertText(renderer,fTxt);
		
	}
	
	public static void main(String[] args) throws Exception
    {
		AhtUtilsTstBootstrap.init();
		
		TestOfxViewTableFactory.initFiles();
		TestOfxViewTableFactory test = new TestOfxViewTableFactory();
		test.setSaveReference(true);
		test.init();
	
		test.testOfx();
		test.testLatex();
    }
}