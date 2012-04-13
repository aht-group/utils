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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuFactory
{
	final static Logger logger = LoggerFactory.getLogger(MenuFactory.class);
	
	private String lang;
	private String contextRoot;
	
	public void setContextRoot(String contextRoot) {this.contextRoot = contextRoot;}

	private Access access;
	private boolean noRestrictions;
	private Map<String,Boolean> mapViewAllowed;
	
	public MenuFactory(Access access, Map<String,Boolean> mapViewAllowed, String lang)
	{
		this.access=access;
		this.mapViewAllowed=mapViewAllowed;
		this.lang=lang;
		noRestrictions=false;
	}
	
	public MenuFactory(Access access, String lang)
	{
		this.access=access;
		this.lang=lang;
		noRestrictions=true;
	}
	
	public MenuFactory(String lang)
	{
		this.lang=lang;
		noRestrictions=true;
	}
	
	public Menu create(Menu menu, String codeCurrent)
	{
		Menu result = new Menu();
		
		try {result.getMenuItem().addAll(processChilds(menu.getMenuItem(),codeCurrent));}
		catch (ExlpXpathNotFoundException e) {logger.warn(e.getMessage());}
		catch (ExlpXpathNotUniqueException e) {logger.warn(e.getMessage());}
		
		return result;
	}
	
	private List<MenuItem> processChilds(List<MenuItem> origs, String codeCurrent) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		List<MenuItem> result = new ArrayList<MenuItem>();
		
		for(MenuItem mi :origs)
		{
			MenuItem miAdd = null;
			if(mi.isSetView())
			{
				View view = AccessXpath.getView(access, mi.getView().getCode());
				if(noRestrictions || view.isPublic() || (mapViewAllowed.containsKey(mi.getView().getCode()) && mapViewAllowed.get(mi.getView().getCode())))
				{
					miAdd = processItem(mi,codeCurrent,view);
				}
			}
			else {miAdd = processItem(mi,codeCurrent,null);}
			if(miAdd!=null)
			{
				if(mi.getCode().equals(codeCurrent)){miAdd.setActive(true);}
				else{miAdd.setActive(false);}
				result.add(miAdd);
			}
		}
		
		return result;
	}
	
	private MenuItem processItem(MenuItem miOrig, String codeCurrent, View view) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		MenuItem mi = new MenuItem();
		mi.setCode(miOrig.getCode());
		if(miOrig.isSetLangs())
		{
			mi.setName(getNameFromLangs(miOrig.getLangs()));
		}
		else if(miOrig.isSetView())
		{
			mi.setName(getNameFromViews(view));
		}
		else
		{
			logger.warn("Translation missing!!");
			mi.setName("Translation missing");	
		}
		if(miOrig.isSetHref()) {mi.setHref(miOrig.getHref());}
		else if(miOrig.isSetView()) {mi.setHref(getHrefFromViews(miOrig.getView()));}
		else {mi.setHref("#");}
		
		mi.getMenuItem().addAll(processChilds(miOrig.getMenuItem(),codeCurrent));
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
		Langs langs = view.getLangs();
		return getNameFromLangs(langs);
	}
	
	private String getHrefFromViews(View viewCode)
	{
		try
		{
			View view = AccessXpath.getView(access, viewCode.getCode());
			if(view.isSetNavigation() && view.getNavigation().isSetUrlMapping())
			{
				StringBuffer sb = new StringBuffer();
				if(contextRoot!=null)
				{
					sb.append("/").append(contextRoot);
				}
				sb.append(view.getNavigation().getUrlMapping().getValue());
				return sb.toString();
			}
		}
		catch (ExlpXpathNotFoundException e) {}
		catch (ExlpXpathNotUniqueException e) {}
		return "#";
	}
}
