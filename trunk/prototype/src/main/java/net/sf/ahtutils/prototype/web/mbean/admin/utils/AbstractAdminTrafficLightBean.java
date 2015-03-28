package net.sf.ahtutils.prototype.web.mbean.admin.utils;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.factory.ejb.util.EjbTrafficLightFactory;
import net.sf.ahtutils.interfaces.facade.UtilsTrafficLightFacade;
import net.sf.ahtutils.interfaces.model.util.UtilsTrafficLight;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractAdminTrafficLightBean <L extends UtilsLang,D extends UtilsDescription,SCOPE extends UtilsStatus<SCOPE,L,D>,LIGHT extends UtilsTrafficLight<L,D,SCOPE>>
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminTrafficLightBean.class);
	
	protected UtilsTrafficLightFacade<L,D,SCOPE,LIGHT> fUtils;
	
	private Class<SCOPE> cScope;
	private Class<LIGHT> cLight;
	
	private String[] defaultLangs;
	
	protected EjbTrafficLightFactory<L,D,SCOPE,LIGHT> efLight;
	
	public void initSuper(String[] defaultLangs,Class<L> cLang,Class<D> cDescription,Class<SCOPE> cScope,Class<LIGHT> cLight)
	{
		this.defaultLangs=defaultLangs;
		this.cScope=cScope;
		this.cLight=cLight;
		efLight = EjbTrafficLightFactory.factory(cLang,cDescription,cLight);
	}
	
	//Scopes
	protected List<SCOPE> trafficLightScopes;
	public List<SCOPE> getTrafficLightScopes(){return trafficLightScopes;}
	
	protected void reloadTrafficLightScopes()
	{
		trafficLightScopes = fUtils.all(cScope);
		logger.trace("Results: " + trafficLightScopes.size() +" scopes loaded.");
	}
	
	//Scope
	protected SCOPE scope;
	public SCOPE getScope(){return scope;}
	public void setScope(SCOPE scope){this.scope = scope;}
	
	public void selectScope()
	{
		logger.info(AbstractLogMessage.selectEntity(scope));
		reloadTrafficLights();
	}
	
	//Lights
	protected List<LIGHT> trafficLights;
	public List<LIGHT> getTrafficLights(){return trafficLights;}
	
	protected void reloadTrafficLights()
	{
		trafficLights = fUtils.allOrderedTrafficLights(cLight,scope);
		logger.debug("Results: " + trafficLights.size());
	}
	
	//Light
	protected LIGHT trafficLight;
	public LIGHT getTrafficLight(){return trafficLight;}
	public void setTrafficLight(LIGHT trafficLight){this.trafficLight = trafficLight;}
	
	public void addTrafficLight() throws UtilsContraintViolationException
	{
		logger.debug(AbstractLogMessage.addEntity(cLight));
		trafficLight = efLight.build(defaultLangs,scope);
	}
	
	public void selectTrafficLight()
	{
		logger.debug(AbstractLogMessage.selectEntity(trafficLight));
	}
	
	public void save() throws UtilsLockingException, UtilsContraintViolationException
	{
		logger.debug(AbstractLogMessage.saveEntity(trafficLight));
		trafficLight = fUtils.save(trafficLight);
		reloadTrafficLights();
	}
	
	public void rm() throws UtilsContraintViolationException
	{
		logger.debug(AbstractLogMessage.rmEntity(trafficLight));
		fUtils.rm(trafficLight);
		trafficLight=null;
		reloadTrafficLights();
	}
}