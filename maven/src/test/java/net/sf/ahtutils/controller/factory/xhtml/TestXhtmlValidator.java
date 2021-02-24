package net.sf.ahtutils.controller.factory.xhtml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jeesl.processor.JeeslXhtmlParser;
import org.junit.Before;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import net.sf.ahtutils.test.AbstractUtilsMavenTst;
import net.sf.ahtutils.test.UtilsMavenTstBootstrap;

public class TestXhtmlValidator extends AbstractUtilsMavenTst
{
	final static Logger logger = LoggerFactory.getLogger(TestXhtmlValidator.class);


	@Before
	public void init() throws EvaluatorException, IOException, ParserConfigurationException, SAXException
	{
		JeeslXhtmlParser xhtmlParser = new JeeslXhtmlParser("src/test/resources/data/factory/xhtml");
		xhtmlParser.parse();
    }

	public static void main(String[] args) throws Exception
    {
		UtilsMavenTstBootstrap.init();

		TestXhtmlValidator test = new TestXhtmlValidator();
		test.init();
    }
}