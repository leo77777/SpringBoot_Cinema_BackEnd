package fr.leo.cinema.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Seance implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// Ci dessous on précise le format temps que l'on désire :
	//  TemporalType.TIMESTAMP   ou  TemporalType.DATE   ou TemporalType.TIME !
	@Temporal(TemporalType.TIME) // Heure, minutes, secondes !
	private Date heureDebut;
}
