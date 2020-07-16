package org.jeesl.interfaces.model.module.workflow.instance;

import java.io.Serializable;
import java.util.Date;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;

public interface JeeslWorkflowDelegate <WY extends JeeslWorkflowActivity<?,?,?,USER>,
										USER extends JeeslUser<?>		
									>
		extends Serializable,EjbPersistable,EjbRemoveable,EjbSaveable,
				EjbWithId,EjbWithParentAttributeResolver
{
	public static enum Attributes{activity}
	
	public WY getActivity();
	public void setActivity(WY activity);
	
	public Date getRecordRequest();
	public void setRecordRequest(Date recordRequest);
	
	public USER getUserRequest();
	public void setUserRequest(USER userRequest);

	public String getRemarkRequest();
	public void setRemarkRequest(String remarkRequest);
	
	
	public Date getRecordApproval();
	public void setRecordApproval(Date recordApproval);
	
	USER getUserApproval();
	void setUserApproval(USER userApproval);

	String getRemarkApproval();
	void setRemarkApproval(String remarkApproval);

	public Boolean isResult();
	public void setResult(Boolean result);
}