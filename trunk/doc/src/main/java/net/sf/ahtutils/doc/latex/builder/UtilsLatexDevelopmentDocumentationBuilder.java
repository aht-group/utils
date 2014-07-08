package net.sf.ahtutils.doc.latex.builder;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsLatexDevelopmentDocumentationBuilder extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(UtilsLatexDevelopmentDocumentationBuilder.class);
					
	public static final String cfgKeyErSvg = "doc.image.admin.development.er";
	
	
	public static enum ErCode {erIntroduction}
	public static enum EclipseClassifier {luna,svn,git}
	public static enum Code {latex}
		
	public UtilsLatexDevelopmentDocumentationBuilder(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm)
	{
		super(config,translations,langs,cmm);
	}
	
	@Override protected void applyBaseLatexDir()
	{
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
	}
	
	@Override protected void applyConfigCodes()
	{	
		addConfig(ErCode.erIntroduction.toString(),"ofx.aht-utils/development/er.xml","admin/development/er");
		addConfig("eclipse","ofx.aht-utils/development/environment/eclipse.xml","admin/development/environment/eclipse");
		
		addConfig(Code.latex.toString(),"ofx.aht-utils/development/environment/latex.xml","admin/development/environment/latex");
	}

	public void render(Code code) throws UtilsConfigurationException{render(2,code);}
	public void render(int lvl, Code code) throws UtilsConfigurationException{render(lvl,code.toString());}
	
	public void render(ErCode code) throws UtilsConfigurationException{render(code.toString());}
	
	public void renderEclipse(EclipseClassifier... versions) throws UtilsConfigurationException
	{
		String[] classifier = new String[versions.length];
		for(int i=0;i<versions.length;i++){classifier[i]=versions[i].toString();}
		render(2,"eclipse",classifier);
	}
	
	protected void applySectionSettings(String code, Section section)
	{
		logger.info("TEST "+code);
		for(Object s : section.getContent())
		{
			if (s instanceof Image)
			{
				Image image = (Image)s;
				if(image.getId().equals("image.admin.development.er"))
				{
					image.getMedia().setSrc(config.getString(cfgKeyErSvg));
				}
			}
		}
	}
}