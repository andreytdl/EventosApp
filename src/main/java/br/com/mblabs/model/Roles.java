package br.com.mblabs.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Roles implements GrantedAuthority {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String nomeRole;

	@ManyToMany
	private List<Usuario> usuarios;
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return getNomeRole();
	}
	public String ToString() {
		return getNomeRole();
	}

	private String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}
	


}
