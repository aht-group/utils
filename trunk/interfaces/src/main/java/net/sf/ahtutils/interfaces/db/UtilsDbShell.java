package net.sf.ahtutils.interfaces.db;

import java.io.File;
import java.util.List;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.interfaces.util.TextWriter;

public interface UtilsDbShell
{
	public static enum Operation {dump,restore}
	
	public static final String cfgBinDump = "db.bin.dump";
	public static final String cfgBinRestore = "db.bin.restore";
	
	public static final String cfgDbTables = "db.tables.table";
	
	public static final String cfgDirSql = "db.dir.sql";
	public static final String cfgDirShell = "db.dir.shell";
	
	TextWriter getWriter();
	File getShellFile();
	
	void discoverTables();
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException;
	List<String> getTables();
}
