package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	private boolean withResponsible,withOrganisation;

	public LatexQmWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm)
	{
		super(config,translations,langs,cmm);
		
		withResponsible = false;
		withOrganisation = false;
	}
	
	private List<String> buildHeaderKeys()
	{
		List<String> keys = new ArrayList<String>();
		
		keys.add("auTableQaRole");
		keys.add("auTableQaName");
		if(withResponsible){keys.add("auTableQaResponsibilities");}
		if(withOrganisation){keys.add("auTableQaOrganisation");}
		
		return keys;
	}
	
	// *****************************************************************************
	
	public void writeQaTeam(Qa qa,boolean withResponsible, boolean withOrganisation) throws OfxAuthoringException, IOException
	{
		setWithResponsible(withResponsible);
		setWithOrganisation(withOrganisation);
		for(String lang : langs)
		{
			writeQaTeam(qa, lang);
		}
	}
	
	public void writeQaTeam(Qa qa,String lang) throws OfxAuthoringException, IOException
	{
		File f = new File(baseLatexDir+"/"+lang+"/tab/qa/team.tex");
		
		OfxQaTeamTableFactory fOfx = new OfxQaTeamTableFactory(config,lang,translations);
		Table table = fOfx.build(qa, buildHeaderKeys());
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
		
	public void setWithResponsible(boolean withResponsible) {this.withResponsible = withResponsible;}
	public void setWithOrganisation(boolean withOrganisation) {this.withOrganisation = withOrganisation;}
}