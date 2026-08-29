package com.lab.apis.model;

public class Ej10Pedido {

	private Long id;
	private String cliente;
	private String producto;
	private Integer cantidad;
	private Double total;
	private String estado;

	public Ej10Pedido() {
	}

	public Ej10Pedido(Long id, String cliente, String producto, Integer cantidad, Double total, String estado) {
		this.id = id;
		this.cliente = cliente;
		this.producto = producto;
		this.cantidad = cantidad;
		this.total = total;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}