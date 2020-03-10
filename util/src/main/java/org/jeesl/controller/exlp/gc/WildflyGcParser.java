package org.jeesl.controller.exlp.gc;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jeesl.util.KeyValuePair;
import org.jeesl.util.Tree;
import org.jeesl.util.Tree.TraversalMode;
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
//		pattern.add(Pattern.compile(PatternLibrary.eximDate+"T"+PatternLibrary.eximTime+"\\.([0-9]+)(.*)"));
	}

	public void parseLine(String line)
	{
		Tree<KeyValuePair<String, String>> gcLogEntry = parseStatements(line);
		
		try {
			if (gcLogEntry.getData() == null || !gcLogEntry.getData().getKey().contentEquals("TimeStamp")) { throw new ParseException("The line does not exhibit the proper format", 0); }
			
			String[] dateTimeInformation = gcLogEntry.getData().getValue().split("\\|");
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
			
			String gcEventType = gcLogEntry.getChildren().stream().findFirst().orElse(new Tree<KeyValuePair<String, String>>(new KeyValuePair<String, String>("", ""))).getData().getKey().trim();
			if (gcEventType.isEmpty()) { gcEventType = "N/A"; }
			
			float userTime = -1, sysTime = -1, realTime = -1;
			KeyValuePair<String, String> timesStatement = gcLogEntry.find(node -> node.getKey().contentEquals("Times"), TraversalMode.FULL).stream().findFirst().orElse(null);
			
			if (timesStatement != null)
			{
				Matcher timeMatcher = Pattern.compile(PatternLibrary.gcTimeElementPattern).matcher(timesStatement.getValue());
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
	
	private Tree<KeyValuePair<String, String>> parseStatements(String line)
	{
		Tree<KeyValuePair<String, String>> root = new Tree<KeyValuePair<String, String>>();
		
		Matcher matcher = Pattern.compile(PatternLibrary.gcDateTimePattern).matcher(line);
		if (matcher.matches())
		{
			root.setData(new KeyValuePair<String, String>("TimeStamp", String.format("%s|%s", matcher.group(1).replaceAll("[T:-]", "."), matcher.group(2))));
			
			line = String.format("[%s]", matcher.group(3));
			List<Integer> bracketIndices = findBracketIndices(line);
			
			root.addChildren(extractStatement(line, bracketIndices, 0).getChildren());
			
			root.forEach(node -> node.setValue(node.getValue().replace(node.getKey(), "").replaceFirst("^:?\\s?", "").trim().replaceAll("(\\s{2,}|\\s,\\s)", "")), TraversalMode.FULL);
		}
		
		return root;
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
	
	private Tree<KeyValuePair<String, String>> extractStatement(String line, List<Integer> bracketIndices, Integer openBracketListPosition)
	{
		Tree<KeyValuePair<String, String>> node = new Tree<KeyValuePair<String, String>>();
		String key = "", value = "";
		
		int closedBracketListPosition = openBracketListPosition + 1;
		
		while (line.charAt(bracketIndices.get(closedBracketListPosition)) == '[')
		{
			key = key.isEmpty() ? line.substring(bracketIndices.get(openBracketListPosition) + 1, bracketIndices.get(closedBracketListPosition)).trim() : key;
			node.addChild(extractStatement(line, bracketIndices, closedBracketListPosition));
			closedBracketListPosition = openBracketListPosition + node.countChildren(false) * 2 + 1;
		}
		value = line.substring(bracketIndices.get(openBracketListPosition) + 1, bracketIndices.get(closedBracketListPosition));
		for (Tree<KeyValuePair<String, String>> child : node.getChildren())
		{
			value = value.replace(String.format("[%s]", child.getData().getValue()), "");
		}
		
		if (value.indexOf(":") >= 0)
		{
			key = value.split(":")[0];
		}
		
		node.setData(new KeyValuePair<String, String>(key, value));
		return node;
	}

	public void event(ZonedDateTime timeStamp, String eventType, float userTime, float sysTime, float realTime)
	{
		leh.handleEvent(new GcEvent(Date.from(timeStamp.toInstant()), eventType, userTime, sysTime, realTime));
	}
}