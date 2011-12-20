package net.sf.ahtutils.controller.servlet.facebook;

import javax.servlet.http.HttpSession;

import net.sf.ahtutils.xml.cloud.facebook.App;
import net.sf.ahtutils.xml.cloud.facebook.SignedRequest;

public interface FbLoginCallback
{
	void receivedOauth(SignedRequest sr, HttpSession session);
	void receivedAppAuthCode(App app, String code, HttpSession session);
}
