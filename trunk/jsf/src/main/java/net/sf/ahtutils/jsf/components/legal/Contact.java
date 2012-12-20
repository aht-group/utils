package net.sf.ahtutils.jsf.components.legal;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="net.sf.ahtutils.jsf.components.legal.Contact")
public class Contact extends UINamingContainer
{
	final static Logger logger = LoggerFactory.getLogger(Contact.class);
	
	public String decode(String input)
	{
		return new String(Base64.decodeBase64(input));
	}
	
	public String encode(String input)
	{
		return Base64.encodeBase64URLSafeString(input.getBytes());
	}
}