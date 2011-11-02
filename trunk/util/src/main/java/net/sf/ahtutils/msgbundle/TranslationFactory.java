package net.sf.ahtutils.msgbundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
	public static final String msgBundleSuffix = "properties";
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	private TranslationMap tMap;

	private String inEncoding,outEncoding;
	private List<String> outFiles;

	private MultiResourceLoader mrl;

	public TranslationFactory()
	{
		mrl = new MultiResourceLoader();
		tMap = new TranslationMap();
		inEncoding = "UTF-8";
		outEncoding = "UTF-8";
		outFiles = new ArrayList<String>();
	}
	
	public void writeMessageResourceBundles(String bundleName, String bundlePackage, String targetDirectory) throws FileNotFoundException, AhtUtilsNotFoundException
	{
		File baseDir = new File(targetDirectory);
		if(!baseDir.exists() || !baseDir.isDirectory())
		{
			throw new FileNotFoundException("Directory "+baseDir.getAbsolutePath()+" does not exist!");
		}
		
		bundlePackage=bundlePackage.replaceAll("\\.", "/");
		File bundleDir = new File(baseDir,bundlePackage);
		if(!bundleDir.exists())
		{
			bundleDir.mkdirs();
		}
		
		for(String langKey : tMap.getLangKeys())
		{
			logger.info("Processing "+langKey);
			String fName = bundleName+"_"+langKey+"."+msgBundleSuffix;
			outFiles.add(targetDirectory+"/"+fName);
			
			
/*			Map<String,String> mapLang = mapTranslations.get(s);
			for(String key : mapLang.keySet())
			{
				pw.println(key+"="+mapLang.get(key));
			}
*/			
			
			try
			{
				File f = new File(bundleDir,fName);
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

		logger.info("Loaded "+translations.getTranslation().size()+" Elements from "+xmlFile);
			
		for(Translation translation : translations.getTranslation())
		{
			for(Lang lang : translation.getLangs().getLang())
			{
				tMap.add(lang.getKey(), translation.getKey(), lang.getTranslation());
			}
		}
	}
	
	public List<String> getStats()
	{
		List<String> result = new ArrayList<String>();
		result.add("Created Message Bundle (output Ecoding: "+outEncoding+")");
		for(String langKey : tMap.getLangKeys())
		{
			StringBuffer sb = new StringBuffer();
			sb.append("    ").append(langKey).append(": ");
			int number = 0;
			try {number = tMap.getTranslationKeys(langKey).size();}
			catch (AhtUtilsNotFoundException e) {logger.error(e);}
			sb.append(number).append(" translations");
			result.add(sb.toString());
		}
		return result;
	}
	
	public void debug()
	{
		for(String s : getStats())
		{
			logger.debug(s);
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
	
	protected TranslationMap gettMap()
	{
		return tMap;
	}
}