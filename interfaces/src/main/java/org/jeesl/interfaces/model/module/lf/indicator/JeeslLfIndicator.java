package org.jeesl.interfaces.model.module.lf.indicator;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.lf.JeeslLfLogframe;
import org.jeesl.interfaces.model.module.lf.monitoring.JeeslLfIndicatorMonitoring;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslLfIndicator<LF extends JeeslLfLogframe<?,?,?,?,?,?>,
									IL extends JeeslLfIndicatorLevel<?,?,?,?,?>,
									IT extends JeeslLfIndicatorType<?,?,?,?,?>,
									TTG extends JeeslLfTargetTimeGroup<?,?>,
									LFM extends JeeslLfIndicatorMonitoring<?,TTG,?>>
						extends  Serializable,EjbSaveable,EjbRemoveable,
						EjbWithId,EjbWithName,EjbWithParentAttributeResolver

{
	public enum Attributes{interval,logframe}

	LF getLogframe();
	void setLogframe(LF logframe);

	IL getLevel();
	void setLevel(IL level);

	IT getType();
	void setType(IT type);
	TTG getTargetTimeGroup();
	void setTargetTimeGroup(TTG targetTimeGroup);

	List<LFM> getMonitoringItems();
	void setMonitoringItems(List<LFM> monitoringItems);

}