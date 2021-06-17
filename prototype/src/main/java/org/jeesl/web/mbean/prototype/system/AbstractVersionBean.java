package org.jeesl.web.mbean.prototype.system;

import java.io.Serializable;

import org.jeesl.factory.builder.system.LocaleFactoryBuilder;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractVersionBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>>
			extends AbstractAdminBean<L,D,LOC>
			implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractVersionBean.class);
	
	public AbstractVersionBean(LocaleFactoryBuilder<L,D,LOC> fbStatus)
	{
		super(fbStatus.getClassL(),fbStatus.getClassD());
	}
	
	public String version(String library) throws ClassNotFoundException
	{
		switch(library)
		{
			case "jsf": return Class.forName("javax.faces.view.ViewScoped").getPackage().getImplementationVersion(); 
			case "prime": return Class.forName("org.primefaces.event.SelectEvent").getPackage().getImplementationVersion();
			case "omni": return Class.forName("org.omnifaces.cdi.ViewScoped").getPackage().getImplementationVersion();
			default: return "";
		}
	}
	

}
