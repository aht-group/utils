package net.sf.ahtutils.doc.er;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.sf.ahtutils.test.AhtUtilsDocBootstrap;
import net.sf.exlp.util.xml.JaxbUtil;

import org.metachart.processor.graph.Graph2DotConverter;
import org.metachart.xml.graph.Graph;
import org.metachart.xml.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliErDiagram
{
	final static Logger logger = LoggerFactory.getLogger(CliErDiagram.class);
	
	private Graph2DotConverter gdc;
	
	public CliErDiagram()
	{
		gdc = new Graph2DotConverter("a","b");
	}
	
	public void withColorScheme() throws FileNotFoundException
	{
		Node xml = JaxbUtil.loadJAXB("../doc/src/main/resources/listing.aht-utils/administration/security/colorScheme.xml", Node.class);
		JaxbUtil.info(xml);
		gdc.setColorScheme(xml);
	}
	
	public void create() throws IOException, ClassNotFoundException
	{
		File fSrc = new File("../ejb/src/main/java");
		File fDot = new File("../doc/src/main/resources/listing.aht-utils/administration/security/security-er.dot");
		File fSvg = new File("../doc/src/main/resources/svg.aht-utils/administration/security/security-er.svg");
		
		ErGraphProcessor erGraph = new ErGraphProcessor(fSrc);
		erGraph.addPackages("net/sf/ahtutils/model/ejb");
		
		Graph g = erGraph.create();
		JaxbUtil.info(g);
		
		gdc.setOverlap(false);
		gdc.setRatio(0.9);
		gdc.setRanksep(0.2);
		
		gdc.convert(g);
		gdc.save(fDot);
		
		ErImageWriter w = new ErImageWriter("dot");
		w.svg(fDot, fSvg);
	}

	public static void main(String args[]) throws Exception
	{
		AhtUtilsDocBootstrap.init();
		
		CliErDiagram er = new CliErDiagram();
		er.withColorScheme();
		er.create();
	}
}
