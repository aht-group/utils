package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.xpath.AccessXpath;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuFactory
{
	final static Logger logger = LoggerFactory.getLogger(MenuFactory.class);
	
	private String lang;
	private Access access;
	private Map<String,Boolean> mapViewAllowed;
	
	public MenuFactory(Access access, Map<String,Boolean> mapViewAllowed, String lang)
	{
		this.access=access;
		this.mapViewAllowed=mapViewAllowed;
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
			if(mi.isSetView())
			{
				if(mapViewAllowed.containsKey(mi.getView().getCode()) && mapViewAllowed.get(mi.getView().getCode()))
				{
					result.add(processItem(mi));
				}
			}
			else
			{
				result.add(processItem(mi));
			}
		}
		
		return result;
	}
	
	private MenuItem processItem(MenuItem miOrig)
	{
		MenuItem mi = new MenuItem();
		
		if(miOrig.isSetLangs())
		{
			mi.setName(getNameFromLangs(miOrig.getLangs()));
		}
		else if(miOrig.isSetView())
		{
			mi.setName(getNameFromViews(miOrig.getView()));
		}
		else
		{
			logger.warn("Translation missing!!");
			mi.setName("Translation missing");	
		}
		if(miOrig.isSetHref()) {mi.setHref(miOrig.getHref());}
		else if(miOrig.isSetView()) {mi.setHref(getHrefFromViews(miOrig.getView()));}
		else {mi.setHref("#");}
		
		
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
	
	private String getNameFromViews(View viewCode)
	{
		Langs langs=null;
		try
		{
			View view = AccessXpath.getView(access, viewCode.getCode());
			langs = view.getLangs();
		}
		catch (ExlpXpathNotFoundException e) {}
		catch (ExlpXpathNotUniqueException e) {}
		return getNameFromLangs(langs);
	}
	
	private String getHrefFromViews(View viewCode)
	{
		try
		{
			View view = AccessXpath.getView(access, viewCode.getCode());
			if(view.isSetNavigation() && view.getNavigation().isSetUrlMapping())
			{
				
				return view.getNavigation().getUrlMapping().getValue();
			}
		}
		catch (ExlpXpathNotFoundException e) {}
		catch (ExlpXpathNotUniqueException e) {}
		return "#";
	}
}
