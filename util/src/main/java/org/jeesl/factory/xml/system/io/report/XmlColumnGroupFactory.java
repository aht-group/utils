package org.jeesl.factory.xml.system.io.report;

import java.util.Collections;
import java.util.Comparator;

import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.util.JeeslTrafficLight;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.util.comparator.ejb.system.io.report.IoReportColumnComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.report.ColumnGroup;

public class XmlColumnGroupFactory <L extends JeeslLang,D extends JeeslDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XmlColumnGroupFactory.class);
	
	private ColumnGroup q;
	
	private Comparator<COLUMN> cColumn;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	private XmlColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION> xfColumn;
	private XmlLayoutFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS> xfLayout;
	
	public XmlColumnGroupFactory(String localeCode, ColumnGroup q)
	{
		this.q=q;
		cColumn = new IoReportColumnComparator<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>().factory(IoReportColumnComparator.Type.position);
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
		if(q.isSetXlsColumn()){xfColumn = new XmlColumnFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION>(localeCode,q.getXlsColumn().get(0));}
		if(q.isSetLayout()){xfLayout = new XmlLayoutFactory<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS>(localeCode,q.getLayout());}
	}
	
	public ColumnGroup build(GROUP group)
	{
		ColumnGroup xml = new ColumnGroup();
		
		if(q.isSetCode()){xml.setCode(group.getCode());}
		if(q.isSetVisible()){xml.setVisible(group.isVisible());}
		if(q.isSetPosition()){xml.setPosition(group.getPosition());}
		
		if(q.isSetShowLabel()){xml.setShowLabel(group.getShowLabel());}
		if(q.isSetShowWeb()){xml.setShowWeb(group.getShowWeb());}
		
		if(q.isSetQuery()){xml.setQuery(group.getQueryColumns());}
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(group.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(group.getDescription()));}
		if(q.isSetLayout()){xml.setLayout(xfLayout.build(group));}
		
		if(q.isSetXlsColumn())
		{
			Collections.sort(group.getColumns(),cColumn);
			for(COLUMN column : group.getColumns())
			{
				xml.getXlsColumn().add(xfColumn.build(column));
			}
		}
		
		return xml;
	}
}