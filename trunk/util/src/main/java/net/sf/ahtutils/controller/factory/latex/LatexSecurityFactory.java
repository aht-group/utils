package net.sf.ahtutils.controller.factory.latex;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.factory.ofx.security.OfxCategoryListFactory;
import net.sf.ahtutils.controller.factory.ofx.security.OfxRoleTableFactory;
import net.sf.ahtutils.controller.factory.ofx.security.OfxViewTableFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSecurityFactory
{	
	final static Logger logger = LoggerFactory.getLogger(LatexSecurityFactory.class);
	
	private final static String dirViewTabs = "tab/security";
	private final static String dirRoleTabs = "tab/security";
	private final static String dirCategoryDescriptions = "description/security";
	
	private String baseLatexDir;
	
	private Translations translations;
	private String[] headerKeys = {"viewTableHeaderViewId","viewTableHeaderName"};
	private String[] langs;
	
	public LatexSecurityFactory(Translations translations,String baseLatexDir,String[] langs)
	{
		this.translations=translations;
		this.baseLatexDir=baseLatexDir;
		this.langs=langs;
	}
	
	public void createViewTabs(String xmlFile) throws UtilsConfigurationException
	{
		logger.info("Creating view tables from "+xmlFile+" to LaTex");
		
		try
		{
			Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
			for(Category category : access.getCategory())
			{
				for(String lang : langs)
				{
					File f = new File(baseLatexDir,lang+"/"+dirViewTabs+"/views-"+category.getCode()+".tex");
					OfxViewTableFactory fOfx = new OfxViewTableFactory("de",translations);
					fOfx.saveDescription(f, category.getViews().getView(),headerKeys);
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}

	public void createRoleTabs(String xmlFile) throws UtilsConfigurationException
	{
		logger.info("Creating view tables from "+xmlFile+" to LaTex");
		
		try
		{
			Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
			for(Category category : access.getCategory())
			{
				for(String lang : langs)
				{
					File f = new File(baseLatexDir,lang+"/"+dirRoleTabs+"/role-"+category.getCode()+".tex");
					OfxRoleTableFactory fOfx = new OfxRoleTableFactory("de",translations);
					fOfx.saveDescription(f, category.getRoles().getRole(),headerKeys);
				}
			}
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void createCategoryDescriptions(String xmlFile, String extractId) throws UtilsConfigurationException
	{
		logger.info("Creating descriptions from "+xmlFile+" to LaTex");
		for(String lang : langs){saveCategoryDescriptions(lang,xmlFile,extractId);}
	}
	private void saveCategoryDescriptions(String lang, String xmlFile, String extractId) throws UtilsConfigurationException
	{
		File f = new File(baseLatexDir,lang+"/"+dirCategoryDescriptions+"/"+extractId+".tex");
		try
		{
			logger.debug("Converting "+xmlFile+" to LaTex ("+f.getAbsolutePath());
			Access access = JaxbUtil.loadJAXB(xmlFile, Access.class);
			OfxCategoryListFactory latexFactory = new OfxCategoryListFactory("de");
			latexFactory.saveDescription(f, access.getCategory());
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}