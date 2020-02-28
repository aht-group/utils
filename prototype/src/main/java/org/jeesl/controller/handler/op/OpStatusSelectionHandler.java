package org.jeesl.controller.handler.op;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.handler.OpEntitySelection;
import org.jeesl.interfaces.bean.op.OpEntityBean;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpStatusSelectionHandler <T extends EjbWithId> extends AbstractOpSelectionHandler<T> implements OpEntitySelection<T>
{
	final static Logger logger = LoggerFactory.getLogger(OpEntitySelection.class);
	public static final long serialVersionUID=1;

    public OpStatusSelectionHandler(OpEntityBean bean) {this(bean,new ArrayList<T>());}
    public OpStatusSelectionHandler(OpEntityBean bean, List<T> opEntites)
    {
	    	super(bean,opEntites);
	    	showLang=true;
    }
}