package fr.leo.cinema.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.MediaList;

import fr.leo.cinema.dao.FilmRepository;
import fr.leo.cinema.dao.TicketRepository;
import fr.leo.cinema.entities.Film;
import fr.leo.cinema.entities.Ticket;
import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {	
	
/*	@Autowired
	private FilmRepository filmRepository;	
	//@RequestMapping(value="/listFilms" , method = RequestMethod.GET)
	@GetMapping("/listFilms")
	public List<Film> films(){
		return filmRepository.findAll();		
	} */

	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	
	@GetMapping(path="/imageFilm/{id}" , produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image( @PathVariable (name="id") Long id) throws Exception {
		
		Film film =  filmRepository.findById(id).get();
		String photoName = film.getPhoto();
		File file = new File(System.getProperty("user.home") + "\\cinema\\images\\" + photoName  );
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path) ;	
	}	

	// @RequestBody <= vous indiquez que les données sont dans la requete au format json !
	@Transactional
	@PostMapping(path="/payerTickets")
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){		
		List<Ticket> listTickets = new ArrayList<>();
		ticketForm.getTickets().forEach(id->{ 
			Ticket ticket = ticketRepository.findById(id).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setCodePayement(ticketForm.getCodePayement());
			ticket.setReserve(true);
			ticketRepository.save(ticket);
			listTickets.add(ticket);
		});
		return listTickets;
	}

}

@Data
 class TicketForm{	
	 private String nomClient;
	 private int codePayement;
	 private List<Long> tickets = new ArrayList<>();
}






 

