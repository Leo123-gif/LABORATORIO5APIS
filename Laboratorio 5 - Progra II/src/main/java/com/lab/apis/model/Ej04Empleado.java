package com.lab.apis.model;

public class Ej04Empleado {

	private Long id;
	private String nombre;
	private String puesto;
	private Double salario;
	private String departamento;

	public Ej04Empleado() {
	}

	public Ej04Empleado(Long id, String nombre, String puesto, Double salario, String departamento) {
		this.id = id;
		this.nombre = nombre;
		this.puesto = puesto;
		this.salario = salario;
		this.departamento = departamento;
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

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
}