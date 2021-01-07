package org.jeesl.web.mbean.prototype.io.revision;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.io.JeeslIoRevisionFacade;
import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.factory.builder.io.IoRevisionFactoryBuilder;
import org.jeesl.interfaces.model.io.revision.entity.JeeslRevisionMissingLabel;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.web.mbean.prototype.system.AbstractAdminBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractAdminRevisionMissingLabelBean <L extends JeeslLang, D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,RML extends JeeslRevisionMissingLabel>
	extends AbstractAdminBean<L,D,LOC> implements Serializable
{
	private IoRevisionFactoryBuilder<L, D, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, RML> fbRevision;

	public AbstractAdminRevisionMissingLabelBean(final IoRevisionFactoryBuilder<L,D,?,?,?,?,?,?,?,?,?,?,?,RML> fbRevision) {
		super(fbRevision.getClassL(),fbRevision.getClassD());
		this.fbRevision=fbRevision;
	}


	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminRevisionMissingLabelBean.class);

	private RML missingLabel;public RML getMissingLabel() {return missingLabel;} public void setMissingLabel(RML missingLabel) {this.missingLabel = missingLabel;}
	private JeeslIoRevisionFacade<L, D, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, RML> fRevision;
	private List<RML> missingLabels; public List<RML> getMissingLabels() {return missingLabels;}



	protected void postConstructMissingEntity(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage, JeeslIoRevisionFacade<L,D,?,?,?,?,?,?,?,?,?,?,?,RML> fRevision)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fRevision=fRevision;
		if(fRevision==null) {logger.warn(JeeslIoRevisionFacade.class.getSimpleName()+" is NULL");}
		reloadMissingLabels();
	}


	private void reloadMissingLabels()
	{
		missingLabels = fRevision.allMissingLabels(fbRevision.getClassMissingRevision());
		if(debugOnInfo){logger.info(AbstractLogMessage.reloaded(fbRevision.getClassMissingRevision(),missingLabels));}
	}


	public void removeMissingLabels() throws JeeslConstraintViolationException, JeeslLockingException, JeeslNotFoundException
	{
		if(debugOnInfo){logger.info("clean missing revision table.........");}
		fRevision.cleanMissingLabels(fbRevision.getClassMissingRevision());
		reloadMissingLabels();
	}

	public void selectEntity()
	{
		if(debugOnInfo){logger.info("select Missing entity");}

	}


}