package net.sf.ahtutils.web.showcase;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.openfuxml.addon.jsf.factory.JsfComponentFactory;
import org.openfuxml.xml.addon.jsf.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class ShowcaseHighlightBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ShowcaseHighlightBean.class);
	private static final long serialVersionUID = 1L;
	private Component component;
	
	public Component getComponent() {return component;}

	@PostConstruct
    public void init() throws FileNotFoundException
    {				
		JsfComponentFactory jcf = new JsfComponentFactory();
		component = jcf.buildComponent("META-INF/resources/ahtutils/highlight.xhtml");
    }
}