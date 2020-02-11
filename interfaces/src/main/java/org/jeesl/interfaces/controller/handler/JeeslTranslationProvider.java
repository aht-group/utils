package org.jeesl.interfaces.controller.handler;

import java.util.List;

import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.openfuxml.interfaces.configuration.OfxTranslationProvider;

public interface JeeslTranslationProvider <LOC extends JeeslStatus<LOC,?,?>> extends OfxTranslationProvider
{
	void setLanguages(List<LOC> locales);
}