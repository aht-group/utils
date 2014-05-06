package net.sf.ahtutils.doc.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Map;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.interfaces.CrossMediaManager;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.util.filter.OfxClassifierFilter;
import org.openfuxml.util.filter.OfxLangFilter;
import org.openfuxml.util.media.MediaSourceModificationTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractLatexDocumentationBuilder.class);
	
	protected Configuration config;
	protected Map<String,String> dstFiles;
	
	protected String baseLatexDir;
	protected String[] langs;
	
	protected CrossMediaManager cmm;
	
	public AbstractLatexDocumentationBuilder(Configuration config, String[] langs, MediaSourceModificationTracker msmt)
	{
		this.config=config;
		this.langs=langs;
		
		dstFiles = new Hashtable<String,String>();
		
		applyConfigCodes();
		applyBaseLatexDir();
		
		logger.info("Using baseLatexDir: "+baseLatexDir);
		
		cmm = new LatexCrossMediaManager(new File(baseLatexDir),config.getString(LatexCrossMediaManager.keyOfxLatexImageDir),msmt);
		
	}
	
	protected void applyBaseLatexDir()
	{
		logger.error("Method applyBaseLatexDir() needs to be @Override");
	}
	
	protected void applyConfigCodes()
	{
		logger.error("Method applyConfigCodes() needs to be @Override");
	}
	
	public void crossMediaTranscode() throws OfxAuthoringException
	{
		cmm.transcode();
	}
	
	protected void addConfig(String code, String source, String destination)
	{
		config.addProperty(code, source);
		dstFiles.put(code, destination);
	}
	
	protected void render(String code) throws UtilsConfigurationException{render(code,null);}
	protected void render(String code,String classifier[]) throws UtilsConfigurationException
	{
		try
		{
			renderSection(code,classifier);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	protected void renderSection(String code, String classifier[]) throws OfxAuthoringException, IOException
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
		
		for(String lang : langs)
		{
			OfxLangFilter omf = new OfxLangFilter(lang);
			Section sectionlang = omf.filterLang(section);
			File f = new File(baseLatexDir,lang+"/"+"section"+"/"+dstFiles.get(code)+".tex");
			LatexSectionRenderer renderer = new LatexSectionRenderer(cmm,1,new LatexPreamble());
			renderer.render(sectionlang);
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
		}
	}
	
}