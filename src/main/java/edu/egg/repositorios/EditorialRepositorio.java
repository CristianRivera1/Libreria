package edu.egg.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.entidades.Editorial;
import edu.egg.entidades.Libro;

@Repository

public interface EditorialRepositorio extends JpaRepository<Editorial,String>{

	
@Query("SELECT l FROM Editorial l WHERE l.nombre = :nombre")	
	public List<Libro> buscarEditorialPorNombre(@Param("nombre") String nombre);


@Query("SELECT l FROM Editorial l WHERE l.id = :id")
public List<Libro> buscarLibroPorid(@Param("id") String id);
}
