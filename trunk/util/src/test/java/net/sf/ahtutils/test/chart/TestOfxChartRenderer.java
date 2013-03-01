package net.sf.ahtutils.test.chart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.junit.Test;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.Grid;
import org.openfuxml.xml.addon.chart.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxChartRenderer.class);
	
	public TestOfxChartRenderer()
	{
		
	}
	
	public Chart getTimeSeries()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		
		chart.setRenderer(getType());
		chart.setGrid(getGrid());
		
		chart.getDataSet().add(getX("a"));
		return chart;
	}
	
	private Grid getGrid()
	{
		Grid grid = new Grid();
		grid.setDomain(false);
		grid.setRange(false);
		return grid;
	}
	
	private Renderer getType()
	{
		Renderer type = new Renderer();
		Renderer.Timebar tBar = new Renderer.Timebar();
		tBar.setShadow(false);
		tBar.setGradient(false);
		type.setTimebar(tBar);
		return type;
	}
	
	private DataSet getX(String label)
	{
		Random rnd = new Random();
		DataSet x = new DataSet();
		x.setLabel(label);
		for(int i=1;i<5;i++)
		{
			Data data = new Data();
			data.setRecord(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2010, 1, i)));
			data.setY(1+rnd.nextInt(i));
			if(rnd.nextInt(100)<70){x.getData().add(data);}
		}
		return x;
	}
	
	@Test
	public void renderDirect() throws IOException
    {	
		TestOfxChartRenderer test = new TestOfxChartRenderer();
		Chart chart = test.getTimeSeries();
		JaxbUtil.debug(chart);
			
		OfxChartRenderer ofxRenderer = new OfxChartRenderer();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("target/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
    }
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("config");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
		
		TestOfxChartRenderer test = new TestOfxChartRenderer();
		test.renderDirect();
	}
}
