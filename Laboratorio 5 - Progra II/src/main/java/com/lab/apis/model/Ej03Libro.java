package com.lab.apis.model;

public class Ej03Libro {

	private Long id;
	private String titulo;
	private String autor;
	private String genero;
	private Double precio;

	public Ej03Libro() {
	}

	public Ej03Libro(Long id, String titulo, String autor, String genero, Double precio) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}