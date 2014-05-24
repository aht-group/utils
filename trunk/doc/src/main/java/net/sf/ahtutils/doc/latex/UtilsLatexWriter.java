package net.sf.ahtutils.doc.latex;

import java.io.IOException;
import java.io.StringWriter;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.util.media.MediaSourceModificationTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexWriter extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexWriter.class);
		
	public UtilsLatexWriter(Configuration config, String[] langs,MediaSourceModificationTracker msmt)
	{
		super(config,langs,msmt);
	}
	
	private String renderTable(Table table) throws OfxAuthoringException, IOException
	{
		LatexTableRenderer tableRenderer = new LatexTableRenderer(false);
		tableRenderer.render(table);
		
		StringWriter sw = new StringWriter();
		tableRenderer.write(sw);
		return sw.toString();
	}
}