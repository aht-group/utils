package org.jeesl.api.facade.io;

import java.util.List;

import org.jeesl.exception.ejb.JeeslNotFoundException;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.interfaces.model.system.io.revision.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiAttribute;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiLink;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiFacade <L extends JeeslLang,D extends JeeslDescription,
									SYSTEM extends JeeslIoSsiSystem,
									MAPPING extends JeeslIoSsiMapping<SYSTEM,ENTITY>,
									ATTRIBUTE extends JeeslIoSsiAttribute<MAPPING,ENTITY>,
									DATA extends JeeslIoSsiData<MAPPING,LINK>,
									LINK extends JeeslIoSsiLink<L,D,LINK,?>,
									ENTITY extends JeeslRevisionEntity<?,?,?,?,?,?>
									>
			extends JeeslFacade
{	
	MAPPING fMapping(Class<?> json, Class<?> ejb) throws JeeslNotFoundException;
	DATA fIoSsiData(MAPPING mapping, String code) throws JeeslNotFoundException;
	
	<T extends EjbWithId> DATA fIoSsiData(MAPPING mapping, T ejb) throws JeeslNotFoundException;
	List<DATA> fIoSsiData(MAPPING mapping, List<LINK> links);
	<A extends EjbWithId> List<DATA> fIoSsiData(MAPPING mapping, List<LINK> links, A a);
	<A extends EjbWithId, B extends EjbWithId> List<DATA> fIoSsiData(MAPPING mapping, List<LINK> links, A a, B b);
	
	Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping);
	<A extends EjbWithId> Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping, A a);
	<A extends EjbWithId, B extends EjbWithId> Json1Tuples<LINK> tpIoSsiLinkForMapping(MAPPING mapping, A a, B b);
	
	Json1Tuples<MAPPING> tpMapping();
}