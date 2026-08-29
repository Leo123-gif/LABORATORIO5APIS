package com.lab.apis.model;

public class Ej08Tarea {

	private Long id;
	private String titulo;
	private String descripcion;
	private String prioridad;
	private Boolean completada;

	public Ej08Tarea() {
	}

	public Ej08Tarea(Long id, String titulo, String descripcion, String prioridad, Boolean completada) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.completada = completada;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public Boolean getCompletada() {
		return completada;
	}

	public void setCompletada(Boolean completada) {
		this.completada = completada;
	}
}