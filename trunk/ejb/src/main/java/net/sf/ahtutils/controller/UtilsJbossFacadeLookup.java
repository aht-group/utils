/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package net.sf.ahtutils.controller;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsJbossFacadeLookup
{
	final static Logger logger = LoggerFactory.getLogger(UtilsJbossFacadeLookup.class);
	
	private String appName;
	private String moduleName;
	private String host;
	private String username;
	private String password;
	
	public UtilsJbossFacadeLookup(String appName, String moduleName, String host)
	{
		this(appName,moduleName,host,null,null);
	}
	public UtilsJbossFacadeLookup(String appName, String moduleName, String host, String username, String password)
	{
		this.appName=appName;
		this.moduleName=moduleName;
		this.host=host;
		this.username=username;
		this.password=password;
	}
	   
	@SuppressWarnings("unchecked")
	public <F extends Object> F lookup(Class<F> facade) throws NamingException
    {
        final Context context = createContext();
        
        final String distinctName = "";
 
        final String beanName = facade.getSimpleName()+"Bean";
        final String viewClassName = facade.getName();
        
        StringBuffer sb = new StringBuffer();
        sb.append("ejb:");
        sb.append(appName).append("/");
        sb.append(moduleName).append("/");
        sb.append(distinctName).append("/");
        sb.append(beanName);
        sb.append("!").append(viewClassName);
        logger.trace("Trying: "+sb.toString());
        return (F) context.lookup(sb.toString());
    }
   
   private Context createContext() throws NamingException
   {
       final Hashtable<String,String> jndiProperties = new Hashtable<String,String>();
       jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//       jndiProperties.put(Context.PROVIDER_URL, "remote://" +host +":4447");
//       if(username!=null){jndiProperties.put(Context.SECURITY_PRINCIPAL, username);}
//       if(password!=null){jndiProperties.put(Context.SECURITY_CREDENTIALS, password);}
       return new InitialContext(jndiProperties);
   }
}