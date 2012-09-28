package net.sf.ahtutils.controller.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.controller.interfaces.UtilsFacade;
import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.EjbWithCode;
import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.EjbWithName;
import net.sf.ahtutils.model.interfaces.EjbWithNr;
import net.sf.ahtutils.model.interfaces.EjbWithType;
import net.sf.ahtutils.model.interfaces.EjbWithValidFrom;
import net.sf.ahtutils.model.interfaces.UtilsProperty;
import net.sf.ahtutils.model.interfaces.with.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsFacadeBean implements UtilsFacade
{
	final static Logger logger = LoggerFactory.getLogger(UtilsFacadeBean.class);
	
	protected EntityManager em;
	
	public UtilsFacadeBean(EntityManager em)
	{
		this.em=em;
	}
	
	public <T extends Object> T find(Class<T> type, long id) throws UtilsNotFoundException
	{
		T o = em.find(type,id);
		if(o==null){throw new UtilsNotFoundException("No entity "+type+" with id="+id);}
		return o;
	}
	
	public <T extends Object> T persist(T o) throws UtilsContraintViolationException
	{
		try
		{
			em.persist(o);
		}
		catch (Exception e)
		{
			if(e instanceof javax.validation.ConstraintViolationException)
			{
				throw new UtilsContraintViolationException(e.getMessage());
			}
			if( e instanceof javax.persistence.PersistenceException)
			{
				if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
				{
					throw new UtilsContraintViolationException(e.getCause().getMessage());
				}
				else
				{
					System.err.println("This Error is not handled: "+e.getClass().getName());
					e.printStackTrace();
				}
			}
			else
			{
				System.err.println("This Error is not handled: "+e.getClass().getName());
				e.printStackTrace();
			}
//			System.err.println("Error by "+ex.getCausedByException().getClass().getCanonicalName());
//			if(ex.getCausedByException().getClass().getSimpleName().equals(ConstraintViolationException.class.getSimpleName())){throw (ConstraintViolationException)ex.getCausedByException();}
//			else if(ex.getCausedByException().getClass().getSimpleName().equals(EntityExistsException.class.getSimpleName()))
			{
	//			logger.warn(ex.getCausedByException());
			}
//			else {throw ex;}
		}
	    return o;
	}
	
	public <T extends EjbWithCode> T fByCode(Class<T> type, String code) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery = criteriaQuery.where(root.<T>get("code").in(code));

		T result=null;
		TypedQuery<T> q = em.createQuery(criteriaQuery); 
		try	{result=(T)q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+type.getSimpleName()+" for code="+code);}
		return result;
	}
	
	@Override
	public <T extends UtilsProperty> Integer valueIntForKey(Class<T> type, String key, Integer defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Integer(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}
	@Override
	public <T extends UtilsProperty> Long valueLongForKey(Class<T> type, String key, Long defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Long(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public <T extends UtilsProperty> Boolean valueBooleanForKey(Class<T> type, String key, Boolean defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Boolean(t.getValue());
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}

	@Override
	public <T extends UtilsProperty> Date valueDateForKey(Class<T> type, String key, Date defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return new Date(new Long(t.getValue()));
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}
	@Override
	public <T extends UtilsProperty> String valueStringForKey(Class<T> type, String key, String defaultValue) throws UtilsNotFoundException
	{
		try
		{
			T t = valueForKey(type, key);
			return t.getValue();
		}
		catch (UtilsNotFoundException e)
		{
			if(defaultValue!=null){return defaultValue;}
			else{throw e;}
		}
	}
	private <T extends UtilsProperty> T valueForKey(Class<T> type, String key) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery = criteriaQuery.where(root.<T>get("key").in(key));

		T result=null;
		TypedQuery<T> q = em.createQuery(criteriaQuery); 
		try	{result=(T)q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found "+type.getSimpleName()+" for key="+key);}
		return result;
	}
	
	public <T extends Object> List<T> all(Class<T> type)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> from = criteriaQuery.from(type);
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		
		TypedQuery<T> typedQuery = em.createQuery(select);
		return typedQuery.getResultList();
	}
	
	public <T extends EjbWithPosition> List<T> allOrderedPosition(Class<T> type)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> from = criteriaQuery.from(type);
		
		Expression<Date> ePosition = from.get("position");
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		select.orderBy(criteriaBuilder.asc(ePosition));
		
		TypedQuery<T> typedQuery = em.createQuery(select);
		return typedQuery.getResultList();
	}
	
	public <T extends Object> T update(T o) throws UtilsContraintViolationException, UtilsLockingException
	{
		try
		{
			em.merge(o);
			em.flush();
		}
		catch (Exception e)
		{
//			System.out.println("Exception in update");
//			e.printStackTrace();
			
//			System.err.println(javax.validation.ConstraintViolationException.class.getSimpleName()+" "+(e instanceof javax.validation.ConstraintViolationException));
//			System.err.println(javax.persistence.PersistenceException.class.getSimpleName()+" "+(e instanceof javax.persistence.PersistenceException));
//			System.err.println(javax.persistence.OptimisticLockException.class.getSimpleName()+" "+(e instanceof javax.persistence.OptimisticLockException));
			
			if(e instanceof javax.validation.ConstraintViolationException)
			{
				throw new UtilsContraintViolationException(e.getMessage());
			}
			if(e instanceof javax.persistence.OptimisticLockException)
			{
				throw new UtilsLockingException(e.getMessage());
			}
			if(e instanceof javax.persistence.PersistenceException)
			{
				if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
				{
					throw new UtilsContraintViolationException(e.getCause().getMessage());
				}
				else
				{
					System.err.println("This Error is not handled: "+e.getClass().getName());
					e.printStackTrace();
				}
			}
			
			System.err.println("(end) This Error is not handled: "+e.getClass().getName());
			e.printStackTrace();
			
		}
		return o;
	}
	
	@Override public <T extends EjbRemoveable> void rm(T o) throws UtilsIntegrityException {rmProtected(o);}
	
	public <T extends Object> void rmProtected(T o) throws UtilsIntegrityException
	{
		try
		{
			o=em.merge(o);
			em.remove(o);
			em.flush();
		}
		catch(javax.persistence.PersistenceException e)
		{
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
			{
				throw new UtilsIntegrityException("Delete Referential Integrity check failed for "+o.getClass().getSimpleName()+". Object may be used somewhere else: "+o);
			}
			throw(e);
		}
	}
	
	public <T extends EjbWithType> List<T> allForType(Class<T> clazz, String type)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery = criteriaQuery.where(root.<T>get("type").in(type));
        
		TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
	
	public <T extends EjbWithName> T fByName(Class<T> type, String name) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery = criteriaQuery.where(root.<T>get("name").in(name));

		TypedQuery<T> q = em.createQuery(criteriaQuery); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+type.getSimpleName()+" for name="+name);}
	}
	
	public <T extends EjbWithNr, P extends EjbWithId> T fByNr(Class<T> type, String parentName, P parent, int nr) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
	    
	    Root<T> from = criteriaQuery.from(type);
	    Path<Object> pathParent = from.get(parentName);
	    Path<Object> pathNr = from.get("nr");
	    
	    CriteriaQuery<T> select = criteriaQuery.select(from);
	    select.where( criteriaBuilder.equal(pathParent, parent.getId()),
	    		      criteriaBuilder.equal(pathNr, nr));

		TypedQuery<T> q = em.createQuery(select); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+type.getSimpleName()+" for nr="+nr);}
	}
	
	public <T extends EjbWithValidFrom> T fFirstValidFrom(Class<T> type, String parentName, long id, Date validFrom) throws UtilsNotFoundException
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> pathParent = fromType.get(parentName);

	    Expression<Date> fromDate = fromType.get("validFrom");
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( criteriaBuilder.equal(pathParent, id),
	    		      criteriaBuilder.lessThanOrEqualTo(fromDate, validFrom));
	    select.orderBy(criteriaBuilder.desc(fromDate));
	    
		TypedQuery<T> q = em.createQuery(select);
		q.setMaxResults(1);
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("No "+type.getSimpleName()+" for "+parentName+".id="+id+" validFrom="+validFrom);}
	}
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> p1Path = fromType.get(p1Name);
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.equal(p1Path, p1.getId()));
	    
		TypedQuery<T> q = em.createQuery(select);
		
		try	{return q.getResultList();}
		catch (NoResultException ex){return new ArrayList<T>();}
	}
	
	//******************** ORDERED *****************
	
	public <T extends EjbWithRecord> List<T> allOrderedRecord(Class<T> type, boolean ascending)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> from = criteriaQuery.from(type);
		
		Expression<Date> eRecord = from.get("record");
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(ascending){select.orderBy(criteriaBuilder.asc(eRecord));}
		else{select.orderBy(criteriaBuilder.desc(eRecord));}
		
		TypedQuery<T> typedQuery = em.createQuery(select);
		return typedQuery.getResultList();
	}
	
	public <T extends EjbWithRecord, AND extends EjbWithId, OR extends EjbWithId> List<T> allOrderedForParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr,boolean ascending)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** allOrderedForParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("ascending:" +ascending);
			logger.trace("AND "+lpAnd.size());
			logger.trace("OR "+lpOr.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);
		Expression<Date> eRecord = from.get("record");
		
		Predicate pOr = cB.or(ParentPredicate.array(cB, from, lpOr));
		Predicate pAnd = cB.and(ParentPredicate.array(cB, from, lpAnd));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(ascending){select.orderBy(cB.asc(eRecord));}
		else{select.orderBy(cB.desc(eRecord));}
		if(lpOr==null || lpOr.size()==0)
		{
			select.where(pAnd);
		}
		else
		{
			select.where(cB.and(pAnd,pOr));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	// ************************************
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> p1Path = fromType.get(p1Name);
	    Path<Object> p2Path = fromType.get(p2Name);
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.and(cB.equal(p1Path, p1.getId()),
	    					 cB.equal(p2Path, p2.getId())));
	    
		TypedQuery<T> q = em.createQuery(select);
		
		try	{return q.getResultList();}
		catch (NoResultException ex){return new ArrayList<T>();}
	}
	
	public <T extends EjbWithId, I extends EjbWithId> List<T> allForParent(Class<T> type, String p1Name, I p1, String p2Name, I p2, String p3Name, I p3)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
	    CriteriaQuery<T> criteriaQuery = cB.createQuery(type);
	    
	    Root<T> fromType = criteriaQuery.from(type);
	    Path<Object> p1Path = fromType.get(p1Name);
	    Path<Object> p2Path = fromType.get(p2Name);
	    Path<Object> p3Path = fromType.get(p3Name);
	    
	    CriteriaQuery<T> select = criteriaQuery.select(fromType);
	    select.where( cB.and(cB.equal(p1Path, p1.getId()),
	    					 cB.equal(p2Path, p2.getId()),
	    					 cB.equal(p3Path, p3.getId())));
	    
		TypedQuery<T> q = em.createQuery(select);
		
		try	{return q.getResultList();}
		catch (NoResultException ex){return new ArrayList<T>();}
	}
	
	public <T extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrParents(Class<T> queryClass, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** fForAndOrParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("AND "+lpAnd.size());
			logger.trace("OR "+lpOr.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);

		Predicate pOr = cB.or(ParentPredicate.array(cB, from, lpOr));
		Predicate pAnd = cB.and(ParentPredicate.array(cB, from, lpAnd));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(lpOr==null || lpOr.size()==0)
		{
			select.where(pAnd);
		}
		else
		{
			select.where(cB.and(pAnd,pOr));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	public <T extends EjbWithId, P extends EjbWithId, OR extends EjbWithId, AND extends EjbWithId> List<T> fForAndOrGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<AND>> lpAnd, List<ParentPredicate<OR>> lpOr)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** fForAndOrPGrandParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("AND "+lpAnd.size());
			logger.trace("OR "+lpOr.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);
		Root<P> fromParent = criteriaQuery.from(parentClass);
		
		Path<Object> pathToParent = from.get(parentName);
	    Path<Object> pathParentId = fromParent.get("id");
		
		Predicate pOr = cB.or(ParentPredicate.array(cB, fromParent, lpOr));
		Predicate pAnd = cB.and(ParentPredicate.array(cB, fromParent, lpAnd));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(lpOr==null || lpOr.size()==0)
		{
			select.where(pAnd,cB.equal(pathToParent, pathParentId));
		}
		else
		{
			select.where(cB.and(pAnd,pOr),cB.equal(pathToParent, pathParentId));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}
	
	public <T extends EjbWithId, P extends EjbWithId, OR1 extends EjbWithId, OR2 extends EjbWithId> List<T> fGrandParents(Class<T> queryClass, Class<P> parentClass, String parentName, List<ParentPredicate<OR1>> lpOr1, List<ParentPredicate<OR2>> lpOr2)
	{
		if(logger.isTraceEnabled())
		{
			logger.trace("****** fGrandParents");
			logger.trace("QueryClass:" +queryClass.getName());
			logger.trace("OR1 "+lpOr1.size());
			logger.trace("OR2 "+lpOr2.size());
			logger.trace("************************");
		}
		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = cB.createQuery(queryClass);
		 
		Root<T> from = criteriaQuery.from(queryClass);
		Root<P> fromParent = criteriaQuery.from(parentClass);
		
		Path<Object> pathToParent = from.get(parentName);
	    Path<Object> pathParentId = fromParent.get("id");
		
		Predicate pOr1 = cB.or(ParentPredicate.array(cB, fromParent, lpOr1));
		Predicate pOr2 = cB.or(ParentPredicate.array(cB, fromParent, lpOr2));
		    
		CriteriaQuery<T> select = criteriaQuery.select(from);
		if(lpOr1==null || lpOr2==null || lpOr1.size()==0 || lpOr2.size()==0)
		{
			logger.trace("Returning empty List");
			return new ArrayList<T>();
		}
		else
		{
			logger.trace("Executing");
			select.where(pOr1,pOr2,cB.equal(pathToParent, pathParentId));
		}
		 
		TypedQuery<T> q = em.createQuery(select);
		return q.getResultList();
	}

	@Override
	public <T extends EjbWithId> T save(T o) throws UtilsContraintViolationException,UtilsLockingException
	{
		if(o.getId()==0){return this.persist(o);}
		else{return this.update(o);}
	}


}