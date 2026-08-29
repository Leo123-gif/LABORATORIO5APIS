package com.lab.apis.model;

public class Ej07Vehiculo {

	private Long id;
	private String marca;
	private String modelo;
	private Integer anio;
	private Double precio;

	public Ej07Vehiculo() {
	}

	public Ej07Vehiculo(Long id, String marca, String modelo, Integer anio, Double precio) {
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.anio = anio;
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}