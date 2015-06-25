package net.sf.ahtutils.doc.er;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.batik.transcoder.TranscoderException;
import org.metachart.processor.graph.Graph2DotConverter;
import org.metachart.xml.graph.Graph;
import org.metachart.xml.graph.Node;
import org.openfuxml.media.transcode.Svg2PdfTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractErDiagram
{
	final static Logger logger = LoggerFactory.getLogger(AbstractErDiagram.class);
	
	protected File fSrc,fDot,fSvg;
	protected File dPdf;
	
	protected String packages;
	protected String colorScheme;
	
	protected void create(String key) throws ClassNotFoundException, IOException, TranscoderException
	{
		List<String> subset = new ArrayList<String>();
		subset.add(key);
		buildSvg("neato",subset,new File(fSvg,key+".svg"),new File(dPdf,key+".pdf"));
	}
	
	protected void buildSvg(String type, List<String> subset,File fDst, File fPdf) throws ClassNotFoundException, IOException, TranscoderException
	{
		ErGraphProcessor egp = new ErGraphProcessor(fSrc);
		egp.addPackages(packages,subset);
		
		Graph g = egp.create();
//		JaxbUtil.info(g);System.exit(-1);
		
		Node xml = JaxbUtil.loadJAXB(colorScheme, Node.class);
		JaxbUtil.info(xml);
		
		Graph2DotConverter gdc = new Graph2DotConverter("b");
		gdc.setColorScheme(xml);
		
		gdc.setOverlap(false);
		gdc.setRatio(0.9);
		gdc.setRanksep(0.2);
		
		gdc.convert(g);
		gdc.save(fDot);
		
		ErImageWriter w = new ErImageWriter(type);
		w.svg(fDot, fDst);
		
		if(fPdf!=null)
		{
			logger.info("SVG-PDF");
			Svg2PdfTranscoder.transcode(fDst,fPdf);
			logger.info("SVG-PDF done");
		}
	}
}