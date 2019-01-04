package com.TAP.vaadin_gestor;

public class Producto {

	private String nombre;
	private int cantidad;
	private double precio;
	
	public Producto(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Producto(int cantidad) {
		super();
		this.cantidad = cantidad;
	}
	
	public Producto(double precio) {
		super();
		this.precio = precio;
	}
	
	public Producto(String nombre, int cantidad, double precio) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}
