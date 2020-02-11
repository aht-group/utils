package org.jeesl.controller.facade.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jeesl.api.facade.system.JeeslJobFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.system.JobFactoryBuilder;
import org.jeesl.factory.ejb.system.job.EjbJobCacheFactory;
import org.jeesl.factory.ejb.system.job.EjbJobFactory;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.job.JeeslJob;
import org.jeesl.interfaces.model.system.job.JeeslJobCache;
import org.jeesl.interfaces.model.system.job.JeeslJobCategory;
import org.jeesl.interfaces.model.system.job.JeeslJobExpiration;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedback;
import org.jeesl.interfaces.model.system.job.JeeslJobFeedbackType;
import org.jeesl.interfaces.model.system.job.JeeslJobPriority;
import org.jeesl.interfaces.model.system.job.JeeslJobRobot;
import org.jeesl.interfaces.model.system.job.JeeslJobStatus;
import org.jeesl.interfaces.model.system.job.JeeslJobTemplate;
import org.jeesl.interfaces.model.system.job.JeeslJobType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.with.text.EjbWithEmail;
import org.joda.time.DateTime;

public class JeeslSystemJobFacadeBean<L extends JeeslLang,D extends JeeslDescription,
									TEMPLATE extends JeeslJobTemplate<L,D,CATEGORY,TYPE,PRIORITY,EXPIRE>,
									CATEGORY extends JeeslJobCategory<L,D,CATEGORY,?>,
									TYPE extends JeeslJobType<L,D,TYPE,?>,
									EXPIRE extends JeeslJobExpiration<L,D,EXPIRE,?>,
									JOB extends JeeslJob<TEMPLATE,PRIORITY,FEEDBACK,STATUS,USER>,
									PRIORITY extends JeeslJobPriority<L,D,PRIORITY,?>,
									FEEDBACK extends JeeslJobFeedback<JOB,FT,USER>,
									FT extends JeeslJobFeedbackType<L,D,FT,?>,
									STATUS extends JeeslJobStatus<L,D,STATUS,?>,
									ROBOT extends JeeslJobRobot<L,D>,
									CACHE extends JeeslJobCache<TEMPLATE,CONTAINER>,
									CONTAINER extends JeeslFileContainer<?,?>,
									USER extends EjbWithEmail
									>
					extends JeeslFacadeBean
					implements JeeslJobFacade<L,D,TEMPLATE,CATEGORY,TYPE,EXPIRE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,CONTAINER,USER>
{
	private static final long serialVersionUID = 1L;

	private final JobFactoryBuilder<L,D,TEMPLATE,CATEGORY,TYPE,EXPIRE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fbJob;
	
	private EjbJobCacheFactory<TEMPLATE,CACHE> efCache;
	private EjbJobFactory<L,D,TEMPLATE,CATEGORY,TYPE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> efJob;
	
	public JeeslSystemJobFacadeBean(EntityManager em, final JobFactoryBuilder<L,D,TEMPLATE,CATEGORY,TYPE,EXPIRE,JOB,PRIORITY,FEEDBACK,FT,STATUS,ROBOT,CACHE,USER> fbJob)
	{
		super(em);
		this.fbJob=fbJob;
		
		efCache = fbJob.cache();
		efJob = fbJob.job();
	}
	
	@Override public <E extends Enum<E>> TEMPLATE fJobTemplate(E type, String code) throws JeeslNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<TEMPLATE> cQ = cB.createQuery(fbJob.getClassTemplate());
		Root<TEMPLATE> template = cQ.from(fbJob.getClassTemplate());
		
		Join<TEMPLATE,TYPE> jType = template.join(JeeslJobTemplate.Attributes.type.toString());
		Expression<String> eType = jType.get(JeeslStatus.EjbAttributes.code.toString());
		Expression<String> eCode = template.get(JeeslJobTemplate.Attributes.code.toString());
		
		predicates.add(cB.equal(eType,type.toString()));
		predicates.add(cB.equal(eCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(template);

		TypedQuery<TEMPLATE> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("No "+fbJob.getClassTemplate().getSimpleName()+" found for type="+type.toString()+" and code="+code);}
		catch (NonUniqueResultException ex){throw new JeeslNotFoundException("No unique results in "+fbJob.getClassTemplate().getSimpleName()+" for type="+type.toString()+" and code="+code);}
	}
	
	@Override
	public List<JOB> fJobs(TEMPLATE template, String code)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<JOB> cQ = cB.createQuery(fbJob.getClassJob());
		Root<JOB> job = cQ.from(fbJob.getClassJob());
		
		Join<JOB,TEMPLATE> jTemplate = job.join(JeeslJob.Attributes.template.toString());
		Path<Date> pRecordCreation = job.get(JeeslJob.Attributes.recordCreation.toString());
		Path<String> pCode = job.get(JeeslJob.Attributes.code.toString());
		
		predicates.add(jTemplate.in(template));
		predicates.add(cB.equal(pCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.asc(pRecordCreation));
		cQ.select(job);

		TypedQuery<JOB> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public List<JOB> fJobs(List<CATEGORY> categories, List<TYPE> types, List<STATUS> status, Date from, Date to)
	{
		if(categories==null || categories.isEmpty()){return new ArrayList<JOB>();}
		if(types==null || types.isEmpty()){return new ArrayList<JOB>();}
		if(status==null || status.isEmpty()){return new ArrayList<JOB>();}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<JOB> cQ = cB.createQuery(fbJob.getClassJob());
		Root<JOB> job = cQ.from(fbJob.getClassJob());
		
		Join<JOB,TEMPLATE> jTemplate = job.join(JeeslJob.Attributes.template.toString());
		Path<CATEGORY> pCategory = jTemplate.get(JeeslJobTemplate.Attributes.category.toString());
		Path<TYPE> pType = jTemplate.get(JeeslJobTemplate.Attributes.type.toString());
		
		Path<Date> pRecordCreation = job.get(JeeslJob.Attributes.recordCreation.toString());
		Path<STATUS> pStatus = job.get(JeeslJob.Attributes.status.toString());
		
		Path<PRIORITY> pPriority = job.get(JeeslJob.Attributes.priority.toString());
		Path<Integer> pPosition = pPriority.get(JeeslJobPriority.Attributes.position.toString());
		
		if(from!=null){predicates.add(cB.greaterThanOrEqualTo(pRecordCreation, (new DateTime(from)).withTimeAtStartOfDay().toDate()));}
		if(to!=null){predicates.add(cB.lessThan(pRecordCreation, (new DateTime(to)).withTimeAtStartOfDay().plusDays(1).toDate()));}
		
		predicates.add(pCategory.in(categories));
		predicates.add(pType.in(types));
		predicates.add(pStatus.in(status));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.orderBy(cB.desc(pPosition),cB.asc(pRecordCreation));
		cQ.select(job);

		TypedQuery<JOB> tQ = em.createQuery(cQ);
		return tQ.getResultList();
	}
	
	@Override public JOB fActiveJob(TEMPLATE template, String code) throws JeeslNotFoundException
	{
		List<STATUS> statuses = new ArrayList<STATUS>();
		try
		{
			statuses.add(this.fByCode(fbJob.getClassStatus(),JeeslJobStatus.Code.queue));
			statuses.add(this.fByCode(fbJob.getClassStatus(),JeeslJobStatus.Code.working));
		}
		catch (JeeslNotFoundException e) {e.printStackTrace();}
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<JOB> cQ = cB.createQuery(fbJob.getClassJob());
		Root<JOB> job = cQ.from(fbJob.getClassJob());
		
		Join<CACHE,TEMPLATE> jTemplate = job.join(JeeslJob.Attributes.template.toString());
		Path<STATUS> pStatus = job.get(JeeslJob.Attributes.status.toString());
		Expression<String> pCode = job.get(JeeslJob.Attributes.code.toString());
		
		predicates.add(cB.equal(jTemplate,template));
		predicates.add(pStatus.in(statuses));
		predicates.add(cB.equal(pCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(job);

		TypedQuery<JOB> tQ = em.createQuery(cQ);
		List<JOB> results = tQ.getResultList();
		if(results.isEmpty()){throw new JeeslNotFoundException("Nothing found for template:"+template.getCode()+" and job.code="+code);}
		else{return results.get(0);}
	}

	@Override public CACHE fJobCache(TEMPLATE template, String code) throws JeeslNotFoundException
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cB = em.getCriteriaBuilder();
		CriteriaQuery<CACHE> cQ = cB.createQuery(fbJob.getClassCache());
		Root<CACHE> cache = cQ.from(fbJob.getClassCache());
		
		Join<CACHE,TEMPLATE> jTemplate = cache.join(JeeslJobCache.Attributes.template.toString());
		Expression<String> pCode = cache.get(JeeslJobCache.Attributes.code.toString());
		
		predicates.add(cB.equal(jTemplate,template));
		predicates.add(cB.equal(pCode,code));
		
		cQ.where(cB.and(predicates.toArray(new Predicate[predicates.size()])));
		cQ.select(cache);

		TypedQuery<CACHE> tQ = em.createQuery(cQ);
		try	{return tQ.getSingleResult();}
		catch (NoResultException ex){throw new JeeslNotFoundException("Nothing found for template:"+template.getCode()+"/"+template.getType().getCode()+" and cache.code="+code);}
		catch (NonUniqueResultException ex){throw new JeeslNotFoundException("Results not unique for template:"+template.getCode()+"/"+template.getType().getCode()+" and cache.code="+code);}
	}

	@Override
	public CACHE uJobCache(TEMPLATE template, String code, byte[] data) throws JeeslConstraintViolationException, JeeslLockingException
	{
		CACHE cache = null;
		
		try {cache = fJobCache(template,code);}
		catch (JeeslNotFoundException e){cache = efCache.build(template, code, data);}
		
		cache.setRecord(new Date());
		cache.setData(data);
		cache = this.save(cache);
		
		return cache;
	}

	@Override
	public JOB cJob(USER user, List<FEEDBACK> feedbacks, TEMPLATE template, String code, String name, String jsonFilter) throws JeeslNotFoundException, JeeslConstraintViolationException, JeeslLockingException
	{
		STATUS status = this.fByCode(fbJob.getClassStatus(),JeeslJobStatus.Code.queue);
		JOB job = efJob.build(user,template,status,code,name,jsonFilter);
		return this.save(job);
	}
}