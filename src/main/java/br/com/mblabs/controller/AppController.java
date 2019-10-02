/**
 * 
 */
package br.com.mblabs.controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.mblabs.model.Evento;
import br.com.mblabs.model.Usuario;
import br.com.mblabs.repository.EventoRepository;
import br.com.mblabs.repository.UsuarioRepository;

/**
 * @author Andrey Torres
 *
 */
@Controller
public class AppController {
	
	private EventoRepository er;
	private UsuarioRepository ur;

	   AppController(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
	       this.er = eventoRepository;
	       this.ur = usuarioRepository;
	   }
	   
	   

	@RequestMapping("/")
	public ModelAndView index(OAuth2AuthenticationToken authentication) {
		
		
		
		Map<String, Object> userInfo = authentication.getPrincipal().getAttributes();
		String nome = (String) userInfo.get("name"); //Pegar todos os eventos que o login do usuário é x e setar a lista no usuário x
		System.out.println(nome);
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("nome", nome);
		System.out.println("Index"); 
		return mv;
	}
/*	@RequestMapping("/")
	public ModelAndView index() {
		String nome = "Blaa";
		System.out.println(nome);
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("nome", nome);
		System.out.println("Index"); 
		return mv;
	}*/
	
/*	@RequestMapping("/eventos")
	public ModelAndView eventos() {
		System.out.println("Eventos");
		
		ModelAndView mv = new ModelAndView("eventos");
		List<Evento> eventos = er.findAll();
		mv.addObject("eventos", eventos);
		
		return mv;
	}
	*/
	@RequestMapping("/eventos/{id}")
	public ModelAndView DetalhesEvento(@PathVariable("id") long id, OAuth2AuthenticationToken authentication) {
		System.out.println("Entrei Detalhes do Evento");
		Map<String, Object> userInfo = authentication.getPrincipal().getAttributes();
		String usuarioLogin = (String) userInfo.get("email"); //Pegar todos os eventos que o login do usuário é x e setar a lista no usuário x
		
		List<Evento> eventos = er.findByUsuarios_login(usuarioLogin);
		Usuario usuario = ur.findByLogin(usuarioLogin);
		usuario.setEventos(eventos);
		List<Usuario> usuarios = ur.findByEventos_id(id);
		
		ModelAndView mv = new ModelAndView("DetalhesEvento");
		List<Evento> evento = er.findAllById(id);
		System.out.println(evento.get(0).getNome());
		mv.addObject("evento", evento);
		
		//List<Usuario> usuarios = er.findUsuario_loginByid(id);
		//System.out.println("Encontrado: "+ er.findUsuario_loginByid(id));
		mv.addObject("usuarios", usuarios);
		
		
		
		
		return mv;
	}
	
	@GetMapping("eventos")
	public ModelAndView searchEventos(
			@RequestParam(name = "local", required = false) String local,
			@RequestParam(name = "data", required = false) String data,
			@RequestParam(name = "tipo", required = false)String tipo) {
			
		ModelAndView mv = new ModelAndView("eventos");
		mv.clear();
		System.out.println("Eventos");
		//System.out.printf("Local:%s\nData:%s\nTipo:%s", local, data, tipo);
		if(local == null && data == null && tipo == null) {
			//System.out.println("--Boa 06");
			List<Evento> encontrado = er.findAll();
			mv.addObject("eventos", encontrado);
		}
		
		else if(local != "" && data == "" && tipo =="") {
			//System.out.println("--Local");
			List<Evento> encontrado = er.findAllByLocalIgnoreCaseContaining(local);
			mv.addObject("eventos", encontrado);
		}
		else if(local != "" && data != "" && tipo =="") {
			//System.out.println("--Local e Data");
			List<Evento> encontrado = er.findAllByLocalAndData(local, data);
			mv.addObject("eventos", encontrado);
		}
		else if(local != "" && data != "" && tipo !="") {
			List<Evento> encontrado = er.findAllByLocalAndDataAndTipo(local, data, tipo);
			mv.addObject("eventos", encontrado);
		}
		else if(local == "" && data != "" && tipo =="") {
			//System.out.println("--Data");
			List<Evento> encontrado = er.findAllByData(data);
			mv.addObject("eventos", encontrado);
		}
		else if(local == "" && data != "" && tipo !="") {
			//System.out.println("--Data e Tipo");
			List<Evento> encontrado = er.findAllByDataAndTipo(data, tipo);
			mv.addObject("eventos", encontrado);
		}
		else if(local == "" && data == "" && tipo == "") {
			//System.out.println("--Todos");
			List<Evento> encontrado = er.findAll();
			mv.addObject("eventos", encontrado);
		}
		else if(local == "" && data == "" && tipo !="") {
			//System.out.println("--Tipo");
			List<Evento> encontrado = er.findAllByTipo(tipo);
			mv.addObject("eventos", encontrado);
		}
		else if(local != "" && data == "" && tipo !="") {
			//System.out.println("--Local e Tipo");
			List<Evento> encontrado = er.findAllByLocalAndTipo(local, tipo);
			mv.addObject("eventos", encontrado);
		}
		
		return mv;
	}
	
	@RequestMapping("/MeusEventos")
	public ModelAndView meusEventos(OAuth2AuthenticationToken authentication) { 
		System.out.println("Meus Eventos");
		Map<String, Object> userInfo = authentication.getPrincipal().getAttributes();
		String usuarioLogin = (String) userInfo.get("email");
		
		ModelAndView mv = new ModelAndView("MeusEventos");
		List<Evento> eventos = er.findByUsuarios_login(usuarioLogin);
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/AreaDoUsuario")
	public String Area() {
		return "AreaDoUsuario";
	}
	
	@RequestMapping("/CriarEvento")
	public String CriarEvento() {
		return "CriarEvento";
	}
	
	@Transactional
	@RequestMapping("/ComprarIngresso/{id}")
	public String ComprarIngresso(@PathVariable("id") long id, OAuth2AuthenticationToken authentication) {
		System.out.println("Comprar Ingressos");
		//Caso o usuário não esteja no banco cadastrar -> Por padrão caso exista o JPA não faz nada, então fica como um if ou update
		Map<String, Object> userInfo = authentication.getPrincipal().getAttributes();
		System.out.println(userInfo);
		String usuarioLogin = (String) userInfo.get("email");
		String usuarioNome = (String) userInfo.get("name");
		
		
		
		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioLogin);
		usuario.setNome(usuarioNome);
		//usuario.setCidade("campinas");
		//usuario.setSenha("blablabla");
		ur.save(usuario);
		
		List<Evento> evento = er.findAllById(id);
		Usuario comprador = ur.findByLogin(usuarioLogin);
				
		
		List<Usuario> listaUsuarios = evento.get(0).getUsuarios();
		if(comprador.getEventos() == null) {
			comprador.setEventos(evento);
		}else {
			comprador.getEventos().addAll(evento);
		}
		
		System.out.println("Eai ? " + comprador.getEventos());
		
		listaUsuarios.add(comprador);
		
		return "redirect:/eventos/{id}";
	}
}