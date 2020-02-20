package org.jeesl.web.mbean.prototype.system.io.dashboard;

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
import org.jeesl.factory.builder.io.IoDashboardFactoryBuilder;
import org.jeesl.factory.ejb.system.io.dashboard.EjbDashboardFactory;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashComponent;
import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashboard;
import org.jeesl.interfaces.model.system.io.dash.JeeslIoDashboardResolution;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.util.comparator.ejb.system.io.dashboard.DashboardComparator;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractIoDashboardBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
											DBR extends JeeslIoDashboardResolution<L,D,DBR,?>,
											DB extends JeeslIoDashboard<L,D,DBR,DB>,
											DBC extends JeeslIoDashComponent<L,D,DBC>>
		extends AbstractAdminBean<L,D>
		implements Serializable,SbToggleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractIoDashboardBean.class);

	private JeeslFacade fUtils;
	private final IoDashboardFactoryBuilder<L,D,DBR,DB,DBC> fbDashboard;

	private EjbDashboardFactory<L,D,DBR,DB> efDashboard;

	private final SbMultiHandler<DBR> sbhResolution; public SbMultiHandler<DBR> getSbhResolution() {return sbhResolution;}
	private final Comparator<DB> comparatorDashboard;

	protected List<DB> dashboards; public List<DB> getDashboards() {return dashboards;}
	protected List<DBR> resolutions; public List<DBR> getResolutions() {return resolutions;}

	protected DB dashboard;

	public DB getDashboard() {
		return dashboard;
	}

	public void setDashboard(DB dashboard) {
		this.dashboard = dashboard;
	}

	public AbstractIoDashboardBean(final IoDashboardFactoryBuilder<L,D,DBR,DB,DBC> fbDashboard)
	{
		super(fbDashboard.getClassL(),fbDashboard.getClassD());
		this.fbDashboard = fbDashboard;
		this.efDashboard = this.fbDashboard.dashboard();
		sbhResolution = new SbMultiHandler<DBR>(fbDashboard.getClassResolution(),this);
		comparatorDashboard = (new DashboardComparator<L,D,DBR,DB>()).factory(DashboardComparator.Type.code);
	}


	public void initSuper(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslFacade fUtils)
	{
		super.initJeeslAdmin(bTranslation, bMessage);
		this.fUtils=fUtils;

		sbhResolution.setList(fUtils.allOrderedPositionVisible(fbDashboard.getClassResolution()));
		sbhResolution.selectAll();
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+": "+fbDashboard.getClassResolution().getSimpleName()+" "+sbhResolution.getSelected().size()+"/"+sbhResolution.getList().size());}
		refreshList();
	}


	private void refreshList()
	{
		dashboards = fUtils.all(fbDashboard.getClassDashboard());
		resolutions = fUtils.all(fbDashboard.getClassResolution());
		Collections.sort(dashboards,comparatorDashboard);
	}

	@Override
	public void toggled(Class<?> c) throws JeeslLockingException, JeeslConstraintViolationException
	{
		if(debugOnInfo){logger.info(SbMultiHandler.class.getSimpleName()+" toggled, but NYI");}
	}

	public void selectDashboard() throws JeeslNotFoundException
	{
		if(debugOnInfo) {logger.info(AbstractLogMessage.selectEntity(dashboard));}
		dashboard = fUtils.find(fbDashboard.getClassDashboard(), dashboard);
	}

	public void saveDashboard() throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		logger.info(AbstractLogMessage.saveEntity(dashboard));
		if(dashboard.getResolution()!=null){
			DBR resolution = fUtils.find(fbDashboard.getClassResolution(),dashboard.getResolution());
			dashboard.setResolution(resolution);
			}
		dashboard = fUtils.save(dashboard);
		logger.info("-----" + dashboard.toString() +"-------");
		refreshList();
	}

	public void addDashboard() {
		if(debugOnInfo){logger.info(AbstractLogMessage.addEntity(fbDashboard.getClassDashboard()));}
		dashboard = efDashboard.build(null);
		dashboard.setName(efLang.createEmpty(localeCodes));
		dashboard.setDescription(efDescription.createEmpty(localeCodes));
	}

	public void deleteDashboard() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info(AbstractLogMessage.rmEntity(dashboard));}
		fUtils.rm(dashboard);
		dashboard=null;
		//bMessage.growlSuccessRemoved();
		refreshList();
	}

	public void cancelDashboard()
	{
		dashboard = null;
	}
}