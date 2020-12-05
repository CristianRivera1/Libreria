package edu.egg.repositorios;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.entidades.Editorial;
import edu.egg.entidades.Foto;
import edu.egg.entidades.Libro;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto,String>{
	
	@Query("SELECT l FROM Foto l WHERE l.id = :id")
	public List<Foto> buscarLibroPorid(@Param("id") String id);

}
