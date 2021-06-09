package org.jeesl.util.web;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestLogger implements ClientRequestFilter
{
	final static Logger logger = LoggerFactory.getLogger(RestLogger.class);
	
	@Override
    public void filter(ClientRequestContext ctx) throws IOException
	{
        logger.info(ctx.getMethod());
        StringBuilder sb = new StringBuilder();
        sb.append(ctx.getUri().toString());
        
        logger.info("URL "+sb.toString());
        logger.info("Header:");
        for(String key : ctx.getStringHeaders().keySet())
        {
        	logger.info(key+": "+ctx.getStringHeaders().get(key));
        }

        logger.info("Properties:");
        for(String prop : ctx.getPropertyNames())
        {
        	logger.info(prop+": "+ctx.getProperty(prop));
        }        
    }
}
