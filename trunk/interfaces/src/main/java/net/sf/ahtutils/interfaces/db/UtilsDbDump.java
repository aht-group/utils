package net.sf.ahtutils.interfaces.db;

public interface UtilsDbDump
{
	public static enum Operation {dump,restore}
	
	public static final String cfgBinDump = "db.bin.dump";
	public static final String cfgDbTables = "db.tables.table";
	
	public static final String cfgDirSql = "db.dir.sql";
	public static final String cfgDirShell = "db.dir.shell";
	
}
