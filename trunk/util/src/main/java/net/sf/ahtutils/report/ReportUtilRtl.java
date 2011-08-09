package net.sf.ahtutils.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.xpath.XPath;


public class ReportUtilRtl {
	
	static Log logger = LogFactory.getLog(ReportUtilRtl.class);
	
	public static InputStream LeftToRightConversion(String jrxmlName) throws JDOMException
	{
		Document doc = JDomUtil.load(jrxmlName);
		Integer pageWidth = doc.getRootElement().getAttribute("pageWidth").getIntValue();
		Integer margin    = doc.getRootElement().getAttribute("rightMargin").getIntValue();
		logger.info("Page has a width of " +pageWidth);	
		logger.info("Converting x position of elements by: x(rtl) = pageWidth - (x(ltr)+width(ltr)+(2*margin)) ");
			
		List<Element> ergebnis = XPath.selectNodes(doc, "//*" );
		
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
