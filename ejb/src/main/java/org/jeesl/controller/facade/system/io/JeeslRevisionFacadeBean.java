package org.jeesl.controller.facade.system.io;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.factory.json.system.revision.JsonRevisionFactory;
import org.jeesl.interfaces.model.system.io.revision.EjbWithRevisionAttributes;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionCategory;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.core.JeeslRevisionViewMapping;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.er.JeeslRevisionDiagram;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.util.query.sql.SqlNativeQueryHelper;
import org.jeesl.util.query.sql.SqlRevisionQueries;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.json.system.revision.JsonRevision;

public class JeeslRevisionFacadeBean<L extends JeeslLang,D extends JeeslDescription,
									RC extends JeeslRevisionCategory<L,D,RC,?>,	
									RV extends JeeslRevisionView<L,D,RVM>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									RS extends JeeslRevisionScope<L,D,RC,RA>,
									RST extends JeeslStatus<RST,L,D>,
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA,ERD>,
									REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
									RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
									RER extends JeeslStatus<RER,L,D>,
									RAT extends JeeslStatus<RAT,L,D>,
									ERD extends JeeslRevisionDiagram<L,D,RC>>
					extends JeeslFacadeBean
					implements JeeslIoRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD>
{
	private static final long serialVersionUID = 1L;
	
	private final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD> fbRevision;
	
	private String revisionPrefix;
	private String revisionTable;

	public JeeslRevisionFacadeBean(EntityManager em, final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD> fbRevision)
	{
		this("_at_","auditinfo",em,fbRevision);
	}
	
	public JeeslRevisionFacadeBean(String revisionPrefix, String revisionTable, EntityManager em,
								final IoRevisionFactoryBuilder<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT,ERD> fbRevision)
	{
		super(em);
		this.fbRevision=fbRevision;
		this.revisionPrefix=revisionPrefix;
		this.revisionTable=revisionTable;
	}
	
	@Override public RV load(Class<RV> cView, RV view)
	{
		view = em.find(cView, view.getId());
		view.getMaps().size();
		return view;
	}
	
	@Override public RS load(Class<RS> cScope, RS scope)
	{
		scope = em.find(cScope, scope.getId());
		scope.getAttributes().size();
		return scope;
	}

	@Override public RE load(Class<RE> cEntity, RE entity)
	{
		entity = em.find(cEntity, entity.getId());
		entity.getAttributes().size();
		entity.getMaps().size();
		return entity;
	}
	
	@Override public List<RS> findRevisionScopes(List<RC> categories, boolean showInvisibleScopes)
	{
		List<ParentPredicate<RC>> ppCategory = ParentPredicate.createFromList(fbRevision.getClassCategory(),"category",categories);
		return allForOrParents(fbRevision.getClassScope(),ppCategory);
	}
	
	@Override public List<RE> findRevisionEntities(List<RC> categories, boolean showInvisibleEntities)
	{
		List<ParentPredicate<RC>> ppCategory = ParentPredicate.createFromList(fbRevision.getClassCategory(),"category",categories);
		return allForOrParents(fbRevision.getClassEntity(),ppCategory);
	}
	
	@Override public void rm(Class<RVM> cMappingView, RVM mapping) throws JeeslConstraintViolationException
	{
		mapping = em.find(cMappingView, mapping.getId());
		mapping.getView().getMaps().remove(mapping);
		this.rmProtected(mapping);
	}

	@Override public <W extends EjbWithRevisionAttributes<RA>>
			RA save(Class<W> cW, W entity, RA attribute) throws JeeslLockingException, JeeslConstraintViolationException
	{
		entity = this.find(cW, entity);
		attribute = this.saveProtected(attribute);
		if(!entity.getAttributes().contains(attribute))
		{
			entity.getAttributes().add(attribute);
			this.saveProtected(entity);
		}
		return attribute;
	}

	@Override public <W extends EjbWithRevisionAttributes<RA>>
			void rm(Class<W> cW, W entity, RA attribute) throws JeeslConstraintViolationException, JeeslLockingException
	{
		entity = this.find(cW, entity);
		if(entity.getAttributes().contains(attribute))
		{
			entity.getAttributes().remove(attribute);
			this.saveProtected(entity);
		}
		this.rmProtected(attribute);		
	}
	
	@Override public <T extends EjbWithId> T jpaTree(Class<T> c, String jpa, long id) throws JeeslNotFoundException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c ");
		sb.append(" FROM "+c.getName()+" c");
		sb.append(" WHERE c.").append(jpa);
		sb.append("=:refId");
		
		TypedQuery<T> q = em.createQuery(sb.toString(), c);
		q.setParameter("refId", id);
		
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("Nothing found "+c.getSimpleName()+" for jpa="+jpa);}
	}
	
	@Override
	public <T extends EjbWithId> List<T> revisions(Class<T> c, List<Long> ids)
	{
		AuditQuery query = AuditReaderFactory.get(em).createQuery().forRevisionsOfEntity(c, false, true);
		query.add(AuditEntity.revisionNumber().in(ids));
		query.addOrder(AuditEntity.revisionNumber().desc());
//		List<SimpleRevisionEntity<T>> list = SimpleRevisionEntity.build(query.getResultList());
//		for(SimpleRevisionEntity<T> item : list){lazyLoad(item.getEntity());}
//		return list;
		return null;
	}
	
	@Override
	public <T extends EjbWithId> List<Long> ids(Class<T> c, JeeslIoRevisionFacade.Scope scope)
	{
		List<Long> result = new ArrayList<Long>();
		
		Table t = c.getAnnotation(Table.class);
		if(t!=null)
		{			
			String query=null;
			switch(scope)
			{
				case live: query = SqlRevisionQueries.idsLive(t.name());break;
				case revision: query = SqlRevisionQueries.idsRevision(revisionPrefix+t.name());break;
			}
			
			for(Object o : em.createNativeQuery(query).getResultList())
			{
				long id = ((BigInteger)o).longValue();
				result.add(id);
			}
		}
		return result;
	}

	@Override
	public <T extends EjbWithId> List<JsonRevision> findCreated(Class<T> c, Date from, Date to)
	{
		List<JsonRevision> revisions = new ArrayList<JsonRevision>();
		Table t = c.getAnnotation(Table.class);
		if(t!=null)
		{			
			for(Object o : em.createNativeQuery(SqlRevisionQueries.revisionsIn(revisionPrefix+t.name(), revisionTable, from, to, SqlRevisionQueries.typesCreateRemove())).getResultList())
			{
				Object[] array = (Object[])o;
				SqlNativeQueryHelper.debugDataTypes(false, "findCreated", array);	 
				revisions.add(JsonRevisionFactory.build(array));
			}
			
		}
		return revisions;
	}
}