package net.sf.ahtutils.controller.factory.latex;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.controller.factory.ofx.status.OfxStatusTableFactory;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexStatusFactory
{	
	final static Logger logger = LoggerFactory.getLogger(LatexStatusFactory.class);
	
	private final static String dirStatus = "tab/status";
	
	private String baseLatexDir;
	
	private Translations translations;
	private String[] headerKeys = {"langStatusTableHeaderCode","langStatusTableHeaderLang","langStatusTableHeaderDescription"};
	private String[] langs;
	
	public LatexStatusFactory(Translations translations,String baseLatexDir,String[] langs)
	{
		this.translations=translations;
		this.baseLatexDir=baseLatexDir;
		this.langs=langs;
	}
	
	public void statusTable(String statusInput, String texName) throws AhtUtilsConfigurationException
	{
		try
		{
			Aht aht = JaxbUtil.loadJAXB(statusInput, Aht.class);
			OfxStatusTableFactory fOfx = new OfxStatusTableFactory(aht.getStatus(),headerKeys,translations);
			for(String lang : langs)
			{
				File f = new File(baseLatexDir+"/"+lang+"/"+dirStatus+"/"+texName);
				fOfx.saveTable(f, lang);
			}
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
}