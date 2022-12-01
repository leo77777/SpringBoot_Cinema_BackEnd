package fr.leo.cinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import fr.leo.cinema.entities.Place;
@RepositoryRestResource 
@CrossOrigin("*")
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
