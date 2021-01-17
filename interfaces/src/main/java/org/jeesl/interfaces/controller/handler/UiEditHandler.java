package org.jeesl.interfaces.controller.handler;

import java.io.Serializable;

//jeesl.highlight:showcase
public interface UiEditHandler extends Serializable
{
	boolean isAllow();
	boolean isDeny();
}
//jeesl.highlight:showcase