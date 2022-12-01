package fr.leo.cinema.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Projection implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dateProjection;
	private Double prix;
	
	@ManyToOne
	private Film film;	
	
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY) // car on l'a déjà !
	private Salle salle;	

	@OneToMany(mappedBy = "projection")
	@JsonProperty(access = Access.WRITE_ONLY) // ici on boucle sinon !
	private Collection<Ticket> tickets;
	
	// Ci dessous, on a une relation unidiretionnelle !
	@ManyToOne
	private Seance seance;

}
