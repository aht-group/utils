package net.sf.ahtutils.web.showcase;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import net.sf.ahtutils.model.ejb.status.AhtUtilsStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @SessionScoped
public class ShowcaseTabTranslationEditorBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ShowcaseTabTranslationEditorBean.class);
	private static final long serialVersionUID = 1L;

	private List<String> keys;
	private AhtUtilsStatus ejb;

	@PostConstruct
	public void init() throws FileNotFoundException
	{			
		keys = new ArrayList<String>();
		keys.add("de");
		keys.add("fr");
		
		ejb = new AhtUtilsStatus();
	}
	
	public void save()
	{
		
	}

	public List<String> getKeys() {return keys;}
	
	public AhtUtilsStatus getEjb() {return ejb;}
	public void setEjb(AhtUtilsStatus ejb) {this.ejb = ejb;}
}