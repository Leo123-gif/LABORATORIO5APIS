package com.lab.apis.model;

public class Ej06Curso {

	private Long id;
	private String nombre;
	private String descripcion;
	private Integer creditos;
	private String modalidad;

	public Ej06Curso() {
	}

	public Ej06Curso(Long id, String nombre, String descripcion, Integer creditos, String modalidad) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.creditos = creditos;
		this.modalidad = modalidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
}