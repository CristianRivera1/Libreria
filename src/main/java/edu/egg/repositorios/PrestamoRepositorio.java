package edu.egg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.entidades.Libro;
import edu.egg.entidades.Prestamo;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo,String>{

@Query("SELECT l FROM Prestamo l WHERE l.id = :id")
	
	public Prestamo buscarprestamosporId(@Param("id") String id);
 
}
