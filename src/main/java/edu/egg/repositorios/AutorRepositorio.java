package edu.egg.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.entidades.Autor;
import edu.egg.entidades.Libro;


@Repository
public interface AutorRepositorio extends JpaRepository<Autor,String>{

    
    @Query("SELECT l FROM Autor l WHERE l.nombre = :nombre")	
	public List<Libro> buscarAutorPorNombre(@Param("nombre") String nombre);
    

	@Query("SELECT l FROM Autor l WHERE l.id = :id")
	public List<Libro> buscarAutorPorid(@Param("id") String id);
}
