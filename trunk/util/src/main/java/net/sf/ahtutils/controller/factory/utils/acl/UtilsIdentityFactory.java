package net.sf.ahtutils.controller.factory.utils.acl;

import net.sf.ahtutils.model.interfaces.EjbWithId;
import net.sf.ahtutils.model.interfaces.acl.UtilsIdentity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsIdentityFactory<I extends UtilsIdentity<U>,U extends EjbWithId>
{
	final static Logger logger = LoggerFactory.getLogger(UtilsIdentityFactory.class);
	
	final Class<U> clUser;
	final Class<I> clIdentity;
	
    public UtilsIdentityFactory(final Class<I> clIdentity,final Class<U> clUser)
    {
    	this.clIdentity=clIdentity;
        this.clUser = clUser;
    } 
    
    public static <I extends UtilsIdentity<U>,U extends EjbWithId> UtilsIdentityFactory<I,U>
    		createFactory(final Class<I> clIdentity,final Class<U> clUser)
    {
        return new UtilsIdentityFactory<I,U>(clIdentity,clUser);
    }
}