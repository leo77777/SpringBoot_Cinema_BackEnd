package fr.leo.cinema.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;
@Projection(name = "p1" , types = { fr.leo.cinema.entities.Projection.class })
public interface ProjectionProj {		
	public Long getId();
	public double getPrix();
	public Date getDateProjection();
	public Film getFilm();
	public Salle getSalle();
	public Seance getSeance();
	public Collection<Ticket> getTickets();
}
