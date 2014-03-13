package net.sf.ahtutils.interfaces.bl;

import java.io.File;

public interface UtilsPathBl
{	
	File getDir(String code);
	File getImageTmpDir();
	File getDataDir();
}