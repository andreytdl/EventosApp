package br.com.mblabs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.mblabs.model.Evento;
import br.com.mblabs.model.Roles;
import br.com.mblabs.model.Usuario;
import br.com.mblabs.repository.EventoRepository;
import br.com.mblabs.repository.RolesRepository;
import br.com.mblabs.repository.UsuarioRepository;

@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
@ComponentScan({"br.com.mblabs.controller", "br.com.mblabs"})
@EnableJpaRepositories(basePackages="br.com.mblabs.repository")
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class EventoappApplication{

	public static void main(String[] args) {
		SpringApplication.run(EventoappApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(EventoRepository er, UsuarioRepository ur, RolesRepository rr) {
        
		Roles ADM = new Roles();
		ADM.setNomeRole("ROLE_ADMIN");
		
		Roles user = new Roles();
		user.setNomeRole("ROLE_USER");
		
		rr.save(ADM);
		rr.save(user);
		
		Usuario u1 = new Usuario();
		u1.setLogin("andrey@gmail.com");
	//$2a$10$vUh24GlFXBFAtrM6Mkl5vul4UnSAhIMK4.Mf1uKWivkDq0bfinUEm" -> new BCryptPasswordEncoder().encode("123")
		u1.setSenha(new BCryptPasswordEncoder().encode("123"));
		u1.setNome("Andrey");
		u1.setCidade("Campinas");
		List<Roles> roles = new ArrayList<Roles>();
		roles.add(ADM);
		List<Evento> eventosLista = new ArrayList<Evento>();
		u1.setRoles(roles);
		ur.save(u1); 
		
		Usuario u2 = new Usuario();
		u2.setLogin("leonardo@gmail.com");
		u2.setSenha(new BCryptPasswordEncoder().encode("123"));
		u2.setNome("Leonardo");
		u2.setCidade("Campinas");
		List<Roles> roles2 = new ArrayList<Roles>();
		roles2.add(user);
		u2.setRoles(roles2);
		ur.save(u2);
		System.out.println(roles2);
		
		
		Evento ev1 = new Evento();
		ev1.setId(1);
		ev1.setNome("IFCH".toUpperCase());
		ev1.setLocal("UNICAMP".toUpperCase());
		ev1.setData("2019-10-11");
		ev1.setHorario("23:00");
		ev1.setTipo("universitario");
		ev1.setValor(20);
		ev1.setDescricao("A festa mais conhecida da Unicamp está de volta");
		
		eventosLista.add(ev1); //usuario
		u1.setEventos(eventosLista);
		
		Evento ev2 = new Evento();
		ev2.setId(2);
		ev2.setNome("Superlogica Experience".toUpperCase());
		ev2.setLocal("Royal Palm".toUpperCase());
		ev2.setData("2019-10-05");
		ev2.setHorario("09:00");
		ev2.setTipo("empresarial");
		ev2.setValor(500);
		ev2.setDescricao("Evento de empreendorismo");
		
		Evento ev3 = new Evento();
		ev3.setId(3);
		ev3.setNome("Festa do Gabriel".toUpperCase());
		ev3.setLocal("Republica do Gabriel".toUpperCase());
		ev3.setData("2019-10-04");
		ev3.setHorario("23:00");
		ev3.setTipo("universitario");
		ev3.setValor(30);
		ev3.setDescricao("Open de tudo e festa até as 5 da manhã");
		
		Evento ev4 = new Evento();
		ev4.setId(4);
		ev4.setNome("MB Evento".toUpperCase());
		ev4.setLocal("Royal Palm".toUpperCase());
		ev4.setData("2019-10-18");
		ev4.setHorario("09:00");
		ev4.setTipo("empresarial");
		ev4.setValor(200);
		ev4.setDescricao("Evento de empreendorismo e inovação");
		
		List<Evento> eventos = new ArrayList<Evento>();
		eventos.add(ev1);
		eventos.add(ev2);
		eventos.add(ev3);
		eventos.add(ev4);
		
		er.save(ev1);
		er.save(ev2);
		er.save(ev3);
		er.save(ev4);
		return null;
	
	}
	
}
