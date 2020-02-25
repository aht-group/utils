package org.jeesl.interfaces.model.system.io.ssi.data;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbRemoveable;
import org.jeesl.interfaces.model.marker.jpa.EjbSaveable;
import org.jeesl.interfaces.model.with.attribute.EjbWithVisibleMigration;
import org.jeesl.interfaces.model.with.code.EjbWithCode;
import org.jeesl.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import org.jeesl.interfaces.model.with.position.EjbWithPosition;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslIoSsiCredential <SYSTEM extends JeeslIoSsiSystem>
								extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
										EjbWithCode,EjbWithPosition,EjbWithParentAttributeResolver,
										EjbWithVisibleMigration
{	
	public enum Attributes {system}
	
	public SYSTEM getSystem();
	public void setSystem(SYSTEM system);

	String getUser();
	void setUser(String user);
	
	String getPwd();
	void setPwd(String pwd);
	
	String getToken();
	void setToken(String token);
	
	String getUrl();
	void setUrl(String url);
	
	boolean isEncrypted();
	void setEncrypted(boolean encrypted);
}