package net.sf.ahtutils.controller.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.controller.doc.installation.BuildDocInstallation;
import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.latex.util.OfxLatexComment;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.util.OfxMultilangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsInstallationLatexFactory extends AbstractDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(UtilsInstallationLatexFactory.class);
	
	public static String resourceR = "ofx.aht-utils/installation/r.xml";
	
	public static enum Type{r};
	private Map<Type,String> map;
	
	protected OfxMultilangFilter multiLangFilter;
	
	private Section section;
	private LatexSectionRenderer latexSectionFactory;
	
	public UtilsInstallationLatexFactory(String lang)
	{
		super(lang);
		multiLangFilter = new OfxMultilangFilter(lang);
		
		map = new Hashtable<Type,String>();
		map.put(Type.r, resourceR);
		
		try
		{
			section = JaxbUtil.loadJAXB(resourceR, Section.class);
			section = multiLangFilter.filterLang(section);
			JaxbUtil.info(section);
		}
		catch (FileNotFoundException e)
		{
			logger.error("This should not happen because a junit Tests verifies the exisitenence of the file!");
			logger.error(e.getMessage());
		}
		JaxbUtil.debug(section);
		
		latexSectionFactory = new LatexSectionRenderer(1,new LatexPreamble());
	}
	
	public void renderLatex(Type type)
	{
		logger.info("Processing "+map.get(type));
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