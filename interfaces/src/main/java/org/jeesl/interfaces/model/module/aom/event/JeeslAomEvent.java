package org.jeesl.interfaces.model.module.aom.event;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.module.aom.JeeslAomAsset;
import org.jeesl.interfaces.model.module.aom.company.JeeslAomCompany;
import org.jeesl.interfaces.model.system.security.user.JeeslSimpleUser;
import org.jeesl.interfaces.model.with.status.JeeslWithStatus;
import org.jeesl.interfaces.model.with.status.JeeslWithType;
import org.jeesl.interfaces.model.with.text.EjbWithRemark;

import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface JeeslAomEvent <COMPANY extends JeeslAomCompany<?,?>,
								ASSET extends JeeslAomAsset<?,ASSET,COMPANY,?,?>,
								ETYPE extends JeeslAomEventType<?,?,ETYPE,?>,
								ESTATUS extends JeeslAomEventStatus<?,?,ESTATUS,?>,
								USER extends JeeslSimpleUser>
			extends Serializable,EjbSaveable,
					EjbWithRecord,EjbWithRemark,EjbWithName,
					JeeslWithType<ETYPE>,JeeslWithStatus<ESTATUS>
				
					
{
	public enum Attributes{assets,status}
	
	List<ASSET> getAssets();
	void setAssets(List<ASSET> assets);
	
	COMPANY getCompany();
	void setCompany(COMPANY vendor);
	
	Double getAmount();
	void setAmount(Double amount);
}