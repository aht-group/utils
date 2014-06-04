package net.sf.ahtutils.doc.latex.writer;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.doc.ofx.security.OfxCategoryListFactory;
import net.sf.ahtutils.doc.ofx.security.OfxRoleTableFactory;
import net.sf.ahtutils.doc.ofx.security.OfxViewTableFactory;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.CrossMediaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSecurityWriter extends AbstractDocumentationLatexWriter
{	
	final static Logger logger = LoggerFactory.getLogger(LatexSecurityWriter.class);
	
	private final static String dirTabs = "tab/security";
	private final static String dirDescriptions = "description/security";
		
	public LatexSecurityWriter(Configuration config, Translations translations,String[] langs, CrossMediaManager cmm)
	{
		super(config,translations,langs,cmm);
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
					File f = new File(baseLatexDir,lang+"/"+dirTabs+"/views-"+category.getCode()+".tex");
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
					File f = new File(baseLatexDir,lang+"/"+dirTabs+"/role-"+category.getCode()+".tex");
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
			File f = new File(baseLatexDir,lang+"/"+dirDescriptions+"/"+extractId+".tex");
			
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