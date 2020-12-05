package edu.egg.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Libro {

	
	@Id
	private String isbn;
	private String titulo;
	private Integer anio;
	private Integer ejemplares;
	private Integer prestados;
	
	@ManyToOne
	private Autor autor;
	@ManyToOne
	private Editorial editorial;
	
	public Libro() {
		
		
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	


	

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public Integer getEjemplares() {
		return ejemplares;
	}
	public void setEjemplares(Integer ejemplares) {
		this.ejemplares = ejemplares;
	}
	public Integer getPrestados() {
		return prestados;
	}
	public void setPrestados(Integer prestados) {
		this.prestados = prestados;
	}
	
	
	
}
