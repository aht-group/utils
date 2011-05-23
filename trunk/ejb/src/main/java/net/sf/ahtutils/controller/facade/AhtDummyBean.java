package net.sf.ahtutils.controller.facade;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import net.sf.ahtutils.controller.interfaces.AhtDummy;

@Stateful
@Remote
public class AhtDummyBean implements AhtDummy,Serializable
{
	static final long serialVersionUID=1;
	
}