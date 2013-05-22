package net.sf.ahtutils.report;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.ahtutils.test.AbstractAhtUtilsReportTest;
import net.sf.ahtutils.xml.report.Info;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Info.Title;
import net.sf.exlp.util.xml.DomUtil;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jdom.Namespace;
import org.jfree.chart.JFreeChart;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.xml.addon.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestDomUtil extends AbstractAhtUtilsReportTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDomUtil.class);
    	
	private Element root;
	private Info expected;
	
	@Before
	public void init() throws IOException, ParserConfigurationException
	{
		Title title = new Title();
		title.setValue("MyTitle");
		
		expected = new Info();
		expected.setTitle(title);
		
		Document doc = JaxbUtil.toW3CDocument(expected);
		root = doc.getDocumentElement();
		
	}
	
	@Test
	public void domUtil()
	{
		Info actual = DomUtil.toJaxb(root, Info.class);
		this.assertJaxbEquals(expected, actual);
	}
	
	@Test
	public void chartReader()
	{
		//Read JDOM data document
		org.jdom.Document jdomDoc =  JDomUtil.load("src/test/resources/data/xml/report/testData.xml");
		
		//Get the root element (report)
		org.jdom.Element reportElement = jdomDoc.getRootElement();
		//System.out.println("Root: " +reportElement.toString());
		
		//Get the info element as child of report element
		org.jdom.Element infoElement   = reportElement.getChild("info", Namespace.getNamespace("http://ahtutils.aht-group.com/report"));
		//System.out.println("Info: " +infoElement.toString());
		
		Info info = (Info) JDomUtil.toJaxb(infoElement, Info.class);
		JaxbUtil.error(info);
		
		OfxChartRenderer ofxRenderer = new OfxChartRenderer();
		for (Media media : info.getMedia())
		{
			Chart chart          = media.getChart();
			logger.error("Test chart");
			JaxbUtil.error(chart);
			JFreeChart jfreeChart = ofxRenderer.render(chart);
			BufferedImage chartImage = jfreeChart.createBufferedImage(300, 400);
		}
	}
	
}