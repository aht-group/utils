package org.jeesl.factory.xml.module.calendar;

import java.util.Date;

import org.jeesl.controller.processor.TimeZoneProcessor;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendar;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarItem;
import org.jeesl.interfaces.model.module.calendar.JeeslCalendarTimeZone;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.model.xml.module.calendar.Item;
import org.jeesl.util.query.xml.module.XmlCalendarQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.DateUtil;

public class XmlCalendarItemFactory <L extends JeeslLang, D extends JeeslDescription,
									CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									CT extends JeeslStatus<L,D,CT>,
									ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
									IT extends JeeslStatus<L,D,IT>
									>
{
	final static Logger logger = LoggerFactory.getLogger(XmlCalendarItemFactory.class);
	
	private final Item q;
	private final TimeZoneProcessor tzp;
	@SuppressWarnings("unused")
	private XmlTypeFactory<L,D,IT> xfType;
	
	
	public XmlCalendarItemFactory(String localeCode, Item q, TimeZoneProcessor tzp)
	{
		this.q=q;
		this.tzp=tzp;
		if(q.isSetType()){xfType = new XmlTypeFactory<>(localeCode,q.getType());}
	}
	
	public static <L extends JeeslLang, D extends JeeslDescription,
					CALENDAR extends JeeslCalendar<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
					ZONE extends JeeslCalendarTimeZone<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
					CT extends JeeslStatus<L,D,CT>,
					ITEM extends JeeslCalendarItem<L,D,CALENDAR,ZONE,CT,ITEM,IT>,
					IT extends JeeslStatus<L,D,IT>
					>
	XmlCalendarItemFactory<L,D,CALENDAR,ZONE,CT,ITEM,IT> instance(String localeCode, XmlCalendarQuery.Key key)
	{
		return new XmlCalendarItemFactory<>(localeCode,XmlCalendarQuery.get(key, localeCode).getItem(),null);
	}
	
	public Item build(ITEM item)
	{
		Item xml = build();		
		if(q.isSetType()){xml.setType(xfType.build(item.getType()));}
		
		
		if(tzp==null){xml.setStart(DateUtil.getXmlGc4D(item.getStartDate()));}
		else{xml.setStart(DateUtil.getXmlGc4D(tzp.project(item.getStartDate())));}
		
		if(tzp==null){xml.setEnd(DateUtil.getXmlGc4D(item.getEndDate()));}
		else{xml.setEnd(DateUtil.getXmlGc4D(tzp.project(item.getEndDate())));}
		
		xml.setAllDay(item.isAllDay());
		
		return xml;
	}
	
	public static Item build()
	{
		Item xml = new Item();		
		return xml;
	}
	
	public static Item build(Date date)
	{
		Item xml = build();		
		xml.setStart(DateUtil.getXmlGc4D(date));
		xml.setEnd(DateUtil.getXmlGc4D(date));
		xml.setAllDay(true);
		return xml;
	}
}