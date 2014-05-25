package net.sf.ahtutils.doc.er;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.test.AhtUtilsDocBootstrap;

import org.apache.commons.configuration.Configuration;
import org.metachart.processor.graph.Graph2DotConverter;
import org.metachart.xml.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliErDiagram
{
	final static Logger logger = LoggerFactory.getLogger(CliErDiagram.class);
	
	public CliErDiagram()
	{
	
	}
	
	public void create() throws IOException, ClassNotFoundException
	{
		File fSrc = new File("../ejb/src/main/java");
		File fDot = new File("../doc/src/main/resources/listing.aht-utils/administration/security/security-er.dot");
		File fSvg = new File("../doc/src/main/resources/svg.aht-utils/administration/security/security-er.svg");
		
		ErGraphProcessor ofx = new ErGraphProcessor(fSrc);
		ofx.addPackages("net/sf/ahtutils/model/ejb");
		
		Graph g = ofx.create();
		
		Graph2DotConverter gdc = new Graph2DotConverter("a","b");
		gdc.convert(g);
		gdc.save(fDot);
		
		ErImageWriter w = new ErImageWriter();
		w.svg(fDot, fSvg);
	}

	public static void main(String args[]) throws Exception
	{
		Configuration config = AhtUtilsDocBootstrap.init();
		
		CliErDiagram er = new CliErDiagram();
		er.create();
	}
}
