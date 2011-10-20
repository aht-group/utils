package net.sf.ahtutils.model.ejb.status;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.sf.ahtutils.model.interfaces.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
@DiscriminatorValue("generic")
@Table(name = "UtilsStatus", uniqueConstraints = @UniqueConstraint(columnNames = {"type","code"}))
public class AhtUtilsStatus implements UtilsStatus<AhtUtilsLang>,EjbRemoveable,Serializable
{
	private static final long serialVersionUID = 1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	protected Map<String, AhtUtilsLang> name;
	
	protected String code;
	
	protected boolean visible;

	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<
	
	public void setId(long id) {this.id = id;}
	public long getId() {return id;}
	
	public Map<String, AhtUtilsLang> getName(){if(name==null){name = new Hashtable<String, AhtUtilsLang>();}return name;}
	public void setName(Map<String, AhtUtilsLang> name) {this.name = name;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public boolean isVisible() {return visible;}
	public void setVisible(boolean visible) {this.visible = visible;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" code="+code);
		return sb.toString();
	}
}