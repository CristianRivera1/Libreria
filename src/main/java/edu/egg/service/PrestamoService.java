package edu.egg.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.egg.entidades.Cliente;
import edu.egg.entidades.Libro;
import edu.egg.entidades.Prestamo;
import edu.egg.error.ErrorService;
import edu.egg.repositorios.ClienteRepositorio;
import edu.egg.repositorios.LibroRepositorio;
import edu.egg.repositorios.PrestamoRepositorio;
@Service
public class PrestamoService {
	@Autowired
	private LibroRepositorio libroRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private PrestamoRepositorio prestamoRepositorio;
	
	
	@Transactional
	public void agregarPrestamo(String isbn,long documento, Date devolucion) throws ErrorService {
		
		Prestamo prestamo = new Prestamo();
		
		Libro libro = libroRepositorio.findById(isbn).get();
		
		Optional<Libro> respuestalibro = libroRepositorio.findById(isbn);
		
		if(respuestalibro.isPresent()) {
			
			Libro libro1 = respuestalibro.get();
				
				prestamo.setLibro(libro1);
	
		}
		Cliente cliente = clienteRepositorio.findById(documento).get();
		
		//Optional<Cliente> respuestacliente = clienteRepositorio.findById(documento);
		
		//if(respuestalibro.isPresent() && respuestacliente.isPresent()) {
		
		validar(documento);
		
		 if (libro.getEjemplares() > 0) {
		
			 prestamo.setCliente(cliente);
			 prestamo.setDevolucion(devolucion);			 
			 Date date = new Date();
			 prestamo.setFecha(date);
			
		
			 prestamoRepositorio.save(prestamo);
		
           int pres1 = libro.getPrestados() + 1;

           int eje1 = libro.getEjemplares() - 1;
           
           libro.setPrestados(pres1);

           libro.setEjemplares(eje1);
           

           libroRepositorio.save(libro);
        
		 }else {
			 throw new ErrorService("No hay ejemplares para prestar");
		 }
		
		
	}
	
	@Transactional
	public void devolucion(String isbn,long documento,String id) throws ErrorService {
		
		Libro libro = libroRepositorio.findById(isbn).get();
		
		Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
		
		Optional<Cliente> respuesta2 = clienteRepositorio.findById(documento);
		
		if(respuesta.isPresent() && respuesta2.isPresent()) {
			
			Prestamo prestamo = respuesta.get();
			
			if(prestamo.getLibro().getIsbn()==isbn && prestamo.getCliente().getDocumento()==documento) {
				
				
			prestamo.setFechaEntrega(new Date());
			
			int diferencia = (int) ((prestamo.getDevolucion().getTime()-prestamo.getFechaEntrega().getTime()));
			
			double plata= (diferencia*20);
			
			prestamo.setMulta(plata);
			
			prestamoRepositorio.delete(prestamo);
				
			int pres = libro.getPrestados() - 1;

            int eje = libro.getEjemplares() + 1;

            libro.setEjemplares(eje);
            libro.setPrestados(pres);
           
            libroRepositorio.save(libro);
          
			
			}else {
				throw new ErrorService("No tiene permisos suficientes para realizar la operación");
			}
			
		}else {
			throw new ErrorService("No se encontro el prestamo solicitado");
		}
		
	}


	public void validar(Long documento) throws ErrorService {
		
		if(documento == 0 || documento == null) {
			
			throw new ErrorService("El id del prestamo no debe ser nulo");
		}
		
        
	}
}
