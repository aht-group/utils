package net.sf.ahtutils.controller.doc.installation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.util.OfxLatexComment;
import org.openfuxml.renderer.processor.latex.content.SectionFactory;
import org.openfuxml.renderer.processor.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildDocR 
{
	final static Logger logger = LoggerFactory.getLogger(BuildDocR.class);
	
	public static String resourceName = "ofx.aht-utils/installation/r.xml";
	
	private Section section;
	private SectionFactory latexSectionFactory;
	
	public BuildDocR()
	{
		try
		{
			section = JaxbUtil.loadJAXB(resourceName, Section.class);
		}
		catch (FileNotFoundException e)
		{
			logger.error("This should not happen because a junit Tests verifies the file!");
			logger.error(e.getMessage());
		}
		JaxbUtil.debug(section);
		
		latexSectionFactory = new SectionFactory(1,new LatexPreamble());
	}
	
	public void renderLatex(String dstName) throws OfxAuthoringException, FileNotFoundException
	{
		latexSectionFactory.render(section);
		List<String> content = new ArrayList<String>();
		content.addAll(OfxLatexComment.comment(BuildDocInstallation.modifyWarning,true));
		content.addAll(latexSectionFactory.getContent());
		
		OfxContentDebugger.debug(content);
	}
	
}