package edu.egg.controladores;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.entidades.Autor;
import edu.egg.entidades.Cliente;
import edu.egg.entidades.Editorial;
import edu.egg.entidades.Libro;
import edu.egg.entidades.Prestamo;
import edu.egg.error.ErrorService;
import edu.egg.repositorios.AutorRepositorio;
import edu.egg.repositorios.ClienteRepositorio;
import edu.egg.repositorios.EditorialRepositorio;
import edu.egg.repositorios.LibroRepositorio;
import edu.egg.repositorios.PrestamoRepositorio;
import edu.egg.service.AutorService;
import edu.egg.service.ClienteService;
import edu.egg.service.EditorialService;
import edu.egg.service.LibroService;
import edu.egg.service.PrestamoService;

@Controller
@RequestMapping("/")

public class PortalControlador {
@Autowired
private ClienteService clienteService;
@Autowired
private AutorService autorService;
@Autowired
private LibroService libroService;
@Autowired
private EditorialService editorialService;
@Autowired
private AutorRepositorio autorRepositorio;
@Autowired
private EditorialRepositorio editorialRepositorio;
@Autowired
private ClienteRepositorio clienteRepositorio;
@Autowired
private LibroRepositorio libroRepositorio;
@Autowired
private PrestamoService prestamoService;
@Autowired
private  PrestamoRepositorio prestamoRepositorio;

@GetMapping("/")
public String principio() {
	return "index.html";
}

@GetMapping("/registro")
public String registro() {
	return "registro.html";
}
@GetMapping("/about")
public String about() {
	return "about.html";
}
@GetMapping("/perfil")
public String perfil() {
	return "perfil.html";
}
@GetMapping("/crear-autor")
public String crearAutor() {
	return "crear-autor.html";
}
@GetMapping("/crear-editorial")
public String crearEditorial() {
	return "crear-editorial.html";
}
@GetMapping("/crear-libro")
public String crearLibro(ModelMap modelo) {
	
	List<Autor> autor= autorRepositorio.findAll();
	List<Editorial> editorial= editorialRepositorio.findAll();
	modelo.put("autor",autor);
	modelo.put("editorial",editorial);
	
	return "crear-libro.html";
}

@GetMapping("/listar-libros")
public String listarLibro(ModelMap modelo) {	
	List<Libro> libro= libroRepositorio.findAll();	
	modelo.put("libro",libro);

	return "listar-libros.html";
}

@GetMapping("/editar-autores")
public String editarAutores(ModelMap modelo) {	
	
	List<Autor> autor= autorRepositorio.findAll();	
	modelo.put("autor",autor);
	
	return "editar-autores.html";
}
@GetMapping("/listar-autores")
public String listarAutores(ModelMap modelo) {	
	
	List<Autor> autor= autorRepositorio.findAll();	
	modelo.put("autor",autor);
	
	return "listar-autores.html";
}

@GetMapping("/listar-prestamo")
public String listarprestamo(ModelMap modelo) {		
	List<Prestamo> prestamo= prestamoRepositorio.findAll();	
	modelo.put("prestamo",prestamo);
	
	return "listar-prestamo.html";
}
@GetMapping("/listar-editorial")
public String listarEditoriales(ModelMap modelo) {		
	List<Editorial> editorial= editorialRepositorio.findAll();	
	modelo.put("editorial",editorial);
	
	return "listar-editorial.html";
}


@GetMapping("/index")
public String inicio() {
	return "index.html";
}
@GetMapping("/login")
public String login(@RequestParam(required= false) String error,ModelMap model) {	
	if(error != null ){
		model.put("error", "Dni o clave son incorrectos");
	}
	return "login.html";
}

@GetMapping("/modificarLibro")
public String modificarLibro(@RequestParam String isbn ,ModelMap modelo) throws ErrorService {
	
	
	Libro libro = libroService.buscarLibroPorISBN(isbn);
	modelo.put("libro",libro);

	List<Autor> autores = autorRepositorio.findAll();
	
	List<Editorial> editoriales = editorialRepositorio.findAll();
	
	modelo.put("autor", autores);
	modelo.put("editorial", editoriales);
	
	return "modificarLibro.html";
}
@GetMapping("/modificarUsuario")
public String modificarUsuario(@RequestParam String documento ,ModelMap modelo) throws ErrorService {
	
	
	Cliente cliente = clienteService.buscarClientePorDocumento(Long.parseLong(documento));
	modelo.addAttribute("cliente",cliente);
    
	return "modificarUsuario.html";
}

@GetMapping("/alquilarLibro")
public String alquilarLibro(@RequestParam String isbn ,ModelMap modelo) throws ErrorService {
	
	
	Libro libro = libroService.buscarLibroPorISBN(isbn);
	modelo.put("libro",libro);
	
	return "alquilarLibro.html";
}
@PostMapping("/registro")
public String crearCliente(ModelMap modelo, MultipartFile archivo,@RequestParam String documento,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String domicilio,@RequestParam String telefono, @RequestParam String clave) {
	
	try {
		clienteService.crearCliente(archivo,Long.parseLong(documento), nombre, apellido, domicilio, telefono, clave);
	} catch (ErrorService e) {
		
		modelo.put("error", e.getMessage());
		modelo.put("documento", documento);
		modelo.put("nombre", nombre);
		modelo.put("apellido", apellido);
		modelo.put("domicilio", domicilio);
		modelo.put("telefono", telefono);
		modelo.put("clave", clave);
		

		return "registro.html";
	}
	modelo.put("Titulo", "Bienvenidos");
	modelo.put("Descipcion", "Esta es una pagina gratisss");
	
	return "perfil.html";
	
}
@PostMapping("/crear-autor")
public String registrarAutor(ModelMap modelo,@RequestParam String nombre) {
	
	try {
		autorService.registrarAutor(nombre);
	} catch (ErrorService e) {
		modelo.put("error", e.getMessage());
		modelo.put("nombre", nombre);
	
		return "crear-autor.html";
	}
	return "perfil.html";
}

@PostMapping("/crear-editorial")
public String registrarEditorial(ModelMap modelo,@RequestParam String nombre) {
	
	try {
		editorialService.registrarEditorial(nombre);
	} catch (ErrorService e) {
		modelo.put("error", e.getMessage());
		modelo.put("nombre", nombre);
	
		return "crear-editorial.html";
	}
	return "perfil.html";
}


@PostMapping("/crear-libro")
public String agregarLibro(ModelMap modelo, @RequestParam String isbn, @RequestParam String titulo, @RequestParam String anio, @RequestParam String ejemplares,@RequestParam String prestados, @RequestParam String autor, @RequestParam String editorial) {

    try {
        libroService.agregarLibro(isbn, titulo, Integer.valueOf(anio) ,Integer.valueOf(ejemplares), Integer.valueOf(prestados), autor, editorial);
    } catch (ErrorService e) {

     List<Autor> autorr = autorRepositorio.findAll();
        List<Editorial> editoriall = editorialRepositorio.findAll();
        modelo.put("autor", autorr);
        modelo.put("editorial", editoriall);
        modelo.put("error", e.getMessage());
        modelo.put("isbn", isbn);
        modelo.put("titulo", titulo);
        modelo.put("anio", anio);
        modelo.put("ejemplares", ejemplares);
        modelo.put("prestados", prestados);
        return "crear-libro.html";
    }
	modelo.put("Titulo", "Bienvenidos");
	modelo.put("Descipcion", "Esta es una pagina gratisss");
	
	return "perfil.html";
	
}

@PostMapping("/modificarLibro")
public String modificar(ModelMap modelo,
		
		@RequestParam String isbn,
		@RequestParam String titulo,
		@RequestParam String anio,
		@RequestParam String ejemplares,
		@RequestParam String autor,
		@RequestParam String editorial,
		@RequestParam String prestados
		) throws ErrorService {
	
			Libro libro = null;
	
	try {
	
		libro = libroService.buscarLibroPorISBN(isbn);
		
		libroService.modificarLibro(isbn, titulo,Integer.valueOf(anio), Integer.valueOf(ejemplares),Integer.valueOf(prestados),autor,editorial);
		
		
	
	}catch(Exception e) {
	
		List<Autor> autores = autorRepositorio.findAll();
		
		List<Editorial> editoriales = editorialRepositorio.findAll();
		
		modelo.put("editorial", editoriales);
		modelo.put("autor", autores);
		modelo.put("error",e.getMessage());
		modelo.put("isbn", isbn);
		modelo.put("titulo", titulo);
		modelo.put("anio", anio);
		modelo.put("ejemplares", ejemplares);
		modelo.put("prestados", prestados);
		modelo.put("libro", libro);
		
		return "modificarLibro.html";
	}
	

	
	return "perfil.html";
}

@PostMapping("/eliminar-libro")
public String eliminarLibro(ModelMap modelo,
		HttpSession session,
		@RequestParam String isbn
		) {
	
	Libro libro = null;
	
	try {
		
		 libro = libroService.buscarLibroPorISBN(isbn);
		libroService.eliminarLibro(isbn);

		//session.setAttribute("autorsession", autor);
		
		
	}catch(ErrorService e) {
		modelo.addAttribute("error", e.getMessage());
		return "listar-libros.html";
	}
	return "perfil.html";
	
}

@PostMapping("/devolverLibro")
public String devolverLibro(ModelMap modelo,
		@RequestParam String isbn,
		@RequestParam String documento,
		@RequestParam String id) {
	
	Libro libro = null;
	
	try {
		
		
		libro = libroService.buscarLibroPorISBN(isbn);
		prestamoService.devolucion(isbn,Long.parseLong(documento),id);;


		
	}catch(ErrorService e) {
		modelo.addAttribute("error", e.getMessage());
		
		return "listar-prestamo.html";
	}
	return "perfil.html";
	
}



@PostMapping("/alquilarLibro")
public String alquilarLibro(ModelMap modelo,
		
		@RequestParam String isbn,
		@RequestParam String documento,
		@RequestParam String devolucion
		) throws ErrorService {
	
			Libro libro = null;
	Date w1=  libroService.convertirStringADate(devolucion);
	try {
	
		clienteService.buscarClientePorDocumento(Long.parseLong(documento));
		
     prestamoService.agregarPrestamo(isbn, Long.parseLong(documento),w1);		
		
	
	}catch(Exception e) {

		
		
		modelo.put("error",e.getMessage());
		modelo.put("isbn", isbn);
		modelo.put("devolucion",devolucion);
		modelo.put("documento", documento);		
		modelo.put("libro", libro);
		
		return "alquilarLibro.html";
	}
	

	
	return "perfil.html";
}
@PostMapping("/modificarUsuario")
public String registro(ModelMap modelo, HttpSession session,
		
		@RequestParam String documento,
		@RequestParam String nombre,
		@RequestParam String apellido,
		@RequestParam String domicilio,
		@RequestParam String telefono,
		@RequestParam String clave,
		 MultipartFile foto
		) throws ErrorService {
	
	
	try {
		
		Cliente cliente = clienteService.buscarClientePorDocumento(Long.parseLong(documento));
		clienteService.modificarCliente(Long.parseLong(documento), nombre, apellido, domicilio, telefono, clave);
		
		modelo.put("cliente", cliente);
		session.setAttribute("clientesession", cliente);

	}catch(ErrorService e) {
		Cliente cliente = clienteService.buscarClientePorDocumento(Long.parseLong(documento));
		modelo.addAttribute("cliente",cliente);
		
		modelo.put("error",e.getMessage());
		modelo.put("documento",documento);
		modelo.put("nombre", nombre);
		modelo.put("apellido", apellido);
		modelo.put("domicilio", domicilio);
		modelo.put("telefono", telefono);
		modelo.put("clave", clave);
		modelo.put("foto", foto);
		
		
		
		
		return "modificarUsuario.html";
	}
	return "perfil.html";
}
}

