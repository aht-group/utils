package net.sf.ahtutils.web.showcase;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.openfuxml.addon.jsf.factory.JsfComponentFactory;
import org.openfuxml.xml.addon.jsf.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @ApplicationScoped
public class ShowcaseBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ShowcaseBean.class);
	private static final long serialVersionUID = 1L;

	private Map<String,Component> component;
	private JsfComponentFactory jcf;

	@PostConstruct
	public void init() throws FileNotFoundException
	{			
		jcf = new JsfComponentFactory();
		component = new Hashtable<String,Component>();
		build("highlight");
		build("menuUl");
		build("breadcrumb");
		build("timeago");
	}

	private void build(String code)
	{
		component.put(code, jcf.buildComponent("META-INF/resources/ahtutils/"+code+".xhtml"));
	}
	
	public Map<String, Component> getComponent() {return component;}
}