package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.exception.ejb.UtilsContraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.model.interfaces.EjbWithCode;

public class UtilsFacadeBean
{
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
	
	public <T extends Object> List<T> all(Class<T> type)
	{
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
		Root<T> from = criteriaQuery.from(type);
		
		CriteriaQuery<T> select = criteriaQuery.select(from);
		
		TypedQuery<T> typedQuery = em.createQuery(select);
		List<T> resultList = typedQuery.getResultList();
		return resultList;
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
				throw new UtilsIntegrityException("Delete Referential Integrity check failed for "+o.getClass().getSimpleName()+". Object may be used somewhere else.");
			}
			throw(e);
		}
	}
}
