package net.sf.ahtutils.model.ejb.security;


import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.ejb.user.AhtUtilsUser;
import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityCategory;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"type","code"}))
@EjbErNode(name="Category",category="security")
public class AhtUtilsSecurityCategory implements Serializable, EjbWithCode,EjbRemoveable,EjbPersistable,
	UtilsSecurityCategory<AhtUtilsLang,AhtUtilsDescription,AhtUtilsSecurityCategory,AhtUtilsSecurityRole,AhtUtilsSecurityView,AhtUtilsSecurityUsecase,AhtUtilsSecurityAction,AhtUtilsUser>
{
	public static final long serialVersionUID=2;
	
	public static enum Code{system,regional}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String code;
	
	@NotNull
	private String type;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsLang> name;
	public Map<String, AhtUtilsLang> getName() {return name;}
	public void setName(Map<String, AhtUtilsLang> name) {this.name = name;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@MapKey(name = "lkey")
	private Map<String, AhtUtilsDescription> description;
	public Map<String, AhtUtilsDescription> getDescription() {return description;}
	public void setDescription(Map<String, AhtUtilsDescription> description) {this.description = description;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	

	

	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append("id="+id);
		return sb.toString();
	}
	
	public boolean equals(Object object)
	{
        return (object instanceof AhtUtilsSecurityCategory)
             ? id == ((AhtUtilsSecurityCategory) object).getId()
             : (object == this);
    }
}