package org.jeesl.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class JeeslXhtmlParser
{
	final static Logger logger = LoggerFactory.getLogger(JeeslXhtmlParser.class);
	private File src;
	private XMLReader xmlReader;
	List<String> parseStats; public List<String> getStats() {return parseStats;}
	private int count; public int getCount() {return count;}
	public JeeslXhtmlParser(String pathToXhtmlFolder) throws ParserConfigurationException, SAXException
	{
		parseStats = new ArrayList<>();
		SAXParserFactory spf = SAXParserFactory.newInstance();
	    spf.setNamespaceAware(true);
	    SAXParser saxParser;
		saxParser = spf.newSAXParser();
		xmlReader = saxParser.getXMLReader();
	    xmlReader.setContentHandler(new DefaultHandler());
	    src = new File(pathToXhtmlFolder);
	}

	public void parse()
	{
		count=0;
		if(xmlReader!=null && src!=null)
		{
			parseFiles(src.listFiles(),xmlReader);
		}
	}

	 private void parseFiles(File[] files,XMLReader xmlReader)
	 {
        for (File file : files)
        {
            if (file.isDirectory())
            {
            	parseFiles(file.listFiles(),xmlReader); // Calls recursive method.
            }
            else
            {
            	if(file.getAbsolutePath().endsWith(".xhtml"))
            	{
	            	count++;
	            	try
					{
						xmlReader.parse(new InputSource(file.getAbsolutePath()));
					}
	            	catch (IOException | SAXException e)
					{
	            		parseStats.add(e.toString());
					}
            	}
            }
        }
    }

}
