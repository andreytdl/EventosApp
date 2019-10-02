package br.com.mblabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.mblabs.model.Evento;
import br.com.mblabs.model.Usuario;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

	List<Evento> findAllByLocalIgnoreCaseContaining(String local);

	List<Evento> findAllByLocalAndData(String local, String data);

	List<Evento> findAllByLocalAndDataAndTipo(String local, String data, String tipo);

	List<Evento> findAllByData(String data);
 
	List<Evento> findAllByDataAndTipo(String data, String tipo);

	List<Evento> findAllByTipo(String tipo);

	List<Evento> findAllByLocalAndTipo(String local, String tipo);

	List<Evento> findAllById(long id);
	
	List<Evento> findById(long id);
	
	List<Evento> findByUsuarios_login(String login); //Todos os eventos na lista do usuário X

	//List<Usuario> findUsuario_loginByid(long id);
	
	
}

