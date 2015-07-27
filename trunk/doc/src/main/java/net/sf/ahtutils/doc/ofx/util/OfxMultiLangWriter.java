package net.sf.ahtutils.doc.ofx.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringIO;

public class OfxMultiLangWriter
{	
	final static Logger logger = LoggerFactory.getLogger(OfxMultiLangWriter.class);
	
	private String[] keys;
	private File baseLatex;
	private CrossMediaManager cmm;
	private DefaultSettingsManager dsm;
	
	public OfxMultiLangWriter(File baseLatex, String[] keys, CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		this.baseLatex=baseLatex;
		this.keys=keys;
		this.cmm=cmm;
		this.dsm=dsm;
	}
	
	public void table(String fileName, Table table) throws OfxAuthoringException, IOException
	{
		for(String lang : keys)
		{
			OfxLangFilter omf = new OfxLangFilter(lang);
			
			LatexTableRenderer tableRenderer = new LatexTableRenderer(cmm,dsm);
			tableRenderer.setPreBlankLine(false);
			tableRenderer.render(omf.filterLang(table));
			
			File f = new File(baseLatex,lang+"/table/"+fileName);
			StringWriter sw = new StringWriter();
			tableRenderer.write(sw);
			StringIO.writeTxt(f, sw.toString());
		}
	}
	
	public  void section(int sectionLevel,String fileName,Section section) throws OfxAuthoringException, OfxConfigurationException, IOException
	{
		for(String lang : keys)
		{
			File f = new File(baseLatex,lang+"/section/"+fileName);
			logger.trace(f.getAbsolutePath());
			OfxLangFilter omf = new OfxLangFilter(lang);
			
			LatexSectionRenderer sectionRenderer = new LatexSectionRenderer(cmm,dsm,sectionLevel,new LatexPreamble(cmm,dsm));
			sectionRenderer.render(omf.filterLang(section));

			StringWriter sw = new StringWriter();
			sectionRenderer.write(sw);
			logger.trace("Writing to : "+f.getAbsolutePath());
			StringIO.writeTxt(f, sw.toString());
		}
	}
}