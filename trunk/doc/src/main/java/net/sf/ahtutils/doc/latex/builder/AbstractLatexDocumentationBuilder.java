package net.sf.ahtutils.doc.latex.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.latex.writer.AbstractDocumentationLatexWriter;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.util.filter.OfxClassifierFilter;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractLatexDocumentationBuilder extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractLatexDocumentationBuilder.class);
	
	protected Map<String,String> dstFiles;

	public AbstractLatexDocumentationBuilder(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm)
	{
		super(config,translations,langs,cmm);
		this.config=config;
		this.langs=langs;
		
		dstFiles = new Hashtable<String,String>();
		
		applyConfigCodes();
		applyBaseLatexDir();
		
		logger.info("Using baseLatexDir: "+baseLatexDir);		
	}
	
	protected void applyBaseLatexDir()
	{
		logger.error("Method applyBaseLatexDir() needs to be @Override");
	}
	
	protected void applyConfigCodes()
	{
		logger.error("Method applyConfigCodes() needs to be @Override");
	}
	
	protected void addConfig(String code, String source, String destination)
	{
		config.addProperty(code, source);
		dstFiles.put(code, destination);
	}
	
	protected void render(String code) throws UtilsConfigurationException{render(1,code);}
	protected void render(int lvl, String code) throws UtilsConfigurationException{render(lvl,code,null);}
	protected void render(String code,String classifier[]) throws UtilsConfigurationException{render(1,code,classifier);}
	protected void render(int lvl, String code,String classifier[]) throws UtilsConfigurationException
	{
		try
		{
			renderSection(lvl,code,classifier);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	protected void renderSection(int lvl,String code, String classifier[]) throws OfxAuthoringException, IOException
	{
		logger.trace("Rendering "+Section.class.getSimpleName()+": "+code);
		Section section = JaxbUtil.loadJAXB(config.getString(code), Section.class);
		JaxbUtil.trace(section);
		applySectionSettings(code, section);
		
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
		
		for(String lang : langs)
		{
			OfxLangFilter omf = new OfxLangFilter(lang);
			Section sectionlang = omf.filterLang(section);
			
			File f = new File(baseLatexDir,lang+"/"+"section"+"/"+dstFiles.get(code)+".tex");
			this.writeSection(lvl,sectionlang, f);
		}
	}
	
	protected void applySectionSettings(String code, Section section) {}
}