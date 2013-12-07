package net.sf.ahtutils.controller.doc.installation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.doc.AbstractDocumentationFactory;
import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.latex.util.OfxLatexComment;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildDocR extends AbstractDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(BuildDocR.class);
	
	public static String resourceName = "ofx.aht-utils/installation/r.xml";
	
	private Section section;
	private LatexSectionRenderer latexSectionFactory;
	
	public BuildDocR(String lang)
	{
		super(lang);
		try
		{
			section = JaxbUtil.loadJAXB(resourceName, Section.class);
			section = multiLangFilter.filterLang(section);
			JaxbUtil.info(section);
		}
		catch (FileNotFoundException e)
		{
			logger.error("This should not happen because a junit Tests verifies the file!");
			logger.error(e.getMessage());
		}
		JaxbUtil.debug(section);
		
		latexSectionFactory = new LatexSectionRenderer(1,new LatexPreamble());
	}
	
	public void renderLatex(String dstName) throws OfxAuthoringException, FileNotFoundException
	{
		latexSectionFactory.render(section);
		List<String> content = new ArrayList<String>();
		content.addAll(OfxLatexComment.comment(BuildDocInstallation.modifyWarning,true));
		content.addAll(latexSectionFactory.getContent());
		
		OfxContentDebugger.debug(content);
		
		File fDst = this.getFile(dstName);
		this.write(content, fDst);
		
	}
	
}