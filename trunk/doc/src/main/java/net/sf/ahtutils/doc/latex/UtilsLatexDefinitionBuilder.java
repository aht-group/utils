package net.sf.ahtutils.doc.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexDefinitionBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexDefinitionBuilder.class);
		
	private MultiResourceLoader mrl;
	private File baseLatex;
	
	public UtilsLatexDefinitionBuilder(Configuration config)
	{
		mrl = new MultiResourceLoader();
		baseLatex = new File(config.getString(UtilsDocumentation.keyBaseDocDir));
	}
	
	public void copyPackages() throws UtilsConfigurationException
	{
		try
		{
			File fTarget = new File(baseLatex,"tex"+File.separator+"packages.tex");
			InputStream is = mrl.searchIs("tex.aht-utils/packages.tex");
			byte[] bytes = IOUtils.toByteArray(is);
			FileIO.writeFileIfDiffers(bytes, fTarget);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void copyColors() throws UtilsConfigurationException
	{
		try
		{
			File fTarget = new File(baseLatex,"tex"+File.separator+"colors.tex");
			InputStream is = mrl.searchIs("tex.aht-utils/colors.tex");
			byte[] bytes = IOUtils.toByteArray(is);
			FileIO.writeFileIfDiffers(bytes, fTarget);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}