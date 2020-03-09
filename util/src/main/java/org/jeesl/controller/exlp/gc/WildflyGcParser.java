package org.jeesl.controller.exlp.gc;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	private class Statement
	{
		public String name = "";
		public String value;
		public List<Statement> subStatements = new ArrayList<Statement>();
		
		public int countNestedStatements()
		{
			return subStatements.stream().mapToInt(child -> 1 + child.countNestedStatements()).sum();
		}
		
		public Statement findStatement(String name)
		{
			if (this.name.contentEquals(name))
			{
				return this;
			}
			return this.subStatements.stream().map(child -> child.findStatement(name)).filter(child -> child != null).findFirst().orElse(null);
		}
		
		public void cleanValues()
		{
			this.value = this.value.replace(this.name, "").replaceFirst("^:?\\s?", "").trim().replaceAll("(\\s{2,}|\\s,\\s)", " ");
			this.subStatements.forEach(child -> child.cleanValues());
		}
	}
	
	final static Logger logger = LoggerFactory.getLogger(WildflyGcParser.class);
	
	public WildflyGcParser(LogEventHandler leh)
	{
		super(leh);
//		pattern.add(Pattern.compile(PatternLibrary.eximDate+"T"+PatternLibrary.eximTime+"\\.([0-9]+)(.*)"));
	}

	public void parseLine(String line)
	{
//		logger.info(line);
//		boolean matched = false;
//		for(int i=0;i<pattern.size();i++)
//		{
//			Matcher m=pattern.get(i).matcher(line);
//			if(m.matches())
//			{
//				matched=true;
//				switch(i)
//				{
//					case 0: event(m.group(1),m.group(2),m.group(3),m.group(4),m.group(5),m.group(6),m.group(7));break;
//				}
//				i=pattern.size();
//			}
//		}
//		if(!matched){logger.warn("Unknown Pattern: "+line);}
		
		Statement statement = parseStatements(line);
		
		try {
			if (!statement.name.contentEquals("TimeStamp")) { throw new ParseException("The line does not exhibit the proper format", 0); }
			
			String[] dateTimeInformation = statement.value.split("\\|");
			ZoneId timeZone = ZoneId.of(dateTimeInformation[1]);
			dateTimeInformation = dateTimeInformation[0].split("\\.");
		
			ZonedDateTime timeStamp = ZonedDateTime.of(Integer.parseInt(dateTimeInformation[0]),								// year
													   Integer.parseInt(dateTimeInformation[1]), 								// month
													   Integer.parseInt(dateTimeInformation[2]), 								// day
													   Integer.parseInt(dateTimeInformation[3]), 								// hours
													   Integer.parseInt(dateTimeInformation[4]), 								// minutes
													   Integer.parseInt(dateTimeInformation[5]), 								// seconds
													   Integer.parseInt(String.format("%s000000", dateTimeInformation[6])), 	// nanoseconds
													   timeZone); 																// zone
			
			String gcEventType = statement.subStatements.stream().findFirst().orElse(new Statement()).name.trim();
			if (gcEventType.isEmpty()) { gcEventType = "N/A"; }
			
			float userTime = -1, sysTime = -1, realTime = -1;
			Statement timesStatement = statement.findStatement("Times");
			
			if (timesStatement != null)
			{
				Matcher timeMatcher = Pattern.compile(PatternLibrary.gcTimeElementPattern).matcher(timesStatement.value);
				while (timeMatcher.find())
				{
					switch (timeMatcher.group("name"))
					{
						case "user":
							userTime = Float.parseFloat(timeMatcher.group("value"));
							break;
						case "sys":
							sysTime = Float.parseFloat(timeMatcher.group("value"));
							break;
						case "real":
							realTime = Float.parseFloat(timeMatcher.group("value"));
							break;
					}
				}
			}
			
			event(timeStamp, gcEventType, userTime, sysTime, realTime);
		}
		catch (NumberFormatException | ParseException exception)
		{
			logger.warn(String.format("Improper format: %s", line));
			return;
		}
	}
	
	private Statement parseStatements(String line)
	{
		Statement statement = new Statement();
		
		Matcher matcher = Pattern.compile(PatternLibrary.gcDateTimePattern).matcher(line);
		if (matcher.matches())
		{
			statement.name = "TimeStamp";
			statement.value = String.format("%s|%s", matcher.group(1).replaceAll("[T:-]", "."), matcher.group(2));
			
			line = String.format("[%s]", matcher.group(3));
			List<Integer> bracketIndices = findBracketIndices(line);
			
			statement.subStatements.addAll(extractStatement(line, bracketIndices, 0).subStatements);
			statement.cleanValues();
		}
		
		return statement;
	}

	private List<Integer> findBracketIndices(String line)
	{
		List<Integer> bracketIndices = new ArrayList<Integer>();
		Matcher regexMatcher = Pattern.compile("(\\[|\\])").matcher(line);
		
		while (regexMatcher.find())
		{
			bracketIndices.add(regexMatcher.start());
		}
		
		return bracketIndices;
	}
	
	private Statement extractStatement(String line, List<Integer> bracketIndices, Integer openBracketListPosition)
	{
		Statement statement = new Statement();
		
		int closedBracketListPosition = openBracketListPosition + 1;
		
		while (line.charAt(bracketIndices.get(closedBracketListPosition)) == '[')
		{
			statement.name = statement.name.isEmpty() ? line.substring(bracketIndices.get(openBracketListPosition) + 1, bracketIndices.get(closedBracketListPosition)).trim() : statement.name;
			statement.subStatements.add(extractStatement(line, bracketIndices, closedBracketListPosition));
			closedBracketListPosition = openBracketListPosition + statement.countNestedStatements() * 2 + 1;
		}
		statement.value = line.substring(bracketIndices.get(openBracketListPosition) + 1, bracketIndices.get(closedBracketListPosition));
		statement.subStatements.forEach(child -> statement.value = statement.value.replace(String.format("[%s]", child.value), ""));
		
		if (statement.value.indexOf(":") >= 0)
		{
			String[] statementContent = statement.value.split(":");
			statement.name = statementContent[0];
		}
		
		return statement;
	}

	public void event(ZonedDateTime timeStamp, String eventType, float userTime, float sysTime, float realTime)
	{
		leh.handleEvent(new GcEvent(Date.from(timeStamp.toInstant()), eventType, userTime, sysTime, realTime));
	}
}