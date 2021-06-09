package org.jeesl.util.web;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

public class RestPlainAuthentication implements ClientRequestFilter
{
   private final String authHeader;

   public RestPlainAuthentication(String header)
   {
      authHeader = header;
   }

   @Override
   public void filter(ClientRequestContext requestContext) throws IOException
   {
      requestContext.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, authHeader);
   }
}
