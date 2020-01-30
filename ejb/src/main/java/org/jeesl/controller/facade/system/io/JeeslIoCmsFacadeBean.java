package org.jeesl.controller.facade.system.io;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.controller.facade.JeeslFacadeBean;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.factory.builder.io.IoCmsFactoryBuilder;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsCategory;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoCmsFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CAT extends JeeslIoCmsCategory<L,D,CAT,?>,
									CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
									V extends JeeslIoCmsVisiblity,
									S extends JeeslIoCmsSection<L,S>,
									E extends JeeslIoCmsElement<V,S,EC,ET,C,FC>,
									EC extends UtilsStatus<EC,L,D>,
									ET extends UtilsStatus<ET,L,D>,
									C extends JeeslIoCmsContent<V,E,MT>,
									MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
									FC extends JeeslFileContainer<?,FM>,
									FM extends JeeslFileMeta<D,FC,?,?>,
									LOC extends UtilsStatus<LOC,L,D>>
					extends JeeslFacadeBean
					implements JeeslIoCmsFacade<L,D,LOC,CAT,CMS,V,S,E,EC,ET,C,MT,FC,FM>
{	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(JeeslIoCmsFacadeBean.class);
	
	private final IoCmsFactoryBuilder<L,D,LOC,CAT,CMS,V,S,E,EC,ET,C,MT,FC,FM> fbCms;
	
	public JeeslIoCmsFacadeBean(EntityManager em,
			IoCmsFactoryBuilder<L,D,LOC,CAT,CMS,V,S,E,EC,ET,C,MT,FC,FM> fbCms)
	{
		super(em);
		this.fbCms=fbCms;
	}
	
	@Override public S load(S section, boolean recursive)
	{
		section = em.find(fbCms.getClassSection(), section.getId());
		if(recursive)
		{
			for(S s : section.getSections())
			{
				s = this.load(s,recursive);
			}
		}
		return section;
	}

	@Override public List<E> fCmsElements(S section) {return this.allForParent(fbCms.getClassElement(),section);}

	@Override public void deleteCmsElement(E element) throws JeeslConstraintViolationException, JeeslLockingException
	{
		if(element.getFrContainer()!=null)
		{
			List<FM> files = this.allForParent(fbCms.getClassFileMeta(),element.getFrContainer());
			if(!files.isEmpty()) {throw new JeeslConstraintViolationException("There are still files");}
			else
			{
				FC container = element.getFrContainer();
				element.setFrContainer(null);
				element = this.save(element);
				this.rm(container);
			}
		}
		if(!element.getContent().isEmpty())
		{
			List<C> list = new ArrayList<>(element.getContent().values());
			for(C c : list)
			{
				c.getElement().getContent().remove(c.getLkey());
				this.rm(c);
			}
		}
		
		this.rmProtected(element);
	}
}