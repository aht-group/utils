package org.jeesl.factory.builder.module;

import org.jeesl.api.facade.module.JeeslCalendarFacade;
import org.jeesl.controller.handler.module.CalendarItemHandler;
import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.module.calendar.EjbTimeZoneFactory;
import org.jeesl.factory.txt.module.calendar.TxtCalendarItemFactory;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarFactoryBuilder<L extends JeeslLang,
									D extends JeeslDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends JeeslStatus<L,D,CT>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends JeeslStatus<L,D,IT>>
	extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(CalendarFactoryBuilder.class);
	
	private final Class<ZONE> cZone; public Class<ZONE> getClassZone()	{return cZone;}
	private final Class<IT> cItemType; public Class<IT> getClassItemType()	{return cItemType;}

	public CalendarFactoryBuilder(final Class<L> cL,final Class<D> cD,final Class<ZONE> cZone, final Class<IT> cItemType)
	{       
		super(cL,cD);
        this.cZone = cZone;
        this.cItemType = cItemType;
	}
	
	public EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> ejbZone(){return new EjbTimeZoneFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT>(cZone);}
	
	public TxtCalendarItemFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> txtItem() {return new TxtCalendarItemFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT>();}
	
	public CalendarItemHandler<L,D,CALENDAR,ZONE,CT,ITEM,IT> itemHandler(final JeeslCalendarFacade<L,D,CALENDAR,ZONE,CT,ITEM,IT> fCalendar){return new CalendarItemHandler<L,D,CALENDAR,ZONE,CT,ITEM,IT>(fCalendar,this);}
}