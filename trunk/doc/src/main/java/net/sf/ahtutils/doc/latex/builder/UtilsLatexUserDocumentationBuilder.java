package net.sf.ahtutils.doc.latex.builder;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexUserDocumentationBuilder extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexUserDocumentationBuilder.class);
					
	public static final String cfgKeyErSvg = "doc.image.admin.development.er";
	
	
	public static enum Code {uiIcons,uiFileExport}
		
	public UtilsLatexUserDocumentationBuilder(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm)
	{
		super(config,translations,langs,cmm);
	}
	
	@Override protected void applyBaseLatexDir()
	{
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
	}
	
	@Override protected void applyConfigCodes()
	{		
		addConfig(Code.uiIcons.toString(),"ofx.aht-utils/user/ui/icons.xml","user/ui/icons");
		addConfig(Code.uiFileExport.toString(),"ofx.aht-utils/user/ui/export/file.xml","user/ui/fileExport");
	}

	public void render(Code code) throws UtilsConfigurationException{render(1,code);}
	public void render(int lvl, Code code) throws UtilsConfigurationException{render(lvl,code.toString());}
}