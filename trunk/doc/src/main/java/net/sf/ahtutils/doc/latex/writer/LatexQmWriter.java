package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;

import net.sf.ahtutils.doc.ofx.qa.OfxQaCategoriesSectionFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxQaTeamTableFactory;
import net.sf.ahtutils.doc.ofx.qa.OfxSectionQaCategoryFactory;
import net.sf.ahtutils.xml.qa.Category;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexQmWriter extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexQmWriter.class);
	
	private String[] headerKeys;
	
	public LatexQmWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm)
	{
		super(config,translations,langs,cmm);
		
		headerKeys = new String[2];
		headerKeys[0] = "auTableQaRole";
		headerKeys[1] = "auTableQaName";
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
			
			OfxSectionQaCategoryFactory fOfx = new OfxSectionQaCategoryFactory(config,lang,translations);
			Section section = fOfx.build(category);
			writeSection(section, f);
		}
}