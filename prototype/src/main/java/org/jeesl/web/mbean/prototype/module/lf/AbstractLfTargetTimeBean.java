package org.jeesl.web.mbean.prototype.module.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslLogframeFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.LfFactoryBuilder;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeInterval;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.tenant.JeeslTenantRealm;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public abstract class AbstractLfTargetTimeBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								R extends JeeslTenantRealm<L,D,R,?>,
								TTG extends JeeslLfTargetTimeGroup<L,TTI>,
								TTI extends JeeslLfTargetTimeInterval<L,D,TTI,?>,
								TTE extends JeeslLfTargetTimeElement<L,TTG>
								>
					extends AbstractAdminBean<L,D,LOC>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractLfTargetTimeBean.class);
	protected boolean uiGenerate;
	public boolean getUiGenerate()
	{
		if(getUiAllowGenerate() && uiGenerate)
		{
			if(timeGroup.getEndDate()!= null) {return false;}
			if(timeGroup.getStartDate()!= null) {return false;}
			return true;
		}
		return false;
	}

	protected boolean uiAllowGenerate;
	public boolean getUiAllowGenerate()
	{
		if(timeGroup!=null && timeGroup.getId() > 0) {return true;}
		return false;
	}
	protected List<TTG> timeGroups; public List<TTG> getTimeGroups() {return timeGroups;} public void setTimeGroups(List<TTG> timeGroups) {this.timeGroups = timeGroups;}
	protected TTG timeGroup; public TTG getTimeGroup() {return timeGroup;} public void setTimeGroup(TTG timeGroup) {this.timeGroup = timeGroup;}
	protected List<TTE> elements; public List<TTE> getElements() {return elements;} public void setElements(List<TTE> elements) {this.elements = elements;}
	protected TTE element; public TTE getElement() {return element;} public void setElement(TTE element) {this.element = element;}
	protected List<TTI> intervalTypes; public List<TTI> getIntervalTypes() {return intervalTypes;} public void setIntervalTypes(List<TTI> intervalTypes) {this.intervalTypes = intervalTypes;}


	protected final LfFactoryBuilder<L,D,R,TTG,TTI,TTE> fbLogFrame;

	protected JeeslLogframeFacade<L,D,R,TTG,TTI,TTE> fLogFrame;


	public AbstractLfTargetTimeBean(LfFactoryBuilder<L,D,R,TTG,TTI,TTE> fbLf)
	{
		super(fbLf.getClassL(),fbLf.getClassD());
		this.fbLogFrame=fbLf;
		this.timeGroups = new ArrayList<TTG>();
		this.intervalTypes = new ArrayList<TTI>();
	}

	protected void postConstructHd(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslLogframeFacade<L,D,R,TTG,TTI,TTE> fLf)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fLogFrame=fLf;
		this.intervalTypes = fLf.all(fbLogFrame.getClassTTI());
		reloadTimeGroups();
	}

	protected void reloadTimeGroups()
	{
		timeGroups = fLogFrame.all(fbLogFrame.getClassTTG());
		if(debugOnInfo) {logger.info(AbstractLogMessage.reloaded(fbLogFrame.getClassTTG(),timeGroups));}
		uiGenerate = false;
	}

	public void addTimeGroup()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLogFrame.getClassTTG()));}
		timeGroup = fbLogFrame.buildTargetTimeGroup();
		//timeGroup.setName(efLang.createEmpty(localeCodes));
		reset(false,true);
	}

	public void saveTimeGroup() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(timeGroup));}
		timeGroup.setInterval(fLogFrame.find(fbLogFrame.getClassTTI(),timeGroup.getInterval()));
		timeGroup = fLogFrame.save(timeGroup);
		reloadTimeGroups();
		reloadElements();
	}

	public void selectTimeGroup() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(timeGroup));}
		timeGroup = fLogFrame.find(fbLogFrame.getClassTTG(),timeGroup);
		reloadElements();
	}

	public void deleteTimeGroup() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(timeGroup));}
		fLogFrame.rm(timeGroup);
		reloadTimeGroups();
		reset(true,true);
	}
	public void addElement()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.addEntity(fbLogFrame.getClassTTE()));}
		element = fbLogFrame.buildElement(timeGroup,elements);
		//element.setName(efLang.createEmpty(localeCodes));
	}

	public void selectElement()
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(timeGroup));}
		//element = efLang.persistMissingLangs(fLogFrame,localeCodes,element);
	}

	public void saveElement() throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.saveEntity(element));}
		element = fLogFrame.save(element);
		reloadElements();
	}

	public void deleteElement() throws JeeslConstraintViolationException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.rmEntity(timeGroup));}
		fLogFrame.rm(element);
		reloadElements();
		reset(false,true);
	}
	public void reloadElements()
	{
		elements = fLogFrame.allForParent(fbLogFrame.getClassTTE(),timeGroup);
		uiGenerate = false;
	}

	public void generateTimeGroup() throws JeeslConstraintViolationException, JeeslLockingException
	{
		saveTimeGroup();
		Calendar cal = Calendar.getInstance();
		Date record = timeGroup.getStartDate();
		TTI interval = timeGroup.getInterval();

		if(interval.getCode().equals("quarter")){
			cal.setTime(record);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			record = cal.getTime();
		}
		int count = 0;
		while (record.getTime() <= timeGroup.getEndDate().getTime())
		{
			count++;
			element = fbLogFrame.buildElement(timeGroup,elements);
			//element.setName(efLang.createEmptyWithDefault(localeCodes,String.valueOf(count)));
			element.setRecord(record);
			if(interval.getCode().equals("week")){cal.setTime(record); cal.add(Calendar.WEEK_OF_MONTH, 1);record = cal.getTime();}
			if(interval.getCode().equals("month")){cal.setTime(record); cal.add(Calendar.MONTH, 1);record = cal.getTime();}
			if(interval.getCode().equals("year")){cal.setTime(record); cal.add(Calendar.YEAR, 1);record = cal.getTime();}
			if(interval.getCode().equals("quarter")){cal.setTime(record); cal.add(Calendar.MONTH, 3);record = cal.getTime();}
		}
	}
	private void reset(boolean rTimeGroup, boolean rElement)
	{
		if(rTimeGroup) {timeGroup=null;}
		if(rElement) {element=null;}
	}


	public void resetTimeGroup() {reset(true, false);}
	public void resetElement() {reset(false,true);}

	public void updateUiTimeGroup(){uiGenerate = true;}

}