package net.sf.ahtutils.model.ejb.security;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
import net.sf.ahtutils.model.interfaces.security.UtilsSecurityRole;
import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@EjbErNode(name="Role",category="security")

public class AhtUtilsSecurityRole implements EjbWithCode,Serializable,EjbRemoveable,EjbPersistable,
	UtilsSecurityRole<AhtUtilsLang,AhtUtilsDescription,AhtUtilsSecurityCategory,AhtUtilsSecurityRole,AhtUtilsSecurityView,AhtUtilsSecurityUsecase,AhtUtilsSecurityAction,AhtUtilsUser>
{
	public static enum Code {systemAht}
	public static enum CodeRegion {regionalManager,regionalEditor}
	
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull @ManyToOne
	private AhtUtilsSecurityCategory category;
	
	@NotNull
	private String code;
	
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
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<AhtUtilsSecurityView> views;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<AhtUtilsSecurityAction> actions;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<AhtUtilsSecurityUsecase> usecases;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "User_SecurityRole")
	private List<AhtUtilsUser> users;
	
	//******************************************************************************
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public AhtUtilsSecurityCategory getCategory() {return category;}
	public void setCategory(AhtUtilsSecurityCategory category) {this.category = category;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
		
	public List<AhtUtilsSecurityView> getViews() {if(views==null){views = new ArrayList<AhtUtilsSecurityView>();}return views;}
	public void setViews(List<AhtUtilsSecurityView> views) {this.views = views;}
	
	public List<AhtUtilsSecurityAction> getActions() {if(actions==null){actions = new ArrayList<AhtUtilsSecurityAction>();}return actions;}
	public void setActions(List<AhtUtilsSecurityAction> actions) {this.actions = actions;}
	
	public List<AhtUtilsSecurityUsecase> getUsecases() {if(usecases==null){usecases = new ArrayList<AhtUtilsSecurityUsecase>();}return usecases;}
	public void setUsecases(List<AhtUtilsSecurityUsecase> usecases) {this.usecases = usecases;}
	
	public List<AhtUtilsUser> getUsers() {return users;}
	public void setUsers(List<AhtUtilsUser> users) {this.users = users;}
	
	//******************************************************************************
	
	public boolean equals(Object object)
	{
        return (object instanceof AhtUtilsSecurityRole)
             ? id == ((AhtUtilsSecurityRole) object).getId()
             : (object == this);
    }
	
	public int hashCode()
	{
		int hashCode = 11;
		int multi = 29;

		hashCode = hashCode * multi + (int)(this.id & 0xFFFFFFFF);
		hashCode = hashCode * multi + (int)(this.id >>> 32);
		return hashCode;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append(AhtUtilsSecurityRole.class.getSimpleName());
			sb.append("-").append(id);
			sb.append(" ").append(code);
			sb.append("]");
		return sb.toString();
	}
}