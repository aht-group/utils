package org.jeesl.controller.facade.system.io;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.io.JeeslIoReportFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
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

import net.sf.ahtutils.controller.util.ParentPredicate;

public class JeeslIoReportFacadeBean<L extends JeeslLang,D extends JeeslDescription,
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
									STYLE extends JeeslReportStyle<L,D>,
									CDT extends JeeslStatus<CDT,L,D>,CW extends JeeslStatus<CW,L,D>,
									RT extends JeeslStatus<RT,L,D>,
									ENTITY extends EjbWithId,
									ATTRIBUTE extends EjbWithId,
									TL extends JeeslTrafficLight<L,D,TLS>,
									TLS extends JeeslStatus<TLS,L,D>,
									FILLING extends JeeslStatus<FILLING,L,D>,
									TRANSFORMATION extends JeeslStatus<TRANSFORMATION,L,D>>
					extends JeeslFacadeBean
					implements JeeslIoReportFacade<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE,TL,TLS,FILLING,TRANSFORMATION>
{	
	private final Class<CATEGORY> cCategory;
	private final Class<REPORT> cReport;
	private final Class<WORKBOOK> cWorkbook;
	private final Class<SHEET> cSheet;
	private final Class<GROUP> cGroup;
	private final Class<COLUMN> cColumn;
	private final Class<ROW> cRow;
	private final Class<TEMPLATE> cTemplate;
	private final Class<CELL> cCell;
	
	public JeeslIoReportFacadeBean(EntityManager em, final Class<CATEGORY> cCategory, final Class<REPORT> cReport, final Class<WORKBOOK> cWorkbook, final Class<SHEET> cSheet, final Class<GROUP> cGroup, final Class<COLUMN> cColumn, final Class<ROW> cRow, final Class<TEMPLATE> cTemplate, final Class<CELL> cCell)
	{
		super(em);
		this.cCategory=cCategory;
		this.cReport=cReport;
		this.cWorkbook=cWorkbook;
		this.cSheet=cSheet;
		this.cGroup=cGroup;
		this.cColumn=cColumn;
		this.cRow=cRow;
		this.cTemplate=cTemplate;
		this.cCell=cCell;
	}
	
	@Override public REPORT load(REPORT report, boolean recursive)
	{
		report = em.find(cReport, report.getId());
		if(report.getWorkbook()!=null)
		{
			report.getWorkbook().getSheets().size();
			
			if(recursive)
			{
				for(SHEET sheet : report.getWorkbook().getSheets())
				{
					for(GROUP group : sheet.getGroups()){group.getColumns().size();}
				}
			}
		}
		return report;
	}
	
	@Override public WORKBOOK load(WORKBOOK workbook)
	{
		workbook = em.find(cWorkbook, workbook.getId());
		workbook.getSheets().size();
		return workbook;
	}
	
	@Override public SHEET load(SHEET sheet, boolean recursive)
	{
		sheet = em.find(cSheet, sheet.getId());
		sheet.getGroups().size();
		sheet.getRows().size();
		if(recursive)
		{
			for(GROUP group : sheet.getGroups()){group.getColumns().size();}
		}
		return sheet;
	}
	
	@Override public GROUP load(GROUP group)
	{
		group = em.find(cGroup, group.getId());
		group.getColumns().size();
		return group;
	}
	
	@Override public TEMPLATE load(TEMPLATE template)
	{
		template = em.find(cTemplate, template.getId());
		template.getCells().size();
		return template;
	}
	
	@Override public SHEET fSheet(WORKBOOK workbook, String code) throws JeeslNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<SHEET> cQ = cB.createQuery(cSheet);
        
		Root<SHEET> sheet = cQ.from(cSheet);
        
        Path<WORKBOOK> pWorkbook = sheet.get(JeeslReportSheet.Attributes.workbook.toString());
        Expression<String> eCode = sheet.get(JeeslReportSheet.Attributes.code.toString());
       
        CriteriaQuery<SHEET> select = cQ.select(sheet);
	    select.where(cB.and(cB.equal(pWorkbook,workbook),cB.equal(eCode,code)));

		TypedQuery<SHEET> q = em.createQuery(cQ); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("Nothing found "+cSheet.getSimpleName()+" workbook:"+workbook.toString()+" code:"+code);}
	}
	
	@Override public void rmSheet(SHEET sheet) throws JeeslConstraintViolationException
	{
		sheet = em.find(cSheet, sheet.getId());
		sheet.getWorkbook().getSheets().remove(sheet);
		this.rmProtected(sheet);
	}
	
	@Override public void rmGroup(GROUP group) throws JeeslConstraintViolationException
	{
		group = em.find(cGroup, group.getId());
		group.getSheet().getGroups().remove(group);
		this.rmProtected(group);
	}
	
	@Override public void rmColumn(COLUMN column) throws JeeslConstraintViolationException
	{
		column = em.find(cColumn, column.getId());
		column.getGroup().getColumns().remove(column);
		this.rmProtected(column);
	}
	
	@Override public void rmRow(ROW row) throws JeeslConstraintViolationException
	{
		row = em.find(cRow, row.getId());
		row.getSheet().getRows().remove(row);
		this.rmProtected(row);
	}
	
	@Override public void rmCell(CELL cell) throws JeeslConstraintViolationException
	{
		cell = em.find(cCell, cell.getId());
		cell.getTemplate().getCells().remove(cell);
		this.rmProtected(cell);
	}
	
	@Override public List<REPORT> fReports(List<CATEGORY> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<CATEGORY>> ppCategory = ParentPredicate.createFromList(cCategory,"category",categories);
		return allForOrParents(cReport,ppCategory);
	}
}