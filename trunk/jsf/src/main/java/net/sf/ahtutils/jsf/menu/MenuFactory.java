package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.xpath.AccessXpath;
import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
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
	private Map<String,String> translationsMenu,translationsAccess;
	private Map<String,View> mapView;
	
	private static final String rootNode = UUID.randomUUID().toString();
	private DirectedGraph<String, DefaultEdge> graph;
	
	private int alwaysUpToLevel;
	
	public MenuFactory(String lang){this(null,lang);}	
	public MenuFactory(Access access, String lang)
	{
		this(access,null,lang);
		noRestrictions=true;
	}
	
	public MenuFactory(Access access, Map<String,Boolean> mapViewAllowed, String lang)
	{
		this.access=access;
		this.mapViewAllowed=mapViewAllowed;
		this.lang=lang;
		noRestrictions=false;
		translationsMenu = new Hashtable<String,String>();
		
		mapView = new Hashtable<String,View>();
		translationsAccess = new Hashtable<String,String>();
		createAccessMaps();
		alwaysUpToLevel = 1;
	}
	
	public void setAlwaysUpToLevel(int alwaysUpToLevel) {this.alwaysUpToLevel = alwaysUpToLevel;}
	
	private void createAccessMaps()
	{
		for(Category c : access.getCategory())
		{
			if(c.isSetViews())
			{
				for(View v : c.getViews().getView())
				{
					mapView.put(v.getCode(), v);
					if(v.isSetLangs())
					{
						for(Lang l : v.getLangs().getLang())
						{
							if(l.getKey().equals(lang)){translationsAccess.put(v.getCode(), l.getTranslation());}
						}
					}
				}
			}
		}
	}
	
	private void processMenu(Menu menu)
	{
		graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		graph.addVertex(rootNode);
	     	
		for(MenuItem mi : menu.getMenuItem())
		{
			processMenuItem(rootNode,mi);
		}
	}
	
	private void processMenuItem(String parentNode, MenuItem mi)
	{
		graph.addVertex(mi.getCode());
		graph.addEdge(parentNode, mi.getCode());
		
		if(mi.isSetLangs())
		{
			for(Lang l : mi.getLangs().getLang())
			{
				if(l.getKey().equals(lang)){translationsMenu.put(mi.getCode(), l.getTranslation());}
			}
		}
		for(MenuItem miChild : mi.getMenuItem())
		{
			processMenuItem(mi.getCode(),miChild);
		}
	}
	
	public Menu create(Menu menu, String codeCurrent)
	{
		processMenu(menu);
		Menu result = new Menu();
		
		try {result.getMenuItem().addAll(processChilds(1,menu.getMenuItem(),codeCurrent));}
		catch (UtilsNotFoundException e) {logger.warn(e.getMessage());}
		
		return result;
	}
	
	private List<MenuItem> processChilds(int level, List<MenuItem> origs, String codeCurrent) throws UtilsNotFoundException
	{
		List<MenuItem> result = new ArrayList<MenuItem>();
		
		for(MenuItem mi :origs)
		{
			MenuItem miAdd = null;
			if(mi.isSetView())
			{
				if(!mapView.containsKey(mi.getView().getCode())){throw new UtilsNotFoundException("No view with code="+mi.getView().getCode());}
				View view = mapView.get(mi.getView().getCode());
				if(noRestrictions || view.isPublic() || (mapViewAllowed.containsKey(mi.getView().getCode()) && mapViewAllowed.get(mi.getView().getCode())))
				{
					miAdd = processItem(mi,codeCurrent,view);
				}
			}
			else {miAdd = processItem(mi,codeCurrent,null);}
			if(miAdd!=null)
			{
				if(mi.getCode().equals(codeCurrent))
				{
					miAdd.setActive(true);
				}
				else{miAdd.setActive(false);}
				
				boolean currentIsChild = false;
				DijkstraShortestPath<String, DefaultEdge> dsp = new DijkstraShortestPath<String, DefaultEdge>(graph, mi.getCode(), codeCurrent);
				List<DefaultEdge> path = dsp.getPathEdgeList();
				
				if(path!=null)
				{
					currentIsChild = true;
					miAdd.setActive(true);
				}
				
				if(level<alwaysUpToLevel || currentIsChild)
				{
					miAdd.getMenuItem().addAll(processChilds(level+1,mi.getMenuItem(),codeCurrent));
				}
				result.add(miAdd);
			}
		}
		return result;
	}
	
	private MenuItem processItem(MenuItem miOrig, String codeCurrent, View view) throws UtilsNotFoundException
	{
		MenuItem mi = new MenuItem();
		mi.setCode(miOrig.getCode());
		if(miOrig.isSetLangs())
		{
			mi.setName(getNameFromMenuItem(miOrig.getCode()));
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
		return mi;
	}
	
	private String getNameFromMenuItem(String code)
	{
		if(!translationsMenu.containsKey(code)){return "???"+code+"???";}
		else {return translationsMenu.get(code);}
	}
	
	private String getNameFromViews(View view)
	{
		if(!translationsAccess.containsKey(view.getCode())){return "???"+view.getCode()+"???";}
		else {return translationsAccess.get(view.getCode());}
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
