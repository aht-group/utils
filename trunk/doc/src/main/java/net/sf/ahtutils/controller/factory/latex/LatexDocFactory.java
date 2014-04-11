package net.sf.ahtutils.controller.factory.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.util.filter.OfxClassifierFilter;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexDocFactory
{	
	final static Logger logger = LoggerFactory.getLogger(LatexDocFactory.class);
	
	private static enum Code {accessIntroduction,
								
							systemLogIntroduction,
							 };
							 
	public static enum InstallationCode {instDebian,instJava,instJboss,instPostGis,instMaven}
	
	public static enum InstallationArchitecture {debianWheezy,debianSqueeze,debianRaspberry}
	public static enum JBossVersion {as7,eap6}
	
	private final static String dirTexts = "txt";
	
	private Configuration config;
	private Map<String,String> dstFiles;
	
	private String baseLatexDir;
	private String[] langs;
	
	public LatexDocFactory(Configuration config, String[] langs)
	{
		this.config=config;
		this.langs=langs;
		baseLatexDir=config.getString(UtilsDocumentation.keyBaseDocDir);
		dstFiles = new Hashtable<String,String>();
		applyConfigCodes();
	}
	
	public void buildDoc() throws UtilsConfigurationException
	{
		logger.info("buildDoc");
		render(Code.accessIntroduction.toString());
		render(Code.systemLogIntroduction.toString());
	}

	public void render(InstallationCode code) throws UtilsConfigurationException{render(code.toString());}
	
	public void render(InstallationCode code, InstallationArchitecture... architectures) throws UtilsConfigurationException
	{
		String[] classifier = new String[architectures.length];
		for(int i=0;i<architectures.length;i++){classifier[i]=architectures[i].toString();}
		render(code.toString(),classifier);
	}
	public void render(InstallationCode code, JBossVersion... versions) throws UtilsConfigurationException
	{
		String[] classifier = new String[versions.length];
		for(int i=0;i<versions.length;i++){classifier[i]=versions[i].toString();}
		render(code.toString(),classifier);
	}
	
	private void render(String code) throws UtilsConfigurationException{render(code,null);}
	private void render(String code,String classifier[]) throws UtilsConfigurationException
	{
		try
		{
			renderSection(code,classifier);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	private void renderSection(String code, String classifier[]) throws OfxAuthoringException, IOException
	{
		logger.trace("Rendering "+Section.class.getSimpleName()+": "+code);
		Section section = JaxbUtil.loadJAXB(config.getString(code), Section.class);

		Comment comment = XmlCommentFactory.build();
		DocumentationCommentBuilder.configKeyReference(comment, config, code, "Source file");
		
		
		if(classifier!=null && classifier.length>0)
		{
			OfxClassifierFilter mlf = new OfxClassifierFilter(classifier);
			section = mlf.filterLang(section);
			DocumentationCommentBuilder.ofxClassifier(comment,classifier);
		}
		
		DocumentationCommentBuilder.doNotModify(comment);
		section.getContent().add(comment);
		
//		try
		{
			for(String lang : langs)
			{
				
				OfxLangFilter omf = new OfxLangFilter(lang);
				Section sectionlang = omf.filterLang(section);
				File f = new File(baseLatexDir,lang+"/"+dirTexts+"/"+dstFiles.get(code)+".tex");
				LatexSectionRenderer renderer = new LatexSectionRenderer(1,new LatexPreamble());
				renderer.render(sectionlang);
				StringWriter actual = new StringWriter();
				renderer.write(actual);
				StringIO.writeTxt(f, actual.toString());
			}
		}
//		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	private void applyConfigCodes()
	{
		addConfig(Code.accessIntroduction.toString(),"ofx.aht-utils/administration/access/introduction.xml","admin/access/introduction");
		addConfig(Code.systemLogIntroduction.toString(),"ofx.aht-utils/administration/logging/introduction.xml","admin/system/logging/introduction");
		
		//Installation
		addConfig(InstallationCode.instDebian.toString(),"ofx.aht-utils/installation/debian.xml","admin/installation/debian");
		addConfig(InstallationCode.instJava.toString(),"ofx.aht-utils/installation/java.xml","admin/installation/java");
		addConfig(InstallationCode.instJboss.toString(),"ofx.aht-utils/installation/jboss.xml","admin/installation/jboss");
		addConfig(InstallationCode.instPostGis.toString(),"ofx.aht-utils/installation/postgres.xml","admin/installation/postgres");
		addConfig(InstallationCode.instMaven.toString(),"ofx.aht-utils/installation/maven.xml","admin/installation/maven");
	}
	
	private void addConfig(String code, String source, String destination)
	{
		config.addProperty(code, source);
		dstFiles.put(code, destination);
	}
	
}