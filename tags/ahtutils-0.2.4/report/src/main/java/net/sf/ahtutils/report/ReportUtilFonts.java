package net.sf.ahtutils.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.sf.ahtutils.report.exception.ReportException;
import net.sf.ahtutils.xml.report.Jr;
import net.sf.ahtutils.xml.report.Media;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.Reports;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportUtilFonts {
	
	private Reports reports;
	private String  reportRoot;
	
	final static Logger logger = LoggerFactory.getLogger(ReportUtilFonts.class);

	public ReportUtilFonts(Reports reports) throws ReportException {
		this.reports = reports;
		reportRoot = reports.getDir() +"/";
	}
	
	@SuppressWarnings("unchecked")
	public void replaceAll(String oldFont, String newFont, Boolean productive) throws JDOMException, IOException
	{
		for(Report report : reports.getReport())
 		{
 			for (Media media : report.getMedia())
 			{
 				for (Jr jr : media.getJr())
 				{
 					String jrxml  = reportRoot +"/" +"jrxml"  +"/" +report.getDir() +"/" + media.getType() + "/" + jr.getType() + jr.getName() +".jrxml";
 					logger.debug("Inspecting " +jrxml +" to find Font attributes.");
 					
 					org.jdom.Document jdomDoc =  JDomUtil.load(jrxml);
 					XPath xpath = XPath.newInstance("//*[local-name()='font']");
 					ArrayList<Element> nodes = new ArrayList<Element>();
 					nodes.addAll((ArrayList<Element>) xpath.selectNodes(jdomDoc));
 					Boolean changed = false;
 					for (Element node : nodes)
 					{
 						Element font = (Element) node;
 						String fontName = font.getAttributeValue("fontName");
 						if (fontName!=null && fontName.equals(oldFont))
 						{
 							logger.debug("Changing font from " +oldFont +" to " +newFont);
 							font.setAttribute("fontName", newFont);
 							changed = true;
 						}
 					}
 					if (changed)
 					{
 						XMLOutputter xmlOutput = new XMLOutputter(org.jdom.output.Format.getPrettyFormat());
 						if (productive)
 						{
 							xmlOutput.output(jdomDoc, new FileWriter(jrxml));
 						}
 						else
 						{
 							logger.info("Changed font from " +oldFont +" to " +newFont +" in " +jr.getType() + jr.getName() +".jrxml");
 						}
 	 					
 					}
 				}
 			}
 		}			
	}
}
