package edu.egg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.entidades.Cliente;
import edu.egg.entidades.Foto;
import edu.egg.error.ErrorService;
import edu.egg.repositorios.ClienteRepositorio;

@Service
public class ClienteService implements UserDetailsService {

	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private FotoService fotoService;

	@Transactional
	public void crearCliente(MultipartFile archivo,long documento, String nombre, String apellido, String domicilio, String telefono,
		String clave) throws ErrorService {
      
		Optional<Cliente> respuesta = clienteRepositorio.findById(documento);
		
	if (respuesta.isPresent()) {
		throw new ErrorService("Ya se encontro un usuario con el mismo dni");
	}else {
		Cliente cliente = new Cliente();

		validar(documento, nombre, apellido, domicilio, telefono, clave);

		cliente.setDocumento(documento);
		cliente.setNombre(nombre);
		cliente.setApellido(apellido);
		cliente.setDomicilio(domicilio);
		cliente.setTelefono(telefono);
		String encriptada = new BCryptPasswordEncoder().encode(clave);
		cliente.setClave(encriptada);
        Foto foto= fotoService.guardar(archivo);
		cliente.setFoto(foto);
        clienteRepositorio.save(cliente);}
        
	}
	@Transactional
	public Cliente buscarClientePorDocumento(Long documento) throws ErrorService {
	
		Optional<Cliente> respuesta = clienteRepositorio.findById(documento);
		
		if(respuesta.isPresent()) {
			
			Cliente cliente = respuesta.get();
			
			return cliente;
		}else {
			throw new ErrorService("No se encontro cliente con ese documento");
		}
	}

	@Transactional
	public void modificarCliente(long documento, String nombre, String apellido, String domicilio, String telefono,
			String clave) throws ErrorService {

		validar(documento, nombre, apellido, domicilio, telefono, clave);

		Optional<Cliente> respuesta = clienteRepositorio.findById(documento);

		if (respuesta.isPresent()) {
          
			Cliente cliente = respuesta.get();
			cliente.setDocumento(documento);
			cliente.setNombre(nombre);
			cliente.setApellido(apellido);
			cliente.setDomicilio(domicilio);
			cliente.setTelefono(telefono);
			cliente.setClave(clave);

			clienteRepositorio.save(cliente);
           
		} else {
			throw new ErrorService("No se encontro el cliente solicitado");
		}

	}

	@Transactional
	public void eliminarCliente(long documento) throws ErrorService {

		Optional<Cliente> respuesta = clienteRepositorio.findById(documento);

		if (respuesta.isPresent()) {

			Cliente cliente = respuesta.get();

			clienteRepositorio.delete(cliente);

		} else {
			throw new ErrorService("No se encontro el cliente solicitado");
		}

	}

	public void validar(long documento, String nombre, String apellido, String domicilio, String telefono, String clave)
			throws ErrorService {

		if (documento == 0) {
			throw new ErrorService("El documento del cliente no puede ser nulo");
		}

		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorService("El nombre del cliente no puede ser nulo.");
		}

		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorService("El apellido del cliente no puede ser nulo.");
		}

		if (domicilio == null || domicilio.isEmpty()) {
			throw new ErrorService("El domicilio del cliente no puede ser nulo.");
		}

		if (telefono == null || telefono.isEmpty()) {
			throw new ErrorService("El telefono del cliente no puede ser nulo.");
		}
		if (clave == null || clave.isEmpty() || clave.length() < 8) {
			throw new ErrorService("La clave no puede estar vacia o ser menor a 8 digitos.");
		}

	}

	@Override
	public UserDetails loadUserByUsername(String documento){
		
		Optional <Cliente> respuesta= clienteRepositorio.buscarClientePorDocumento(Long.parseLong(documento));
		if(respuesta.isPresent()) {
			
		Cliente cliente = respuesta.get();
		
		List <GrantedAuthority> permisos= new ArrayList<>();
		GrantedAuthority p1= new SimpleGrantedAuthority("ROLE_CLIENTE_REGISTRO");
		permisos.add(p1);
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		session.setAttribute("clientesession", cliente);
	    User user= new User(String.valueOf(cliente.getDocumento()),cliente.getClave(), permisos);
		 
		 return user;
		 
		}else {
			System.out.println("NO");
				
		return null;
	}
	}
}