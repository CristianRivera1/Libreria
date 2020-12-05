package edu.egg.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.web.multipart.MultipartFile;



@Entity
public class Cliente {

	@Id
	private long documento;
	private String nombre;
	private String apellido;
	private String domicilio;
	private String telefono;
	private String clave;
	
	
	@OneToOne
	private Foto foto;
	
	public Cliente() {
		
	}
	

	


	public Foto getFoto() {
		return foto;
	}


	public void setFoto(Foto foto) {
		this.foto = foto;
	}


	public long getDocumento() {
		return documento;
	}

	public void setDocumento(long documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
}
	
	
