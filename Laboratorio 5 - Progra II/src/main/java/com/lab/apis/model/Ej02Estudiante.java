package com.lab.apis.model;

public class Ej02Estudiante {

	private Long id;
	private String nombre;
	private String apellido;
	private String carrera;
	private Integer edad;

	public Ej02Estudiante() {
	}

	public Ej02Estudiante(Long id, String nombre, String apellido, String carrera, Integer edad) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.carrera = carrera;
		this.edad = edad;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}
}