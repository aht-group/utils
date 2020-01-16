package org.jeesl.factory.json.system.io.ssi;

import org.jeesl.model.json.system.io.ssi.SsiCrendentials;

public class JsonSsiCredentialFactory
{
	public static SsiCrendentials build(String user, String pwd){return build(null,user,pwd);}
	public static SsiCrendentials build(String host, String user, String pwd)
	{
		SsiCrendentials json = new SsiCrendentials();
		json.setUser(user);
		json.setPassword(pwd);
		json.setHost("hmis.moh.gov.rw");
		return json;
	}
}