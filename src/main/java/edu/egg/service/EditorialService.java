package edu.egg.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.egg.entidades.Autor;
import edu.egg.entidades.Editorial;
import edu.egg.error.ErrorService;
import edu.egg.repositorios.EditorialRepositorio;

@Service
public class EditorialService {
	@Autowired
	private EditorialRepositorio editorialRepositorio;
	
	
	@Transactional
	public void registrarEditorial(String nombre) throws ErrorService{
		
		Optional<Editorial> respuesta = editorialRepositorio.findById(nombre);
		
		if (respuesta.isPresent()) {
			throw new ErrorService("Ya hay una editorial con el mismo nombre y apellido");
		}else {

			validar(nombre);		
			Editorial editorial = new Editorial();			
			editorial.setNombre(nombre);		
			editorialRepositorio.save(editorial);
			}
		
	}
	@Transactional
    public void modificarEditorial(String id,String nombre) throws ErrorService{
		
		validar(nombre);
		
		Optional<Editorial> respuesta = editorialRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
		
			Editorial editorial = respuesta.get();
			
			editorial.setNombre(nombre);
			
			editorialRepositorio.save(editorial);
		
		
		}else {
			throw new ErrorService("No se encontro la editorial solicitada");
		}
		
	
	}
    
	@Transactional
    public void eliminarEditorial(String id) throws ErrorService {
		
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
		
		Editorial editorial = respuesta.get();
		
		editorialRepositorio.delete(editorial);
		
		//editorial.setId(null);
		//editorial.setNombre(null);
		
		//editorialRepositorio.save(editorial);
		
		}else {
			throw new ErrorService("No se encontro la editorial solicitada");
		}
	}
	
	public void validar(String nombre) throws ErrorService{
		
        if(nombre == null || nombre.isEmpty()) {
			
			throw new ErrorService("El nombre de la editorial no puede ser nulo.");
		}
		
	}
	
	
}
