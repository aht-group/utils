package net.sf.ahtutils.msgbundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import net.sf.ahtutils.AhtUtilsBootstrap;
import net.sf.ahtutils.controller.exception.AhtUtilsNotFoundException;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translation;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TranslationFactory
{
	static Log logger = LogFactory.getLog(TranslationFactory.class);
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	private TranslationMap tMap;

	private String inEncoding,outEncoding;
	private List<String> outFiles;
	private boolean useLog4j;

	private MultiResourceLoader mrl;

	public TranslationFactory(boolean useLog4j)
	{
		this.useLog4j=useLog4j;
		mrl = new MultiResourceLoader();
		tMap = new TranslationMap();
		inEncoding = "UTF-8";
		outEncoding = "ISO-8859-1";
		outFiles = new ArrayList<String>();
	}
	
	public void writeMessageResourceBundles(String name, String directory) throws FileNotFoundException, AhtUtilsNotFoundException
	{
		File baseDir = new File(directory);
		if(!baseDir.exists() || !baseDir.isDirectory())
		{
			throw new FileNotFoundException("Directory "+baseDir.getAbsolutePath()+" does not exist!");
		}
		for(String langKey : tMap.getLangKeys())
		{
			logger.info("Processing "+langKey);
			String fName = name+"_"+langKey+".txt";
			outFiles.add(directory+"/"+fName);
			
			
/*			Map<String,String> mapLang = mapTranslations.get(s);
			for(String key : mapLang.keySet())
			{
				pw.println(key+"="+mapLang.get(key));
			}
*/			
			
			try
			{
				File f = new File(baseDir,fName);
				OutputStream os = new FileOutputStream(f);
				OutputStreamWriter osw = new OutputStreamWriter(os, outEncoding);
				PrintWriter pw = new PrintWriter(osw, true); 
				
				for(String key : tMap.getTranslationKeys(langKey))
				{
					pw.println(key+"="+tMap.translate(langKey, key));
				}
				
				pw.close();osw.close();os.close();
			}
			catch (IOException e) {e.printStackTrace();}
		}
		
/*		for(String s : mapTranslations.keySet())
		{
			try
			{
				
				
				File f = new File(baseDir,fName);
				OutputStream os = new FileOutputStream(f);
				OutputStreamWriter osw = new OutputStreamWriter(os, outEncoding);
				PrintWriter pw = new PrintWriter(osw, true); 
				
				Map<String,String> mapLang = mapTranslations.get(s);
				for(String key : mapLang.keySet())
				{
					pw.println(key+"="+mapLang.get(key));
				}
				
				pw.close();osw.close();os.close();
			}
			catch (UnsupportedEncodingException e) {logger.error(e);}
			catch (IOException e) {logger.error(e);}
		}
*/	}
	
	public void add(String xmlFile) throws FileNotFoundException
	{
		Translations translations = (Translations)JaxbUtil.loadJAXB(xmlFile, Translations.class);

		String msg = "Loaded "+translations.getTranslation().size()+" Elements from "+xmlFile;
		if(useLog4j){logger.info(msg);}
		else{System.out.println(msg);}
			
		for(Translation translation : translations.getTranslation())
		{
			for(Lang lang : translation.getLangs().getLang())
			{
				tMap.add(lang.getKey(), translation.getKey(), lang.getTranslation());
			}
		}
	}
	
	public void debug()
	{
//		for(String s : getDebugMsg(true))
		{
//			logger.debug(s);
		}
	}
	
/*	public List<String> getDebugMsg(boolean debug)
	{
		List<String> result = new ArrayList<String>();
		result.add("Encoding: in="+inEncoding+" out="+outEncoding);
		for(String langKey : mapTranslations.keySet())
		{
			StringBuffer sb = new StringBuffer();
			sb.append("Keys for language "+langKey+": ");
			Map<String,String> trans = mapTranslations.get(langKey);
			sb.append(trans.keySet().size());
			result.add(sb.toString());
			if(debug)
			{
				for(String sub : trans.keySet())
				{
					result.add("  "+sub+": "+trans.get(sub));
				}
			}
		}
		for(String s : outFiles){result.add("Written: "+s);}
		return result;
	}
*/	
	public void setInEncoding(String inEncoding) {this.inEncoding = inEncoding;}
	public void setOutEncoding(String outEncoding) {this.outEncoding = outEncoding;}
	
	public static void main (String[] args) throws Exception
	{
		AhtUtilsBootstrap.init();
		
		TranslationFactory tFactory = new TranslationFactory(true);
		tFactory.setInEncoding("UTF-8");
		tFactory.setOutEncoding("UTF-8");
		tFactory.add("src/test/resources/data/xml/msgBundle/translation1.xml");
		tFactory.writeMessageResourceBundles("msg","target");
		tFactory.debug();
	}
}