package net.sf.ahtutils.doc.ofx;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Row;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractUtilsOfxDocumentationFactory.class);
		
	protected Configuration config;
	protected String[] langs;
	protected Translations translations;
	
	public AbstractUtilsOfxDocumentationFactory(Configuration config, String[] langs, Translations translations)
	{
		this.config=config;
		this.langs=langs;
		this.translations=translations;
	}
	
	@Deprecated
	protected Row createHeaderRow(String[] headerKeys)
	{
		List<String> keys = new ArrayList<String>();
		for(String key : headerKeys){keys.add(key);}
		return createHeaderRow(keys);
	}
	
	protected Row createHeaderRow(List<String> headerKeys)
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			Cell cell = OfxCellFactory.build();
			for(String lang : langs)
			{
				StringBuffer sb = new StringBuffer();
				if(headerKey.length()>0)
				{
					try
					{
						sb.append(StatusXpath.getLang(translations, headerKey, lang).getTranslation());
					}
					catch (ExlpXpathNotFoundException e)
					{
						sb.append(e.getMessage());
						logger.warn(sb.toString());
					}
					catch (ExlpXpathNotUniqueException e)
					{
						sb.append(e.getMessage());
						logger.warn(sb.toString());
					}
				}
				else
				{
					sb.append("");
				}
				Paragraph p = new Paragraph();
				p.setLang(lang);
				p.getContent().add(sb.toString());
				cell.getContent().add(p);
			}
			
			row.getCell().add(cell);
		}
		return row;
	}
}