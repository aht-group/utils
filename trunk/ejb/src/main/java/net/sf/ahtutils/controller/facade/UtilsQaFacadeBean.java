package net.sf.ahtutils.controller.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import net.sf.ahtutils.controller.util.ParentPredicate;
import net.sf.ahtutils.interfaces.facade.UtilsQaFacade;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaCategory;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaStakeholder;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTest;
import net.sf.ahtutils.interfaces.model.qa.UtilsQaTestDiscussion;
import net.sf.ahtutils.interfaces.model.qa.UtilsQualityAssurarance;
import net.sf.ahtutils.interfaces.model.security.UtilsStaff;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityAction;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityUsecase;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityView;
import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsQaFacadeBean extends UtilsFacadeBean implements UtilsQaFacade
{
	final static Logger logger = LoggerFactory.getLogger(UtilsQaFacadeBean.class);
	
	public UtilsQaFacadeBean(EntityManager em){this(em,false);}
	public UtilsQaFacadeBean(EntityManager em, boolean handleTransaction)
	{
		super(em,handleTransaction);
	}
	
	public <L extends UtilsLang,
			D extends UtilsDescription,
			C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
			R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
			V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
			U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
			A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
			USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
			STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,QA>,
			QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			STATUS extends UtilsStatus<STATUS,L,D>>
		QA load(Class<QA> clQa, QA qa)
	{		
		qa = em.find(clQa, qa.getId());
		qa.getCategories().size();
		qa.getStaff().size();
		qa.getStakeholders().size();
		return qa;
	}
	
	public <L extends UtilsLang,
			D extends UtilsDescription,
			C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
			R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
			V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
			U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
			A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
			USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
			STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,QA>,
			QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			STATUS extends UtilsStatus<STATUS,L,D>>
		QAC load(Class<QAC> clQac, QAC category)
	{		
		category = em.find(clQac, category.getId());
		category.getTests().size();
		return category;
	}
	
	public <L extends UtilsLang,
			D extends UtilsDescription,
			C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
			R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
			V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
			U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
			A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
			USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
			STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,QA>,
			QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			STATUS extends UtilsStatus<STATUS,L,D>>
		QAT load(Class<QAT> clQat, QAT test)
	{		
		test = em.find(clQat, test.getId());
		test.getDiscussions().size();
		return test;
	}
	
	public <L extends UtilsLang,
			D extends UtilsDescription,
			C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
			R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
			V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
			U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
			A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
			USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
			STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,QA>,
			QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			STATUS extends UtilsStatus<STATUS,L,D>>
		List<QAT> fQaTests(Class<QAT> clTest, Class<QAC> clCategory, Class<QA> clQa, QA qa)
	{		
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<QAT> criteriaQuery = cB.createQuery(clTest);
		 
		Root<QAT> fromTest = criteriaQuery.from(clTest);
		Root<QAC> fromCategory = criteriaQuery.from(clCategory);
		Root<QA>  fromQa = criteriaQuery.from(clQa);
		
		Path<Object> pathToCategory = fromTest.get("category");
	    Path<Object> pathCategoryId = fromCategory.get("id");
	    
	    Path<Object> pathToQa = fromCategory.get("qa");
	    Path<Object> pathToQaId = fromQa.get("id");

		CriteriaQuery<QAT> select = criteriaQuery.select(fromTest);
		select.where(cB.equal(pathToCategory, pathCategoryId),cB.equal(pathToQa, pathToQaId),cB.equal(pathToQaId, qa.getId()));    

		select.orderBy(cB.asc(fromCategory.get("nr")),cB.asc(fromTest.get("nr")));
		
		TypedQuery<QAT> q = em.createQuery(select);
		return q.getResultList();
	}
	 
	 public <L extends UtilsLang,
			D extends UtilsDescription,
			C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
			R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
			V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
			U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
			A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
			USER extends UtilsUser<L,D,C,R,V,U,A,USER>,
			STAFF extends UtilsStaff<L,D,C,R,V,U,A,USER,QA>,
			QA extends UtilsQualityAssurarance<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAC extends UtilsQaCategory<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAT extends UtilsQaTest<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QAS extends UtilsQaStakeholder<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			QATD extends UtilsQaTestDiscussion<L,D,C,R,V,U,A,USER,STAFF,QA,QAC,QAT,QAS,QATD,STATUS>,
			STATUS extends UtilsStatus<STATUS,L,D>>
	 	List<QAT> fQaTests(Class<QAT> clTest, Class<QAC> clCategory, List<QAC> categories)
	 {
		 return this.allForOrParents(clTest, ParentPredicate.createFromList(clCategory, "category", categories));
	 }
}