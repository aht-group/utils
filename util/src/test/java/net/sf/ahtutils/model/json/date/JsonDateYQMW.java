package net.sf.ahtutils.model.json.date;

import org.jeesl.interfaces.model.with.date.EjbWithYear;
import org.jeesl.interfaces.model.with.date.EntityWithMonth;
import org.jeesl.interfaces.model.with.date.EntityWithQuarter;
import org.jeesl.interfaces.model.with.date.EntityWithWeek;

public class JsonDateYQMW implements EjbWithYear,EntityWithQuarter,EntityWithMonth,EntityWithWeek
{
	private int year;
	@Override public int getYear() {return year;}
	@Override public void setYear(int year) {this.year = year;}
	
	private String quarter;
	@Override public String getQuarter() {return quarter;}
	@Override public void setQuarter(String quarter) {this.quarter = quarter;}

	private int month;
	@Override public int getMonth() {return month;}
	@Override public void setMonth(int month) {this.month = month;}
	
	private int week;
	@Override public int getWeek() {return week;}
	@Override public void setWeek(int week) {this.week = week;}
}