package net.sf.ahtutils.model.ejb.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.ejb.security.AhtUtilsSecurityAction;
import net.sf.ahtutils.model.ejb.security.AhtUtilsSecurityCategory;
import net.sf.ahtutils.model.ejb.security.AhtUtilsSecurityRole;
import net.sf.ahtutils.model.ejb.security.AhtUtilsSecurityUsecase;
import net.sf.ahtutils.model.ejb.security.AhtUtilsSecurityView;
import net.sf.ahtutils.model.ejb.status.AhtUtilsDescription;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.crud.EjbRemoveable;
import net.sf.ahtutils.model.interfaces.idm.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@Entity
@Table(name="UtilsMeis", uniqueConstraints= {
        @UniqueConstraint(name="unique-email", columnNames = {"email"})
})
@EjbErNode(name="User",category="user")
@NamedQueries
({	
	@NamedQuery(name="fUserByEmail",query="SELECT u FROM MeisUser u WHERE u.email = :email")
})
public class AhtUtilsUser implements Serializable,EjbWithId,EjbPersistable,EjbRemoveable,
									UtilsUser<AhtUtilsLang,AhtUtilsDescription,AhtUtilsSecurityCategory,AhtUtilsSecurityRole,AhtUtilsSecurityView,AhtUtilsSecurityUsecase,AhtUtilsSecurityAction,AhtUtilsUser>
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
    @Override public long getId() {return id;}
    @Override public void setId(long id) {this.id = id;}

	@NotNull @Column(unique=true)
	protected String email;
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
	
	protected String pwd;
    public String getPwd() {return pwd;}
    public void setPwd(String pwd) {this.pwd = pwd;}

	protected String firstName;
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    protected String lastName;
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
	
	private String lang;
    public String getLang() {return lang;}
    public void setLang(String lang) {this.lang = lang;}
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="users")
	private List<AhtUtilsSecurityRole> roles;
    public List<AhtUtilsSecurityRole> getRoles() {if(roles==null){roles = new ArrayList<AhtUtilsSecurityRole>();};return roles;}
    public void setRoles(List<AhtUtilsSecurityRole> roles) {this.roles = roles;}

	// >>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" ").append(firstName).append(" ").append(lastName);
		return sb.toString();
	}
	
	public boolean equals(Object object)
	{
        return (object instanceof AhtUtilsUser) ? id == ((AhtUtilsUser) object).getId() : (object == this);
    }
}