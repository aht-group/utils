package net.sf.ahtutils.controller.facade;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsSyncFacade;
import net.sf.ahtutils.interfaces.model.sync.UtilsSync;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

public class UtilsSyncFacadeBean <L extends UtilsLang,
									D extends UtilsDescription,
									STATUS extends UtilsStatus<STATUS,L,D>,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									SYNC extends UtilsSync<L,D,STATUS,CATEGORY>>  
	extends UtilsFacadeBean implements UtilsSyncFacade<L,D,STATUS,CATEGORY,SYNC>
{	
	public UtilsSyncFacadeBean(EntityManager em)
	{
		super(em);
	}

	@Override
	public SYNC fSync(Class<SYNC> cSync, CATEGORY category, String code) throws UtilsNotFoundException
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
        CriteriaQuery<SYNC> cQ = cB.createQuery(cSync);
        Root<SYNC> root = cQ.from(cSync);
        
        Path<CATEGORY> pathCategory = root.get("category");
        Path<String> pathCode = root.get("code");
        
        Predicate pCategory = cB.equal(pathCategory,category);
        Predicate pCode = cB.equal(pathCode,code);
        
        cQ.where(cB.and(pCategory,pCode));
        cQ.select(root);
        
		TypedQuery<SYNC> q = em.createQuery(cQ); 
		try	{return q.getSingleResult();}
		catch (NoResultException ex){throw new UtilsNotFoundException("Nothing found category:"+category.getCode()+" for code="+code);}
	}

	
}
