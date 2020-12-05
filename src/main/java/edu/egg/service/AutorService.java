package edu.egg.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import edu.egg.entidades.Autor;
import edu.egg.entidades.Cliente;
import edu.egg.error.ErrorService;
import edu.egg.repositorios.AutorRepositorio;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepositorio autorRepositorio;
	
	@Transactional
	public void registrarAutor(String nombre) throws ErrorService {

		Optional<Autor> respuesta = autorRepositorio.findById(nombre);
		
		
	if (respuesta.isPresent()) {
		throw new ErrorService("Ya hay un autor con el mismo nombre y apellido");
	}else {

		validar(nombre);
		
		Autor autor = new Autor();
		
		autor.setNombre(nombre);
		
		autorRepositorio.save(autor);}
	}
	@Transactional
	public void modificarAutor(String id,String nombre) throws ErrorService{
		
		validar(nombre);
		
		Optional<Autor> respuesta = autorRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
		
		Autor autor = respuesta.get();
		autor.setNombre(nombre);
		
		autorRepositorio.save(autor);
		
		}else {
			throw new ErrorService("No se encontro el autor solicitado");
		}
		
	
	}
	
	@Transactional
	public void eliminarAutor(String id) throws ErrorService {
		
        Optional<Autor> respuesta = autorRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
		
		Autor autor = respuesta.get();
		
		autorRepositorio.delete(autor);
	
		
		}else {
			throw new ErrorService("No se encontro el autor solicitado");
		}
	}
	
	public void validar(String nombre) throws ErrorService{
		
         if(nombre == null || nombre.isEmpty()) {
			
			throw new ErrorService("El nombre del autor no puede ser nulo.");
		}
		
	}
	
}
