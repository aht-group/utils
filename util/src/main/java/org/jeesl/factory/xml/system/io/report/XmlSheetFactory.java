package org.jeesl.factory.xml.system.io.report;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.report.ColumnGroup;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;

public class XmlSheetFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT>,
								CDT extends UtilsStatus<CDT,L,D>,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSheetFactory.class);
	
	private XlsSheet q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,FILLING,TRANSFORMATION> xfGroup;

	public XmlSheetFactory(String localeCode, XlsSheet q)
	{
		this.q=q;
		if(getLangs(q)!=null){xfLangs = new XmlLangsFactory<L>(getLangs(q));}
		if(getDescriptions(q)!=null){xfDescriptions = new XmlDescriptionsFactory<D>(getDescriptions(q));}
		if(getColumnGroup(q)!=null){xfGroup = new XmlColumnGroupFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,CDT,FILLING,TRANSFORMATION>(localeCode,getColumnGroup(q));}
	}
	
	public XlsSheet build(SHEET sheet)
	{
		XlsSheet xml = new XlsSheet();
		
		if(q.isSetCode()){xml.setCode(sheet.getCode());}
		if(q.isSetVisible()){xml.setVisible(sheet.isVisible());}
		if(q.isSetPosition()){xml.setPosition(sheet.getPosition());}
		
		if(getLangs(q)!=null){xml.getContent().add(xfLangs.getUtilsLangs(sheet.getName()));}
		if(getDescriptions(q)!=null){xml.getContent().add(xfDescriptions.create(sheet.getDescription()));}
		
		if(getColumnGroup(q)!=null)
		{
			for(GROUP g : sheet.getGroups())
			{
				xml.getContent().add(xfGroup.build(g));
			}
		}
		
		return xml;
	}
	
	private Langs getLangs(XlsSheet q)
	{
		for(Serializable s : q.getContent())
		{
			if(s instanceof Langs){return (Langs)s;}
		}
		return null;
	}
	
	private Descriptions getDescriptions(XlsSheet q)
	{
		for(Serializable s : q.getContent())
		{
			if(s instanceof Descriptions){return (Descriptions)s;}
		}
		return null;
	}
	
	private ColumnGroup getColumnGroup(XlsSheet w)
	{
		for(Serializable s : q.getContent())
		{
			if(s instanceof ColumnGroup){return (ColumnGroup)s;}
		}
		return null;
	}
}