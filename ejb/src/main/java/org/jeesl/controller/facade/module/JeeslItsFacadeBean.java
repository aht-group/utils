package org.jeesl.controller.facade.module;

import java.util.ArrayList;
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

import org.jeesl.api.facade.module.JeeslItsFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.module.ItsFactoryBuilder;
import org.jeesl.interfaces.model.module.its.JeeslItsIssue;
import org.jeesl.interfaces.model.module.its.JeeslItsIssueStatus;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.mcs.JeeslMcsRealm;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslItsFacadeBean<L extends JeeslLang, D extends JeeslDescription,
										REALM extends JeeslMcsRealm<L,D,REALM,?>,
										ISSUE extends JeeslItsIssue<REALM,ISSUE>,
										STATUS extends JeeslItsIssueStatus<L,D,REALM,STATUS,?>>
					extends JeeslFacadeBean
					implements JeeslItsFacade<L,D,REALM,ISSUE>
{	
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(JeeslAssetFacadeBean.class);
	
	private final ItsFactoryBuilder<L,D,REALM,ISSUE,STATUS> fbIssue;
	
	public JeeslItsFacadeBean(EntityManager em, final ItsFactoryBuilder<L,D,REALM,ISSUE,STATUS> fbIssue)
	{
		super(em);
		this.fbIssue=fbIssue;
	}

	@Override
	public <RREF extends EjbWithId> ISSUE fcAItsRoot(REALM realm, RREF rref)
	{
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<ISSUE> cQ = cB.createQuery(fbIssue.getClassIssue());
		Root<ISSUE> root = cQ.from(fbIssue.getClassIssue());
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Expression<Long> eRefId = root.get(JeeslItsIssue.Attributes.rref.toString());
		Path<REALM> pRealm = root.get(JeeslItsIssue.Attributes.realm.toString());
		Path<ISSUE> pParent = root.get(JeeslItsIssue.Attributes.parent.toString());
		
		predicates.add(cB.equal(eRefId,rref.getId()));
		predicates.add(cB.equal(pRealm,realm));
		predicates.add(cB.isNull(pParent));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(root);

		TypedQuery<ISSUE> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex)
		{
			ISSUE result = fbIssue.ejbIssue().build(realm,rref, null);
			try {return this.save(result);}
			catch (JeeslConstraintViolationException | JeeslLockingException e)
			{
				return this.fcAItsRoot(realm,rref);
			}
		}
	}
}