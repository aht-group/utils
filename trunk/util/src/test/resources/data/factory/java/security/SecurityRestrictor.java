package util;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import util.AbstractRestrictor;

import my.package.xx.UtilsMyCode;

public class Restrictor extends AbstractRestrictor
{
	public @Secures @UtilsMyCode boolean isUtilsMyCode(Identity identity) {return super.secureView(identity, "myCode");}
}