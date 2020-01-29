package org.jeesl.controller.handler.sb.decade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.sb.SbDateIntervalSelection;
import org.jeesl.controller.handler.sb.SbDateHandler;
import org.jeesl.controller.handler.sb.tree.SbTree2Handler;
import org.jeesl.controller.handler.tree.TreeUpdateParameter;
import org.jeesl.controller.monitor.ProcessingTimeTracker;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.controller.handler.OutputXpathPattern;
import org.jeesl.interfaces.controller.handler.tree.JeeslTreeSelected;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroDecade;
import org.jeesl.interfaces.model.module.hydro.JeeslHydroYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.exlp.util.io.StringUtil;

@SuppressWarnings("rawtypes")
public class SbDecadeTreeHandler<HD extends JeeslHydroDecade, HY extends JeeslHydroYear> extends SbTree2Handler<HD, HY> implements Serializable,SbDateIntervalSelection
{
	final static Logger logger = LoggerFactory.getLogger(SbDecadeTreeHandler.class);
	private static final long serialVersionUID = 1L;

	private final UtilsFacade fUtils;
	private final Class<HD> cDecade; public Class<HD> getClassDecade(){return cDecade;}
	private final Class<HY> cYear; public Class<HY> getClassYear(){return cYear;}

	public HD getDecade() {return l1;}
	public HY getYear() {return l2;}

	private SbDateHandler sbDateHandler; public SbDateHandler getSbDateHandler() {return sbDateHandler;}

	public SbDecadeTreeHandler(JeeslTreeSelected callback, UtilsFacade fUtils, final Class<HD> cDecade, final Class<HY> cYear)
	{
		super(callback,new SbDecadeTreeCache<HD,HY>(fUtils,cDecade,cYear),new SbDecadeTreeStore<HD,HY>());
		this.fUtils=fUtils;
		this.cDecade = cDecade;
		this.cYear = cYear;
		activateGlobalIgnores();

		xpath1 = OutputXpathPattern.multiLang;
		xpath2 = OutputXpathPattern.multiLang;

		sbDateHandler = new SbDateHandler(this);
	}

	public void update() throws JeeslNotFoundException
	{
		ProcessingTimeTracker ptt = new ProcessingTimeTracker(true);
		reset2();
		viewIsGlobal = true;//identity.hasSystemView(view.getCode());

		if(viewIsGlobal)
		{
			if(debugOnInfo) {logger.info("Global View, populating Decades");}
			list1.addAll(fUtils.all(getClassDecade()));
			selectGlobal();
		}
		else
		{
			if(debugOnInfo) {logger.info("Security view, Applying Domain Roles ... populating years" );}
			List<HY> list = new ArrayList<HY>();
			list = fUtils.all(getClassYear());
			addAllowedL2(list);

			selectSecurity2();
		}

		ptt.stop();
		logger.info("Update "+ptt.toTotalPeriod());
	}

	private void selectGlobal()
	{
		if(debugOnInfo) {logger.info("Selecting Global");}

		if(debugOnInfo) {logger.info("\tSelecting "+getClassDecade().getSimpleName()+" from ");}
		super.cascade1(list1.get(0),TreeUpdateParameter.build(false,true,true,true,true));

	}

	protected void activateGlobalIgnores()
	{
		//It is possible to add Elements to the ignoreX sets
		//ignore2.add(object)
	}

	// The methods to get the parent element need to be specified for each hierarchy level
	@SuppressWarnings("unchecked")
	@Override protected HD getParentForL2(HY type) { return (HD) type.getDecade(); }

	@Override
	public void debug(boolean debug)
	{
		if(debug)
		{
			logger.info(StringUtil.stars());
			super.debug(debug);
		}
	}
	@Override
	public void callbackDateChanged() {
		logger.info("sbDateHandler callback  method");

	}
	public void updateDateRange() {
		if(this.getYear() != null) {
			sbDateHandler.setDate1(this.getYear().getValidFrom());
			sbDateHandler.setDate2(this.getYear().getValidUntil());
		}

	}
}