package net.sf.ahtutils.controller.factory.latex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.controller.exception.AhtUtilsConfigurationException;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.dir.DirChecker;
import net.sf.exlp.util.io.dir.RecursiveFileFinder;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexStatusFactory
{	
	final static Logger logger = LoggerFactory.getLogger(LatexStatusFactory.class);
	
	private Namespace nsLangs = Namespace.getNamespace("s", "http://ahtutils.aht-group.com/status");
	
	private final static String dirViewTabs = "tab/security";
	
	private String baseLatexDir;
	
	private Translations translations;
	private String[] headerKeys = {"viewTableHeaderViewId","viewTableHeaderName"};
	private String[] langs;
	
	public LatexStatusFactory(Translations translations,String baseLatexDir,String[] langs)
	{
		this.translations=translations;
		this.baseLatexDir=baseLatexDir;
		this.langs=langs;
	}
	
	public void langStatistic(String searchDir) throws AhtUtilsConfigurationException
	{
		try
		{
			File fDir = new File(searchDir);
			DirChecker.checkFileIsDirectory(fDir);
			logger.info("Creating statistic for baseDir "+fDir.getAbsolutePath());
			
			RecursiveFileFinder finder = new RecursiveFileFinder(FileFilterUtils.suffixFileFilter(".xml"));
	    	List<File> list = finder.find(fDir);
	    	logger.info("Found "+list.size()+" potential files");
	    	for(File f : list){process(f);}
		}
		catch (ExlpConfigurationException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new AhtUtilsConfigurationException(e.getMessage());}
	}
	
	private void process(File f)
	{
		logger.debug("Processing "+f);
	}

	protected boolean hasLangs(Document doc)
	{
		if(getLangsElements(doc).size()>0){return true;}
		return false;
	}
	
	protected List<Langs> getLangs(Document doc)
	{
		List<Langs> langs = new ArrayList<Langs>();
		for(Element e : getLangsElements(doc))
		{
			langs.add(JDomUtil.toJaxb(e, Langs.class));
		}
		return langs;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Element> getLangsElements(Document doc)
	{
		List<Element> result = new ArrayList<Element>();
		try
		{
			XPath xpath = XPath.newInstance("//s:langs");
			xpath.addNamespace(nsLangs);
			result = xpath.selectNodes(doc);
		}
		catch (JDOMException e) {logger.error("Exception while counting for langs",e);}
		return result;
	}
}