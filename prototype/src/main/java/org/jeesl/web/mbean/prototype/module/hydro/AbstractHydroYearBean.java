package org.jeesl.web.mbean.prototype.module.hydro;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.controller.handler.sb.SbMultiHandler;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.module.HydroFactoryBuilder;
import org.jeesl.factory.ejb.module.hydro.EjbHydroYearFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroDecade;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.util.comparator.ejb.component.sb.HydroYearComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractHydroYearBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											HD extends JeeslHydroDecade<L,D,HD,?>,
											HY extends JeeslHydroYear<L,D,HD,HY>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractHydroYearBean.class);

	private UtilsFacade fUtils;
	private final HydroFactoryBuilder<L,D,HD,HY> fbHydroYear;

	private EjbHydroYearFactory<L,D,HD,HY> efHydroYear;

	private final SbMultiHandler<HD> sbhDecade; public SbMultiHandler<HD> getSbhDecade() {return sbhDecade;}
	private final Comparator<HY> comparatorHydroYear;

	protected List<HY> hydroYears; public List<HY> getHydroYears() {return hydroYears;}

	protected HY hydroYear;

	public HY getHydroYear() {
		return hydroYear;
	}

	public void setHydroYear(HY hydroYear) {
		this.hydroYear = hydroYear;
	}

	public AbstractHydroYearBean(final HydroFactoryBuilder<L,D,HD,HY> fbHydroYear)
	{
		super(fbHydroYear.getClassL(),fbHydroYear.getClassD());
		this.fbHydroYear = fbHydroYear;
		this.efHydroYear = this.fbHydroYear.hydroYear();
		sbhDecade = new SbMultiHandler<HD>(fbHydroYear.getClassDecade(),this);
		comparatorHydroYear = (new HydroYearComparator<L,D,HD,HY>()).factory(HydroYearComparator.Type.code);
	}


	public void initSuper(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, UtilsFacade fUtils)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fUtils=fUtils;

		sbhDecade.setList(fUtils.allOrderedPositionVisible(fbHydroYear.getClassDecade()));
		sbhDecade.selectAll();
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+": "+fbHydroYear.getClassDecade().getSimpleName()+" "+sbhDecade.getSelected().size()+"/"+sbhDecade.getList().size());}
		refreshList();
	}


	private void refreshList()
	{
		hydroYears = fUtils.all(fbHydroYear.getClassYear());
		Collections.sort(hydroYears,comparatorHydroYear);
	}

	@Override
	public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+" toggled, but NYI");}
	}

	public void selectHydroYear() throws JeeslNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(hydroYear));}
		hydroYear = fUtils.find(fbHydroYear.getClassYear(), hydroYear);
	}

	public void saveHydroYear() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(hydroYear));
		if(hydroYear.getDecade()!=null){
			HD decade = fUtils.find(fbHydroYear.getClassDecade(),hydroYear.getDecade());
			hydroYear.setDecade(decade);
			}
		hydroYear = fUtils.save(hydroYear);
		logger.info("-----" + hydroYear.toString() +"-------");
		refreshList();
	}

	public void addHydroYear() {
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbHydroYear.getClassYear()));}
		hydroYear = efHydroYear.build(null);
		hydroYear.setName(efLang.createEmpty(localeCodes));
		hydroYear.setDescription(efDescription.createEmpty(localeCodes));
	}

	public void deleteHydroYear() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(hydroYear));}
		fUtils.rm(hydroYear);
		hydroYear=null;
		//bMessage.growlSuccessRemoved();
		refreshList();
	}

	public void cancelHydroYear()
	{
		hydroYear = null;
	}
}