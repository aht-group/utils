package net.sf.ahtutils.controller.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import net.sf.ahtutils.controller.doc.installation.BuildDocR;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.openfuxml.util.OfxMultilangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractDocumentationFactory 
{
	final static Logger logger = LoggerFactory.getLogger(BuildDocR.class);
	
	public static String modifyWarning = "Do no modify this file, it is automatically generated!";

	protected OfxMultilangFilter multiLangFilter;
	
	public AbstractDocumentationFactory(String lang)
	{
		multiLangFilter = new OfxMultilangFilter(lang);
	}
	
	protected File getFile(String name) throws FileNotFoundException
	{
		File f = new File(name);
		
		if(!f.getParentFile().exists()){throw new FileNotFoundException("Directory does not exist: "+f.getParentFile().getAbsolutePath());}
		
		return f;
	}

	protected void write(List<String> content, File f)
	{
		logger.info("Writing to "+f.getAbsolutePath());
		ExlpTxtWriter writer = new ExlpTxtWriter();
		writer.add(content);
		writer.writeFile(f);
	}
}