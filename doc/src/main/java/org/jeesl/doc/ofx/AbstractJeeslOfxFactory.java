package org.jeesl.doc.ofx;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.model.json.system.translation.JsonTranslation;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.table.OfxHeadFactory;
import org.openfuxml.interfaces.configuration.TranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class AbstractJeeslOfxFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslOfxFactory.class);
	
	protected TranslationProvider tp;
	protected List<String> localeCodes;
	protected List<String> tableHeaderKeys;
	
	protected final List<JsonTranslation> tableHeaders;
	
	protected Translations translations;
//	protected String imagePathPrefix;
	
	protected final OfxMultiLangFactory<L> ofxMultiLang;
	
	@Deprecated
	public AbstractJeeslOfxFactory(List<String> localeCodes, Translations translations)
	{
		this.localeCodes=localeCodes;
		this.translations=translations;
		
		tableHeaderKeys = new ArrayList<String>();
		tableHeaders = new ArrayList<JsonTranslation>();
		
		ofxMultiLang = new OfxMultiLangFactory<L>(localeCodes);
	}
	
	public AbstractJeeslOfxFactory(TranslationProvider tp)
	{	
		this.tp=tp;
		tableHeaderKeys = new ArrayList<String>();
		tableHeaders = new ArrayList<JsonTranslation>();
		ofxMultiLang = new OfxMultiLangFactory<L>(tp.getLocaleCodes());
	}
	
	protected <E extends Enum<E>>void addHeaderKey(Class<?> c)
	{
		JsonTranslation json = new JsonTranslation();
		json.setEntity(c.getSimpleName());
		tableHeaders.add(json);
	}
	protected <E extends Enum<E>>void addHeaderKey(Class<?> c, E code)
	{
		JsonTranslation json = new JsonTranslation();
		json.setEntity(c.getSimpleName());
		json.setCode(code.toString());
		tableHeaders.add(json);
	}
	
	protected <E extends Enum<E>>void addHeaderKey(E code)
	{
		tableHeaderKeys.add(code.toString());
	}
	
	protected Head buildHead()
	{
		Row row = new Row();
		logger.info("Building Head with keys:"+tableHeaderKeys.size()+" locales:"+tp.getLocaleCodes().size());
		for(String headerKey : tableHeaderKeys)
		{
			Cell cell = OfxCellFactory.build();
			for(String localeCode : tp.getLocaleCodes())
			{
				cell.getContent().add(XmlParagraphFactory.build(localeCode,tp.toTranslation(localeCode, headerKey)));
			}
			row.getCell().add(cell);
		}
		
		return OfxHeadFactory.build(row);
	}
	
	protected Head buildTableHeader()
	{
		Row row = new Row();
		logger.info("Building Head with keys:"+tableHeaderKeys.size()+" locales:"+tp.getLocaleCodes().size());
		for(JsonTranslation json : tableHeaders)
		{
			Cell cell = OfxCellFactory.build();
			for(String localeCode : tp.getLocaleCodes())
			{
				cell.getContent().add(XmlParagraphFactory.build(localeCode,tp.toTranslation(localeCode,json.getEntity(),json.getCode())));
			}
			row.getCell().add(cell);
		}
		
		return OfxHeadFactory.build(row);
	}
	
	@Deprecated
	protected Row createHeaderRow(List<String> headerKeys)
	{
		Row row = new Row();
		for(String headerKey : headerKeys)
		{
			Cell cell = OfxCellFactory.build();
			for(String lang : localeCodes)
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