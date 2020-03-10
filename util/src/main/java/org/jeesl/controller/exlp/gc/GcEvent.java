package org.jeesl.controller.exlp.gc;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.event.AbstractEvent;
import net.sf.exlp.interfaces.LogEvent;

public class GcEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(GcEvent.class);
	static final long serialVersionUID = 1;
	
	private String type;
	private float real,user,sys;
	
	public GcEvent(Date record, String type, float user, float sys, float real)
	{
		this.record = record;
		this.type = type;
		this.user = user;
		this.sys = sys;
		this.real = real;
	}

	public void debug()
	{
		super.debug();
		StringBuffer sb = new StringBuffer();
		sb.append("\t");
		sb.append(" type:"+type);
		sb.append(" user:"+user);
		sb.append(" sys:"+sys);
		sb.append(" real:"+real);
		logger.debug(sb.toString());
	}
}