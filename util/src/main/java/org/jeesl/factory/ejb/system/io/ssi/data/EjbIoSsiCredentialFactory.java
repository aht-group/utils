package org.jeesl.factory.ejb.system.io.ssi.data;

import java.util.List;

import org.jeesl.factory.ejb.util.EjbPositionFactory;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiCredential;
import org.jeesl.interfaces.model.system.io.ssi.data.JeeslIoSsiSystem;

public class EjbIoSsiCredentialFactory <SYSTEM extends JeeslIoSsiSystem,
										CRED extends JeeslIoSsiCredential<SYSTEM>>
{
	private final Class<CRED> cCredential;

	public EjbIoSsiCredentialFactory(final Class<CRED> cCredential)
	{
        this.cCredential = cCredential;
	}
	
	public CRED build(SYSTEM system, List<CRED> list)
	{
		CRED ejb = null;
		try
		{
			ejb = cCredential.newInstance();
	        ejb.setSystem(system);
	        ejb.setVisible(false);
	        ejb.setEncrypted(false);
	        EjbPositionFactory.next(ejb, list);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}    
}