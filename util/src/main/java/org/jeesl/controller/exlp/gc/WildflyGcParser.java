package org.jeesl.controller.exlp.gc;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.interfaces.util.PatternLibrary;

public class WildflyGcParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(WildflyGcParser.class);
	
	public WildflyGcParser(LogEventHandler leh)
	{
		super(leh);
		pattern.add(Pattern.compile(PatternLibrary.eximDate+"T"+PatternLibrary.eximTime+"\\.([0-9]+)(.*)"));
	}

	public void parseLine(String line)
	{
		logger.info(line);
		boolean matched = false;
		for(int i=0;i<pattern.size();i++)
		{
			Matcher m=pattern.get(i).matcher(line);
			if(m.matches())
			{
				matched=true;
				switch(i)
				{
					case 0: event(m.group(1),m.group(2),m.group(3),m.group(4),m.group(5),m.group(6),m.group(7));break;
				}
				i=pattern.size();
			}
		}
		if(!matched){logger.warn("Unknown Pattern: "+line);}
	}
	
	public void event(String year, String month, String day, String hour, String min, String sec, String ms)
	{
		Date record = net.sf.exlp.util.DateUtil.getDateFromString(year,month,day,hour,min,sec);
		GcEvent event = new GcEvent(record);
		leh.handleEvent(event);
	}
}