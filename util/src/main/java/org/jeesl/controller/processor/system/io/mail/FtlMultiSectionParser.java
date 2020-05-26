package org.jeesl.controller.processor.system.io.mail;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtlMultiSectionParser
{
	final static Logger logger = LoggerFactory.getLogger(FtlMultiSectionParser.class);
	
	public Map<String,String> parse(String text)
	{
		return parse(text, "\\\\n");
	}
	
	public Map<String,String> parse(String text, String delimiter)
	{
		Map<String,String> result = new HashMap<>();
		
		String[] lines = text.split(delimiter);
		String keyword = "";
		StringBuilder content = new StringBuilder();

		for(String line : lines) {
			if (line.startsWith("##")) {
				if (!keyword.isEmpty() && content.length() > 0) {
					result.put(keyword,content.toString());
				}
				content.setLength(0);
				
				keyword = line.substring(2);
			} else if (!line.isEmpty()) {
				content.append("<p>");
				content.append(line);
				content.append("</p>");
			}
		}
		if (!keyword.isEmpty() && content.length() > 0) {
			result.put(keyword,content.toString());
		}
		
		return result;
	}
}