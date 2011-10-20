package net.sf.ahtutils.model.ejb.status;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;

@Entity
@Table(name = "UtilsLang")
public class AhtUtilsLang implements UtilsLang,EjbRemoveable,Serializable
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String lkey;
	
	@NotNull
	private String lang;
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getLkey() {return lkey;}
	public void setLkey(String lkey) {this.lkey = lkey;}
	
	public String getLang() {return lang;}
	public void setLang(String name) {this.lang = name;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ["+lkey+"]");
			sb.append(" "+lang);
		return sb.toString();
	}
	
	public synchronized static Map<String,AhtUtilsLang> createMap(AhtUtilsLang... languages)
	{
		Map<String,AhtUtilsLang> langMap = new Hashtable<String, AhtUtilsLang>();
		for(AhtUtilsLang lang : languages)
		{
			langMap.put(lang.getLkey(), lang);
		}
		return langMap;
	}
	
	public synchronized static AhtUtilsLang create(String key, String lang)
	{
		AhtUtilsLang pl = new AhtUtilsLang();
		pl.setLkey(key);
		pl.setLang(lang);
		return pl;
	}
}