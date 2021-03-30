package org.jeesl.interfaces.model.module.mdc;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.mdc.activity.JeeslMdcActivity;

public interface JeeslMdcData <ACT extends JeeslMdcActivity<?,?,?,?>
									>
		extends Serializable,EjbSaveable,EjbRemoveable
				
{
	public enum Attributes{activity}
	
	ACT getActivity();
	void setActivity(ACT activity);
}