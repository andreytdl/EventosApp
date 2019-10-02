package br.com.mblabs.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails{

/*@GeneratedValue(strategy=GenerationType.IDENTITY)
@Id
private long id;
*/
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private String senha;
private String nome;
private String cidade;

@Id
private String login;
public String getSenha() {
	return senha;
}
public void setSenha(String senha) {
	this.senha = senha;
}

/*@ManyToMany
@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(
		name = "usuario_id", referencedColumnName = "login"), inverseJoinColumns = @JoinColumn(
		name = "role_id", referencedColumnName = "nomeRole"))*/
@ManyToMany
private List<Roles> roles;

@ManyToMany
private List<Evento> eventos;

public List<Evento> getEventos() {
	return eventos;
}
public void setEventos(List<Evento> eventos) {
	this.eventos = eventos;
}

public List<Roles> getRoles() {
	return roles;
}

public void setRoles(List<Roles> roles) {
	this.roles = roles;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getCidade() {
	return cidade;
}
public void setCidade(String cidade) {
	this.cidade = cidade;
}
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	return this.roles;
} 
@Override
public String getPassword() {
	// TODO Auto-generated method stub
	return this.senha;
}
@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return this.login;
}
@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}

}
