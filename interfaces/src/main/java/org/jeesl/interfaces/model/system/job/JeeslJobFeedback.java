package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslJobFeedback<JOB extends JeeslJob<?,?,?,?,USER>,
								FT extends JeeslJobFeedbackType<?,?,FT,?>,
							USER extends EjbWithEmail
							>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable
{
	JOB getJob();
	void setJob(JOB job);
	
	FT getType();
	void setType(FT type);
	
	USER getUser();
	void setUser(USER user);
}