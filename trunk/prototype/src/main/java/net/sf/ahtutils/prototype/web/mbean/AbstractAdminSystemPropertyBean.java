package net.sf.ahtutils.prototype.web.mbean;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.UtilsProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminSystemPropertyBean <P extends UtilsProperty> 
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSystemPropertyBean.class);
	
	protected UtilsFacade fUtils;
	
	protected P property;
	public P getProperty() {return property;}
	public void setProperty(P property) {this.property = property;}

	protected List<P> properties;
	public List<P> getProperties() {return properties;}
	
	private Class<P> cProperty;
	
	public void initSuper(final Class<P> cProperty)
	{
		this.cProperty=cProperty;
	}
	
	protected void refreshList()
	{
		properties = fUtils.all(cProperty);
	}
	
	public void selectProperty() throws UtilsNotFoundException
	{
		property = fUtils.find(cProperty, property);
	}
	
	public void save() throws UtilsNotFoundException, UtilsContraintViolationException, UtilsLockingException
	{
		property = fUtils.save(property);
		refreshList();
	}
}