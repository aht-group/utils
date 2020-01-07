package org.jeesl.interfaces.controller.processor;

import java.util.List;

import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiData;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiMapping;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;

public interface SsiMappingProcessor <MAPPING extends JeeslIoSsiMapping<?,?>,
										DATA extends JeeslIoSsiData<MAPPING,?>>
{
	MAPPING getMapping();
	
	void initMappings() throws UtilsNotFoundException;
	

	void evaluateData(List<DATA> datas);
	void linkData(List<DATA> datas);
	void ignoreData(List<DATA> datas);
}
