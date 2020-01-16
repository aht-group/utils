package org.jeesl.factory.sql.system.security;

import org.jeesl.interfaces.model.system.security.user.JeeslUser;

public class SqlUserFactory <USER extends JeeslUser<?>>
{
	public SqlUserFactory()
	{
		
	}
	
	public String updateSalt(String table, USER user, String salt)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(table);
		sb.append(" SET salt='").append(salt).append("'");
		sb.append(" WHERE id=").append(user.getId());
		sb.append(";");
		
		return sb.toString();
	}
	
	public String updatePwd(String table, USER user, String pwd)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(table);
		sb.append(" SET pwd='").append(pwd).append("'");
		sb.append(" WHERE id=").append(user.getId());
		sb.append(";");
		
		return sb.toString();
	}
	
	public String updatePwd(String table, USER user, String pwd, long rev)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(table);
		sb.append(" SET pwd='").append(pwd).append("'");
		sb.append(" WHERE id=").append(user.getId());
		sb.append("   AND rev=").append(rev);
		sb.append(";");
		
		return sb.toString();
	}
}