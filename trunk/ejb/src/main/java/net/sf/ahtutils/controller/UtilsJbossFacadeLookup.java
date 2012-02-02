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

public class UtilsJbossFacadeLookup
{
	private String appName;
	private String moduleName;
	
	public UtilsJbossFacadeLookup(String appName, String moduleName)
	{
		this.appName=appName;
		this.moduleName=moduleName;
	}
	
   @SuppressWarnings("unchecked")
   public <F extends Object> F lookup(Class<F> facade, Class<?> bean) throws NamingException
    {
        final Hashtable<String,String> jndiProperties = new Hashtable<String,String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
       
        // The app name is the application name of the deployed EJBs. This is typically the ear name
        // without the .ear suffix. However, the application name could be overridden in the application.xml of the
        // EJB deployment on the server.
        // Since we haven't deployed the application as a .ear, the app name for us will be an empty string
        
        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
        // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
        // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
        // jboss-as-ejb-remote-app
       
        // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
        // our EJB deployment, so this is an empty string
        final String distinctName = "";
      
        // The EJB name which by default is the simple class name of the bean implementation class
        final String beanName = bean.getSimpleName();
        // the remote view fully qualified class name
        
        final String viewClassName = facade.getName();
        // let's do the lookup
        
        StringBuffer sb = new StringBuffer();
        sb.append("ejb:");
        sb.append(appName).append("/");
        sb.append(moduleName).append("/");
        sb.append(distinctName).append("/");
        sb.append(beanName);
        sb.append("!").append(viewClassName);
                
        return (F) context.lookup(sb.toString());
    }
}