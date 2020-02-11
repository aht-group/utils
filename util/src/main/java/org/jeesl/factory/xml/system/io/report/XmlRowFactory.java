package org.jeesl.factory.xml.system.io.report;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlDataTypeFactory;
import org.jeesl.factory.xml.system.status.XmlTypeFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportQueryType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Queries;
import net.sf.ahtutils.xml.report.Row;

public class XmlRowFactory <L extends JeeslLang,D extends JeeslDescription,
								CATEGORY extends JeeslStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,WORKBOOK>,
								IMPLEMENTATION extends JeeslStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<REPORT,SHEET>,
								SHEET extends JeeslReportSheet<L,D,IMPLEMENTATION,WORKBOOK,GROUP,ROW>,
								GROUP extends JeeslReportColumnGroup<L,D,SHEET,COLUMN,STYLE>,
								COLUMN extends JeeslReportColumn<L,D,GROUP,STYLE,CDT,CW,TLS>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								TEMPLATE extends JeeslReportTemplate<L,D,CELL>,
								CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>,
								STYLE extends JeeslReportStyle<L,D>,CDT extends JeeslStatus<CDT,L,D>,
								CW extends JeeslStatus<CW,L,D>,
								RT extends JeeslStatus<RT,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								TL extends JeeslTrafficLight<L,D,TLS>,
								TLS extends JeeslStatus<TLS,L,D>,
								FILLING extends JeeslStatus<FILLING,L,D>,
								TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(XmlRowFactory.class);
	
	private Row q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlTypeFactory<L,D,RT> xfType;
	private XmlDataTypeFactory<CDT,L,D> xfDataType;
	private XmlLayoutFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfLayout;
	private XmlTemplateFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfTemplate;
	
	public XmlRowFactory(String localeCode, Row q)
	{
		this.q=q;
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
		if(q.isSetType()){xfType = new XmlTypeFactory<>(localeCode,q.getType());}
		if(q.isSetDataType()){xfDataType = new XmlDataTypeFactory<CDT,L,D>(localeCode,q.getDataType());}
		if(q.isSetLayout()){xfLayout = new XmlLayoutFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>(localeCode,q.getLayout());}
		if(q.isSetTemplate()){xfTemplate = new XmlTemplateFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>(q.getTemplate());}
	}
	
	public Row build(ROW row)
	{
		Row xml = new Row();
		
		if(q.isSetCode()){xml.setCode(row.getCode());}
		if(q.isSetVisible()){xml.setVisible(row.isVisible());}
		if(q.isSetPosition()){xml.setPosition(row.getPosition());}
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(row.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(row.getDescription()));}
		if(q.isSetType()){xml.setType(xfType.build(row.getType()));}
		if(q.isSetDataType() && row.getDataType()!=null){xml.setDataType(xfDataType.build(row.getDataType()));}
		if(q.isSetTemplate() && row.getTemplate()!=null){xml.setTemplate(xfTemplate.build(row.getTemplate()));}
		
		if(q.isSetQueries()){xml.setQueries(queries(row));}
		if(q.isSetLayout()){xml.setLayout(xfLayout.build(row));}
						
		return xml;
	}
	
	private Queries queries(ROW row)
	{
		Queries xml = XmlQueriesFactory.build();
		if(row.getQueryCell()!=null){xml.getQuery().add(XmlQueryFactory.build(JeeslReportQueryType.Row.cell, row.getQueryCell()));}
		return xml;
	}
}