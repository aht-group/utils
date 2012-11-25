package net.sf.ahtutils.web.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import net.sf.ahtutils.web.mbean.util.AbstractIconBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named @ApplicationScoped
public class IconBean extends AbstractIconBean implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(IconBean.class);
	private static final long serialVersionUID = 1L;
	
	//******* Methods *******************************

	@PostConstruct
    public void init()
    {
		logger.info("@PostConstruct");
		super.initPath("resources/gfx");
		
		mapStatic.put("dmNotSelected", "circle/circle-ok-grey.png");
		mapStatic.put("dmSelected", "circle/circle-ok.png");
    }
}