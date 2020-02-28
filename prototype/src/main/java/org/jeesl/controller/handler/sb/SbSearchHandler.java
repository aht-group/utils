package org.jeesl.controller.handler.sb;

import java.util.ArrayList;

import org.jeesl.interfaces.bean.sb.SbSearchBean;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SbSearchHandler <T extends EjbWithId> extends SbSingleHandler<T>
{
	final static Logger logger = LoggerFactory.getLogger(SbMultiHandler.class);
	private static final long serialVersionUID = 1L;

	private final SbSearchBean bean;

	private String searchText; public String getSearchText() {return this.searchText;} public void setSearchText(String searchText) {this.searchText = searchText;}

	public SbSearchHandler(Class<T> c, SbSearchBean bean)
	{
		super(c,bean);
		this.bean=bean;
		logger.trace("creating handler: " + SbSearchHandler.class.getSimpleName());
		this.searchText ="";
	}

	public void handleKeyEvent()
	{
		logger.trace("handle search event: " + SbSearchHandler.class.getSimpleName());
		this.clear();
		if(searchText != "")
		{
			bean.triggerSbSearch();
		}
	}

	public void cancelEvent()
	{
		logger.trace("cancel search event: " + SbSearchHandler.class.getSimpleName());
		this.clear();
		this.searchText ="";
		this.update(new ArrayList<T>());
	}

	@Override public String toString() {return "handler name: " + this.getClass().getSimpleName() + " for: " +this.c.getSimpleName();}
}