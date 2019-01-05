package com.TAP.vaadin_gestor;

import java.util.Date;

public class Transaccion {
	
	private Date fecha;
	private String transaccion;
	private double valor;
	private String divisa;
	
	public Transaccion(Date fecha, String transaccion, double valor, String divisa) {
		super();
		this.fecha = fecha;
		this.transaccion = transaccion;
		this.valor = valor;
		this.divisa = divisa;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(String transaccion) {
		this.transaccion = transaccion;
	}
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	
}
