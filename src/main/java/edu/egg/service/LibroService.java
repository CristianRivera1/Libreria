package edu.egg.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.egg.entidades.Autor;
import edu.egg.entidades.Cliente;
import edu.egg.entidades.Editorial;
import edu.egg.entidades.Libro;
import edu.egg.error.ErrorService;
import edu.egg.repositorios.AutorRepositorio;
import edu.egg.repositorios.EditorialRepositorio;
import edu.egg.repositorios.LibroRepositorio;

@Service
public class LibroService {
	@Autowired
	private EditorialRepositorio editorialRepositorio;
	@Autowired
	private AutorRepositorio autorRepositorio;
	@Autowired
	private LibroRepositorio libroRepositorio;
	
	
	@Transactional
    public void agregarLibro(String isbn, String titulo, int anio ,int ejemplares,Integer prestados ,String idautor, String ideditorial ) throws ErrorService {
        

        Editorial editorial = editorialRepositorio.getOne(ideditorial);
        
        Autor autor = autorRepositorio.getOne(idautor);

        validar(isbn, titulo, anio, ejemplares, prestados, autor, editorial);

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setPrestados(prestados);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    @Transactional
    public void modificarLibro(String isbn, String titulo, int anio ,int ejemplares,Integer prestados ,String idautor, String ideditorial) throws ErrorService {

        Editorial editorial = editorialRepositorio.getOne(ideditorial);
        
        Autor autor = autorRepositorio.getOne(idautor); 
        
        validar(isbn, titulo, anio, ejemplares, prestados, autor, editorial);
        
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

           

                libro.setTitulo(titulo);
                libro.setAnio(anio);
                libro.setEjemplares(ejemplares);
                libro.setPrestados(prestados);
                libro.setAutor(autor);
                libro.setEditorial(editorial);

                libroRepositorio.save(libro);

           

        } else {
            throw new ErrorService("No existe una editorial con el identificador solicitado");
        }

    }

    @Transactional
	public Libro buscarLibroPorISBN(String isbn) throws ErrorService {
	     
    
		Optional<Libro> respuesta = libroRepositorio.findById(isbn);
		
		if(respuesta.isPresent()) {
			
			Libro libro  = respuesta.get();
			
			return libro;
		}else {
			throw new ErrorService("No existe libro con ese isbn");
		}
		
	}
    @Transactional
    public void eliminarLibro( String isbn) throws ErrorService {

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

            if (libro!=null) {

                libroRepositorio.delete(libro);

            }

        } else {
            throw new ErrorService("No existe una editorial con el identificador solicitado");
        }

    }
    public Date convertirStringADate(String fecha) {

        try {
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
            Date convertido = fechaHora.parse(fecha);
            return convertido;
        } catch (java.text.ParseException ex) {
            Logger.getLogger(LibroService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void validar(String isbn, String titulo, Integer anio, Integer ejemplares, Integer prestados, Autor autor, Editorial editorial) throws ErrorService {

        if (isbn == null) {
            throw new ErrorService("El isbn del libro no debe estar nulo.");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorService("El título del libro no debe estar nulo.");
        }

        if (anio == 0) {
            throw new ErrorService("El año del libro no debe estar nulo.");
        }

        if (ejemplares == 0) {
            throw new ErrorService("La cantidad de libros no puede ser 0.");
        }

        if (prestados > 0) {
            throw new ErrorService("No se puede realizar un prestamos aun.");
        }
        
        if (autor == null) {
            throw new ErrorService("No existe un autor con el autor solicitado");
        }
        
        if (editorial == null) {
            throw new ErrorService("No existe una editorial con el nombre solicitado");
        }
    }
}
