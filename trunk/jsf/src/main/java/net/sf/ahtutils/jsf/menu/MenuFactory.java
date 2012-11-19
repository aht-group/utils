package net.sf.ahtutils.jsf.menu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.xml.access.Access;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;
import net.sf.ahtutils.xml.navigation.Menu;
import net.sf.ahtutils.xml.navigation.MenuItem;
import net.sf.ahtutils.xml.navigation.UrlMapping;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.xpath.AccessXpath;
import net.sf.ahtutils.xml.xpath.NavigationXpath;
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
	private Map<String,MenuItem> mapMenuItems;
	
	private String rootNode;
	private DirectedGraph<String, DefaultEdge> graph;
	
	private int alwaysUpToLevel;
	
	public MenuFactory(Menu menu,String lang){this(menu,null,lang, UUID.randomUUID().toString(),true);}
	public MenuFactory(Menu menu,String lang, String rootNode){this(menu,null,lang,rootNode,true);}	
	public MenuFactory(Menu menu, Access access,String lang){this(menu,access,lang, UUID.randomUUID().toString(),false);}
	public MenuFactory(Menu menu, Access access,String lang, String rootNode){this(menu,access,lang, rootNode,false);}
	public MenuFactory(Menu menu, Access access,String lang, String rootNode, boolean noRestrictions)
	{

		this.access=access;
		this.lang=lang;
		this.rootNode=rootNode;
		this.noRestrictions=noRestrictions;
		translationsMenu = new Hashtable<String,String>();
		mapMenuItems = new Hashtable<String,MenuItem>();
		processMenu(menu);
		
		if(logger.isTraceEnabled())
		{
			
			logger.info("Graph: "+graph);
			logger.info("mapMenuItems.size()"+mapMenuItems.size());
		}
		
		mapView = new Hashtable<String,View>();
		translationsAccess = new Hashtable<String,String>();
		if(access!=null){createAccessMaps();}
		alwaysUpToLevel = 1;
	}
	
	private void processMenu(Menu menu)
	{
		graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		graph.addVertex(rootNode);
		
		logger.trace("Added Root: "+rootNode);
	     	
		for(MenuItem mi : menu.getMenuItem())
		{
			processMenuItem(rootNode,mi);
		}
	}
	
	private void processMenuItem(String parentNode, MenuItem mi)
	{
		graph.addVertex(mi.getCode());
		graph.addEdge(parentNode, mi.getCode());
		
		logger.trace("Added Vertex: "+mi.getCode());
		
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
		mi.getMenuItem().clear();
		mapMenuItems.put(mi.getCode(), mi);
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
	
	public Menu build()
	{
		noRestrictions=true;
		return build(null,rootNode);
	}
	public Menu build(String codeCurrent) {return build(null,codeCurrent);}
	public Menu build(Map<String,Boolean> mapViewAllowed) {return build(mapViewAllowed,rootNode);}
	public Menu build(Map<String,Boolean> mapViewAllowed, String codeCurrent){return build(mapViewAllowed,codeCurrent,false);}
	public Menu build(Map<String,Boolean> mapViewAllowed, String codeCurrent, boolean isLoggedIn)
	{
		logger.trace("Building ("+rootNode+"): "+codeCurrent);
		this.mapViewAllowed=mapViewAllowed;
		Menu result = new Menu();
		
		try {result.getMenuItem().addAll(processChilds(1,rootNode,codeCurrent,isLoggedIn));}
		catch (UtilsNotFoundException e) {logger.warn(e.getMessage());}
		
		return result;
	}
	
	private List<MenuItem> processChilds(int level, String node, String codeCurrent, boolean isLoggedIn) throws UtilsNotFoundException
	{
		List<MenuItem> result = new ArrayList<MenuItem>();
		
		Iterator<DefaultEdge> iterator = graph.outgoingEdgesOf(node).iterator();
		while(iterator.hasNext())
		{
			DefaultEdge edge = iterator.next();
			MenuItem miAdd = null;
			MenuItem mi = mapMenuItems.get(graph.getEdgeTarget(edge));
			if(mi.isSetView())
			{
				if(!mapView.containsKey(mi.getView().getCode())){throw new UtilsNotFoundException("No view with code="+mi.getView().getCode());}
				View view = mapView.get(mi.getView().getCode());
				if(noRestrictions
						|| view.isPublic()
						|| (view.isSetOnlyLoginRequired() && view.isOnlyLoginRequired() && isLoggedIn)
						|| (mapViewAllowed!=null && mapViewAllowed.containsKey(mi.getView().getCode()) && mapViewAllowed.get(mi.getView().getCode())))
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
					miAdd.getMenuItem().addAll(processChilds(level+1,mi.getCode(),codeCurrent,isLoggedIn));
				}
				mapMenuItems.get(miAdd.getCode()).setName(miAdd.getName());
				mapMenuItems.get(miAdd.getCode()).setHref(miAdd.getHref());
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
			mi.setName(getNameFromViews(view,miOrig.getView()));
		}
		else
		{
			logger.warn("Translation missing!!");
			mi.setName("Translation missing");	
		}
		
		if(miOrig.isSetHref())
		{
			mi.setHref(miOrig.getHref());
		}
		else if(miOrig.isSetView())
		{
			mi.setHref(getHrefFromViews(miOrig.getView()));
		}
		else
		{
			mi.setHref("#");
		}
		return mi;
	}
	
	private String getNameFromMenuItem(String code)
	{
		if(!translationsMenu.containsKey(code)){return "???"+code+"???";}
		else {return translationsMenu.get(code);}
	}
	
	private String getNameFromViews(View view, View viewCode)
	{
		StringBuffer sbLabel = new StringBuffer();
		if(!translationsAccess.containsKey(view.getCode())){sbLabel.append("???"+view.getCode()+"???");}
		else {sbLabel.append(translationsAccess.get(view.getCode()));}
		
		if(viewCode.isSetLabel())
		{
			if(sbLabel.length()>0){sbLabel.append(" ");}
			sbLabel.append(viewCode.getLabel());	
		}
		return sbLabel.toString();
	}
	
	private String getHrefFromViews(View viewCode)
	{
		try
		{
			View view = AccessXpath.getView(access, viewCode.getCode());
			if(view.isSetNavigation() && view.getNavigation().isSetUrlMapping())
			{
				UrlMapping urlMapping = view.getNavigation().getUrlMapping();
				StringBuffer sb = new StringBuffer();
				if(contextRoot!=null)
				{
					sb.append("/").append(contextRoot);
				}
				if(urlMapping.isSetUrl())
				{
					
					sb.append(urlMapping.getUrl());
					if(viewCode.isSetUrlParameter()){sb.append(viewCode.getUrlParameter());}
				}
				else{sb.append(urlMapping.getValue());}
				return sb.toString();
			}
		}
		catch (ExlpXpathNotFoundException e) {}
		catch (ExlpXpathNotUniqueException e) {}
		return "#";
	}
	
	private void removeEdgeToTemplate(String dynamicRoot, String templateNode)
	{
		Iterator<DefaultEdge> iterator = graph.getAllEdges(dynamicRoot, templateNode).iterator();
		List<DefaultEdge> list = new ArrayList<DefaultEdge>();
		while(iterator.hasNext()){list.add(iterator.next());}
		graph.removeAllEdges(list);
	}
	
	public void addDynamicNodes(Menu dynamicMenu)
	{
		for(MenuItem mi : dynamicMenu.getMenuItem())
		{
			removeEdgeToTemplate(dynamicMenu.getCode(), mi.getView().getCode());
			
			graph.addVertex(mi.getCode());
			graph.addEdge(dynamicMenu.getCode(), mi.getCode());
			
			mapMenuItems.put(mi.getCode(), mi);
		}
	}
	
	public List<MenuItem> breadcrumb(String code){return breadcrumb(false,code);}
	public List<MenuItem> breadcrumb(boolean withRoot, String code)
	{
		List<MenuItem> result = new ArrayList<MenuItem>();
		
		DijkstraShortestPath<String, DefaultEdge> dsp = new DijkstraShortestPath<String, DefaultEdge>(graph,rootNode, code);
		List<DefaultEdge> path = dsp.getPathEdgeList();
		if(path.size()>0)
		{
			result.add(mapMenuItems.get(graph.getEdgeSource(path.get(0))));
		}
		for(DefaultEdge de : path)
		{
			String src = graph.getEdgeTarget(de);
			result.add(mapMenuItems.get(src));
		}
		if(!withRoot){result.remove(0);}
		return result;
	}
	
	public MenuItem subMenu(Menu menu, String code)
	{
		MenuItem result;
		try {result = NavigationXpath.getMenuItem(menu, code);}
		catch (ExlpXpathNotFoundException e) {result = new MenuItem();}
		catch (ExlpXpathNotUniqueException e) {result = new MenuItem();}
		return result;
	}
	

}
