package fr.leo.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.leo.cinema.dao.CategorieRepository;
import fr.leo.cinema.dao.CinemaRepository;
import fr.leo.cinema.dao.FilmRepository;
import fr.leo.cinema.dao.PlaceRepository;
import fr.leo.cinema.dao.ProjectionRepository;
import fr.leo.cinema.dao.SalleRepository;
import fr.leo.cinema.dao.SeanceRepository;
import fr.leo.cinema.dao.TicketRepository;
import fr.leo.cinema.dao.VilleRepository;
import fr.leo.cinema.entities.Categorie;
import fr.leo.cinema.entities.Cinema;
import fr.leo.cinema.entities.Film;
import fr.leo.cinema.entities.Place;
import fr.leo.cinema.entities.Projection;
import fr.leo.cinema.entities.Salle;
import fr.leo.cinema.entities.Seance;
import fr.leo.cinema.entities.Ticket;
import fr.leo.cinema.entities.Ville;

@Service
@Transactional // Une telle anotation au niveau de la classe, signifie que toutes les 
				// méthodes au niveau de la classe sont transactionnelles !
				// A chaque fois que on appelle une méthode,on a un "Begin Transaction",
				// et à la fin de la méthode un "Commit" ou "Rollback" !
				// Et donc toutes les opérations de la méthode passent, sinon si il y en 
				// a une qui échoue, et bien il y a un rollback !
public class CinemaInitServiceImpl implements ICinemaInitService {

	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public void initVilles() {
		Stream.of("Metz", "Nice", "Libourne", "Medun").forEach(nameVille -> {
			Ville ville = new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(ville -> {
			Stream.of("Kenopolis", "Imax", "Rex", "Scala", "Dollies").forEach(nameCinema -> {
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setVille(ville);
				cinema.setNombreSalles(3 + (int) (Math.random() * 7));
				cinemaRepository.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(cinema -> {
			for (int i = 0; i < cinema.getNombreSalles(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle" + (i + 1));
				salle.setCinema(cinema);
				salle.setNombrePlaces(14 + (int) (Math.random() * 20));
				salleRepository.save(salle);
			}
		});

	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle -> {
			for (int i = 0; i < salle.getNombrePlaces(); i++) {
				Place place = new Place();
				place.setNumero(i + 1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});

	} 

	@Override
	public void initSeances() {
		Stream.of("12:00", "15:00", "17:00", "19:00", "21:00").forEach(s -> {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm");
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			seanceRepository.save(seance);
		});
	}

	@Override
	public void initCategories() {
		Stream.of("Histoire", "Action", "Fiction", "Comique").forEach(cat -> {
			Categorie categorie = new Categorie();
			categorie.setName(cat);	
			categorieRepository.save(categorie);
		});
	}

	@Override
	public void initFilms() {
		List<Categorie> categories = categorieRepository.findAll();
		double[] durees = new double[] {1,1.5,2,2.5,3};
		Stream.of("Cyrano", "The Happy Road", "Visit To A Small Planet",
					"The Snaggy Dog" , "A Star Is Born").forEach(titreFilm -> {
				Film film = new Film();
				film.setTitre(titreFilm);
				film.setDuree(durees[new Random().nextInt(durees.length)]);
				film.setPhoto(titreFilm.replaceAll(" ", "") + ".jpg");
				film.setCategorie(categories.get(new Random().nextInt(categories.size())));
				filmRepository.save(film);
		});
	}

	@Override
	public void initProjections() {
		double[] prices = new double[] {30,50,60,70,90,100}; 
		List<Film> films = filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{					
					int index = new Random().nextInt(films.size());
					Film film = films.get(index);
					//filmRepository.findAll().forEach(film->{
						seanceRepository.findAll().forEach(seance->{
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(salle);
							projection.setSeance(seance);
							projectionRepository.save(projection);							
						});
					//});
				});
			});
		});
	}

	@Override
	public void initTickets() {
		projectionRepository.findAll().forEach(projection->{
			projection.getSalle().getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(projection.getPrix());
				ticket.setProjection(projection);
				ticket.setReserve(false);
				ticketRepository.save(ticket);	
			});
		});

	}

}
