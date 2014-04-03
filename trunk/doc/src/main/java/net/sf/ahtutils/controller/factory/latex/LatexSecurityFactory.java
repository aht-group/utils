package net.sf.ahtutils.controller.factory.latex;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.doc.security.OfxCategoryListFactory;
import net.sf.ahtutils.doc.security.OfxRoleTableFactory;
import net.sf.ahtutils.doc.security.OfxViewTableFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSecurityFactory
{	
	final static Logger logger = LoggerFactory.getLogger(LatexSecurityFactory.class);
	
	private final static String dirViewTabs = "tab/security";
	private final static String dirRoleTabs = "tab/security";
	private final static String dirCategoryDescriptions = "description/security";
	
	private Configuration config;
	
	private String baseLatexDir;
	
	private Translations translations;
	private String[] headerKeys = {"tableHeaderName","tableHeaderDescription"};
	
	private String[] langs;
	
	public LatexSecurityFactory(Configuration config, Translations translations,String baseLatexDir,String[] langs)
	{
		this.config=config;
		this.translations=translations;
		this.baseLatexDir=baseLatexDir;
		this.langs=langs;
	}
	
	public void createViewTabs(String xmlFile) throws UtilsConfigurationException
	{
		logger.info("Creating view tables from "+xmlFile+" to LaTex");
		String[] headerKeysViews = {"auSecurityTableViewName","auSecurityTableViewDescription"};
		try
		{
			Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
			for(Category category : access.getCategory())
			{
				for(String lang : langs)
				{
					File f = new File(baseLatexDir,lang+"/"+dirViewTabs+"/views-"+category.getCode()+".tex");
					OfxViewTableFactory fOfx = new OfxViewTableFactory(config,lang,translations);
					String content = fOfx.buildLatexViewTable(category,headerKeysViews);
					StringIO.writeTxt(f, content);
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}

	public void createRoleTabs(String xmlFile) throws UtilsConfigurationException
	{
		logger.info("Creating view tables from "+xmlFile+" to LaTex");
		String[] headerKeysRoles = {"auSecurityTableRoleName","auSecurityTableRoleDescription"};
		try
		{
			Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
			for(Category category : access.getCategory())
			{
				for(String lang : langs)
				{
					File f = new File(baseLatexDir,lang+"/"+dirRoleTabs+"/role-"+category.getCode()+".tex");
					OfxRoleTableFactory fOfx = new OfxRoleTableFactory(config,lang,translations);
					String content = fOfx.saveDescription(category,headerKeysRoles);
					StringIO.writeTxt(f, content);
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void createCategoryDescriptions(String xmlFile, String extractId) throws UtilsConfigurationException
	{
		logger.info("Creating descriptions from "+xmlFile+" to LaTex");
		for(String lang : langs)
		{
			File f = new File(baseLatexDir,lang+"/"+dirCategoryDescriptions+"/"+extractId+".tex");
			
			try
			{
				logger.debug("Converting "+xmlFile+" to LaTex ("+f.getAbsolutePath());
				Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
				OfxCategoryListFactory latexFactory = new OfxCategoryListFactory(config,lang,translations);
				String content = latexFactory.saveDescription(access.getCategory());
				StringIO.writeTxt(f, content);
			}
			catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
			catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		}
	}
}