package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.List;

import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuFactory
{
	final static Logger logger = LoggerFactory.getLogger(MenuFactory.class);
	
	private String lang;
	
	public MenuFactory(String lang)
	{
		this.lang=lang;
	}
	
	public Menu create(Menu menu)
	{
		Menu result = new Menu();
		
		result.getMenuItem().addAll(processChilds(menu.getMenuItem()));
		
		return result;
	}
	
	private List<MenuItem> processChilds(List<MenuItem> origs)
	{
		List<MenuItem> result = new ArrayList<MenuItem>();
		
		for(MenuItem mi :origs)
		{
			result.add(processItem(mi));
		}
		
		return result;
	}
	
	private MenuItem processItem(MenuItem miOrig)
	{
		MenuItem mi = new MenuItem();
		
		String name;
		if(miOrig.isSetLangs()){name = getNameFromLangs(miOrig.getLangs());}
		else if(miOrig.isSetView()){name = getNameFromViews(miOrig.getView());}
		else
		{
			logger.warn("Translation missing!!");
			name = "Translation missing";
		}
		mi.setName(name);			
		
		mi.getMenuItem().addAll(processChilds(miOrig.getMenuItem()));
		return mi;
	}
	
	private String getNameFromLangs(Langs langs)
	{
		String name = null;
		if(langs!=null)
		{
			try
			{
				Lang actual = StatusXpath.getLang(langs, lang);
				name = actual.getTranslation();
			}
			catch (ExlpXpathNotFoundException e) {}
			catch (ExlpXpathNotUniqueException e) {}
		}
		if(name==null){name = "No Translation found";}
		return name;
	}
	
	private String getNameFromViews(View view)
	{
		String result = "TBD";
		return result;
	}
}
