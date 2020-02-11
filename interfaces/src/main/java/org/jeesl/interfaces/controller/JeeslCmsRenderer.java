package org.jeesl.interfaces.controller;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.jeesl.interfaces.model.system.io.fr.JeeslFileContainer;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.interfaces.model.system.locale.JeeslLocaleProvider;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface JeeslCmsRenderer <L extends JeeslLang,D extends JeeslDescription, LOC extends JeeslLocale<L,D,LOC,?>,
								CAT extends JeeslStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,S,LOC>,
								V extends JeeslIoCmsVisiblity,
								S extends JeeslIoCmsSection<L,S>,
								E extends JeeslIoCmsElement<V,S,EC,ET,C,FC>,
								EC extends JeeslStatus<EC,L,D>,
								ET extends JeeslStatus<ET,L,D>,
								C extends JeeslIoCmsContent<V,E,MT>,
								MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
								FC extends JeeslFileContainer<?,?>
								>
{
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsRenderer.class);
	
	Section build(JeeslLocaleProvider<LOC> lp, String localeCode, S section) throws OfxAuthoringException;
}