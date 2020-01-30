package org.jeesl.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.jeesl.controller.handler.op.AbstractOpSelectionHandler;
import org.jeesl.interfaces.bean.op.OpEntityBean;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class OpPositionNameSelectionHandler <T extends EjbWithId> extends AbstractOpSelectionHandler<T> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;

	@SuppressWarnings("unused")
	private JeeslFacade facade;
	
    public OpPositionNameSelectionHandler(JeeslFacade facade, OpEntityBean bean){this(facade,bean,new ArrayList<T>());}
    public OpPositionNameSelectionHandler(JeeslFacade facade, OpEntityBean bean, List<T> opEntites)
    {
    	super(bean,opEntites);
    	this.facade=facade;
    	showName=true;
    }
}