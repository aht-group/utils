package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.CrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractDocumentationLatexWriter.class);
	
	protected Configuration config;
	protected String baseLatexDir;
	
	protected Translations translations;
	protected String[] langs;
	
	protected CrossMediaManager cmm;
	
	public AbstractDocumentationLatexWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm)
	{
		this.config=config;
		this.translations=translations;
		this.langs=langs;
		this.cmm=cmm;
		
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
	}
	
	protected void writeTable(Table table, File f) throws OfxAuthoringException, IOException
	{
		LatexTableRenderer tableRenderer = new LatexTableRenderer(cmm);
		tableRenderer.setPreBlankLine(false);
		tableRenderer.render(table);
		
		StringWriter sw = new StringWriter();
		tableRenderer.write(sw);
		StringIO.writeTxt(f, sw.toString());
	}
	
	protected void writeSection(Section section, File f) throws OfxAuthoringException, IOException {writeSection(1, section, f);}
	protected void writeSection(int sectionLevel, Section section, File f) throws OfxAuthoringException, IOException
	{
		LatexSectionRenderer sectionRenderer = new LatexSectionRenderer(cmm,sectionLevel,new LatexPreamble());
		sectionRenderer.render(section);
		
		StringWriter sw = new StringWriter();
		sectionRenderer.write(sw);
		StringIO.writeTxt(f, sw.toString());
	}
}