package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.qa.OfxQaCategoriesSectionFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxQaCategorySectionFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxQaTeamTableFactory;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexQmWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexQmWriter.class);
	
	private Configuration config;
	private String baseLatexDir;
	
	private Translations translations;
	private String[] langs;
	private String[] headerKeys;
	
	private LatexCrossMediaManager cmm;
	
	public LatexQmWriter(Configuration config, Translations translations,String[] langs)
	{
		this.config=config;
		this.translations=translations;
		this.langs=langs;
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
		
		headerKeys = new String[2];
		headerKeys[0] = "auTableQaRole";
		headerKeys[1] = "auTableQaName";
		
		cmm = new LatexCrossMediaManager(new File(baseLatexDir),config.getString(LatexCrossMediaManager.keyOfxLatexImageDir));
	}
	
	// *****************************************************************************
	
	public void writeQaTeam(Qa qa) throws OfxAuthoringException, IOException
	{
		for(String lang : langs)
		{
			writeQaTeam(qa, lang);
		}
	}
	
	public void writeQaTeam(Qa qa,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/team.tex");
		
		OfxQaTeamTableFactory fOfx = new OfxQaTeamTableFactory(config,lang,translations);
		Table table = fOfx.build(qa, headerKeys);
		writeTable(table, f);
	}
	
	// *****************************************************************************
	
	public void writeQaCategories(Qa qa) throws OfxAuthoringException, IOException
	{
		for(String lang : langs)
		{
			writeQaCategories(qa, lang);
		}
	}
	
	public void writeQaCategories(Qa qa,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/section/qa/categories.tex");
		
		OfxQaCategoriesSectionFactory fOfx = new OfxQaCategoriesSectionFactory(config,lang,translations);
		Section section = fOfx.build(qa,lang+"/section/qa/category");
		writeSection(section, f);
	}
	
	// *****************************************************************************
	
	public void writeQaCategory(Category category) throws OfxAuthoringException, IOException
	{
		for(String lang : langs)
		{
			writeQaCategory(category, lang);
		}
	}
	
	public void writeQaCategory(Category category, String lang) throws OfxAuthoringException, IOException
	{
		String path = lang+"/section/qa/category";
		File f = new File(baseLatexDir+"/"+path+"/"+category.getCode()+".tex");
		
		OfxQaCategorySectionFactory fOfx = new OfxQaCategorySectionFactory(config,lang,translations);
		Section section = fOfx.build(category);
		writeSection(section, f);
	}
	
	private void writeTable(Table table, File f) throws OfxAuthoringException, IOException
	{
		LatexTableRenderer tableRenderer = new LatexTableRenderer(false);
		tableRenderer.render(table);
		
		StringWriter sw = new StringWriter();
		tableRenderer.write(sw);
		StringIO.writeTxt(f, sw.toString());
	}
	
	private void writeSection(Section section, File f) throws OfxAuthoringException, IOException
	{
		LatexSectionRenderer sectionRenderer = new LatexSectionRenderer(cmm,1,new LatexPreamble());
		sectionRenderer.render(section);
		
		StringWriter sw = new StringWriter();
		sectionRenderer.write(sw);
		StringIO.writeTxt(f, sw.toString());
	}
}