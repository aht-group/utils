package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.qa.OfxQaTeamTableFactory;
import net.sf.ahtutils.xml.qa.Qa;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
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
	
	public LatexQmWriter(Configuration config, Translations translations,String[] langs)
	{
		this.config=config;
		this.translations=translations;
		this.langs=langs;
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
		
		headerKeys = new String[2];
		headerKeys[0] = "auTableQaRole";
		headerKeys[1] = "auTableQaName";
	}
	
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
	
	private void writeTable(Table table, File f) throws OfxAuthoringException, IOException
	{
		LatexTableRenderer tableRenderer = new LatexTableRenderer(false);
		tableRenderer.render(table);
		
		StringWriter sw = new StringWriter();
		tableRenderer.write(sw);
		StringIO.writeTxt(f, sw.toString());
	}
}