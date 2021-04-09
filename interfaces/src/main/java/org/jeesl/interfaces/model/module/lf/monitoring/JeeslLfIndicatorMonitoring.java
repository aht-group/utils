package org.jeesl.interfaces.model.module.lf.monitoring;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.lf.indicator.JeeslLfIndicator;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeElement;
import org.jeesl.interfaces.model.module.lf.target.time.JeeslLfTargetTimeGroup;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface JeeslLfIndicatorMonitoring<LFI extends JeeslLfIndicator<?,?,?,TTG,?>,
						TTG extends JeeslLfTargetTimeGroup<?,?>,
						TTE extends JeeslLfTargetTimeElement<?,?>>
				extends  Serializable,EjbSaveable,EjbRemoveable,EjbWithId,EjbWithName,EjbWithParentAttributeResolver
{
	public enum Attributes{indicator}
	
	LFI getIndicator();
	void setIndicator(LFI indicator);
	
	TTG getTargetTimeGroup();
	void setTargetTimeGroup(TTG targetTimeGroup);

	TTE getTargetTimeElement();
	void setTargetTimeElement(TTE targetTimeElement);

	public Double getTargetAchived();
	public void setTargetAchived(Double targetAchived);
	
	public Double getWorkCompleted();
	public void setWorkCompleted(Double workCompleted) ;
	
	public Double getWorkFromPrevious() ;
	public void setWorkFromPrevious(Double workFromPrevious);
	
	public Double getWorkFromNext();
	public void setWorkFromNext(Double workFromNext);
	
	public Double getAllocatedBudget();
	public void setAllocatedBudget(Double allocatedBudget);
	
	public Double getBudgetExpences() ;
	public void setBudgetExpences(Double budgetExpences);
	
	public String getComment() ;
	public void setComment(String comment);
}
