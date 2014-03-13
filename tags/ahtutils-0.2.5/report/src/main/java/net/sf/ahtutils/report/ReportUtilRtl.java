package net.sf.ahtutils.report;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.output.Format;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportUtilRtl
{
	final static Logger logger = LoggerFactory.getLogger(ReportUtilXls.class);
	
	public static InputStream LeftToRightConversion(String jrxmlName) throws JDOMException
	{
		Document doc = JDomUtil.load(jrxmlName);
		Integer pageWidth = doc.getRootElement().getAttribute("pageWidth").getIntValue();
		Integer margin    = doc.getRootElement().getAttribute("rightMargin").getIntValue();
		logger.info("Page has a width of " +pageWidth);	
		logger.info("Converting x position of elements by: x(rtl) = pageWidth - (x(ltr)+width(ltr)+(2*margin)) ");
			
		
		XPathExpression<Element> xpath = XPathFactory.instance().compile("//*", Filters.element());
		List<Element> ergebnis = xpath.evaluate(doc);
		
		for (Element element : ergebnis)
		{	if (!(element.getAttribute("x")== null))
			{
				Integer x_ltr = element.getAttribute("x").getIntValue();
				Integer width = element.getAttribute("width").getIntValue();
				element.setAttribute("x", new Integer(pageWidth-(x_ltr+width+(2*margin))).toString());
				logger.trace("Converted x position from " +x_ltr +" to " +element.getAttribute("x").getIntValue());
			}
		}
		
		logger.debug("Correcting text alignment");
		Hashtable<String,String> alignmentMapping = new Hashtable<String,String>();
		alignmentMapping.put("Center", "Center");
		alignmentMapping.put("Left", "Right");
		alignmentMapping.put("Right", "Left");
		for (Element element : ergebnis)
		{	if (element.getName()=="textElement")
			{
				String alignment = null;
				if (element.getAttribute("textAlignment")==null)
				{
					alignment = "Left";
				}
				else
				{
					alignment = element.getAttribute("textAlignment").getValue();
				}
				logger.trace("Text alignment is " +alignment +", setting to " +alignmentMapping.get(alignment));
				 
				element.setAttribute("textAlignment", alignmentMapping.get(alignment));	
				logger.trace("Remapped text alignment");
			}
		}
		return JDomUtil.toInputStream(doc, Format.getPrettyFormat());
	}
}
