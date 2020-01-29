package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.exception.ejb.JeeslConstraintViolationException;
import org.jeesl.exception.ejb.JeeslLockingException;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsCategory;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileMeta;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public interface JeeslIoCmsFacade <L extends UtilsLang,D extends UtilsDescription,LOC extends UtilsStatus<LOC,L,D>,
									CAT extends JeeslIoCmsCategory<L,D,CAT,?>,
									CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
									V extends JeeslIoCmsVisiblity,
									S extends JeeslIoCmsSection<L,S>,
									E extends JeeslIoCmsElement<V,S,EC,ET,C,FC>,
									EC extends UtilsStatus<EC,L,D>,
									ET extends UtilsStatus<ET,L,D>,
									C extends JeeslIoCmsContent<V,E,MT>,
									MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
									FC extends JeeslFileContainer<?,?>,
									FM extends JeeslFileMeta<D,FC,?,?>
									>
						extends UtilsFacade
{
	S load(S section, boolean recursive);
	List<E> fCmsElements(S section);
	
	void deleteCmsElement(E element) throws JeeslConstraintViolationException, JeeslLockingException;
}