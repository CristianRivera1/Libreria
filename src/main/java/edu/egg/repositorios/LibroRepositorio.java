package edu.egg.repositorios;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.entidades.Cliente;
import edu.egg.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,String>{

	@Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")	
	public Libro buscarLibroPorTitulo(@Param("titulo") String titulo);
	

	
}
