package net.sf.ahtutils.doc.latex;

import net.sf.ahtutils.doc.UtilsDocumentation;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.latex.util.OfxLatexDefinitionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexDefinitionBuilder extends OfxLatexDefinitionBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexDefinitionBuilder.class);
		
	public static enum Code {colors}
	
	public UtilsLatexDefinitionBuilder(Configuration config)
	{
		super(config.getString(UtilsDocumentation.keyBaseLatexDir));
	}
	
	public void copyColors() throws OfxConfigurationException {copyResource("tex.aht-utils","colors");}
}