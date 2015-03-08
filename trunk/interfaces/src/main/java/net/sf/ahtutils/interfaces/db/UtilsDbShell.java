package net.sf.ahtutils.interfaces.db;

import java.io.File;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.interfaces.util.TextWriter;
import net.sf.exlp.xml.config.Parameters;

public interface UtilsDbShell
{
	public static enum Operation {dump,restore,drop}
	
	public static final String cfgBinDump = "db.bin.dump";
	public static final String cfgBinRestore = "db.bin.restore";
	public static final String cfgBinDrop = "db.bin.drop";
	
	public static final String cfgDbTablesDrop = "db.tables.drop.table";
	public static final String cfgDbTablesRestore = "db.tables.restore.table";
	public static final String cfgDbTablesKey = "db.tables.key.table";
	
	public static final String cfgDirSql = "db.dir.sql";
	public static final String cfgDirShell = "db.dir.shell";
	
	TextWriter getWriter();
	File getShellFile();
	
	public void buildCommands(boolean withStructure) throws ExlpUnsupportedOsException;
	
	Parameters getConfigurationParameter();
}
