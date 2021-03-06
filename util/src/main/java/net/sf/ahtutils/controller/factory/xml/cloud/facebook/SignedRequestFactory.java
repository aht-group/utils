package net.sf.ahtutils.controller.factory.xml.cloud.facebook;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.cloud.facebook.SignedRequest;

public class SignedRequestFactory
{
	final static Logger logger = LoggerFactory.getLogger(SignedRequestFactory.class);
	
	private SignedRequest signedRequest;

	private String txtRaw;
	private String txtPayload;
	
	public SignedRequestFactory()
	{

	}
	
	public SignedRequest decode(String txt)
	{
		this.txtRaw=txt;
		logger.trace("Request.Raw: "+txtRaw);
		int idx = txtRaw.indexOf(".");

		String rawpayload = txtRaw.substring(idx+1);
		txtPayload = new String(new Base64(true).decode(rawpayload));
		logger.debug("Request.Dec: "+txtPayload);
		
		decodeWithJson(txtPayload);
		return signedRequest;
	}
	
	private void decodeWithJson(String s)
	{
		signedRequest = new SignedRequest();
		logger.warn("NYI: Deactivated to get rid of the org.json lib");
//		JSONObject json = null;
//		try
//		{
//			json = new JSONObject(s);
//			if(json.has("issued_at"))
//			{
//				Date issuedAt = new Date(json.getLong("issued_at")*1000);
//				logger.trace("IssuedAt: "+issuedAt);
//				signedRequest.setIssuedAt(DateUtil.toXmlGc(issuedAt));
//			}
//			if(json.has("expires"))
//			{
//				Date exipires = new Date(json.getLong("expires")*1000);
//				logger.trace("Expires: "+exipires);
//				signedRequest.setExpires(DateUtil.toXmlGc(exipires));
//			}
//			if(json.has("user_id"))
//			{
//				User user = new User();
//				user.setId(json.getString("user_id"));
//				signedRequest.setUser(user);
//			}
//			if(json.has("oauth_token"))
//			{
//				Oauth oauth = new Oauth();
//				oauth.setToken(json.getString("oauth_token"));
//				signedRequest.setOauth(oauth);
//				
//			}
//		}
//		catch (JSONException e) {e.printStackTrace();}
	}
	
	public SignedRequest getSignedRequest() {return signedRequest;}
	public String getTxtPayload() {return txtPayload;}
}