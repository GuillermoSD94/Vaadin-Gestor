package com.TAP.vaadin_gestor;

import java.util.ArrayList;
import java.util.List;

public class Balance {
	
	private static Balance singleton;
	private double valor;
	private String divisa;
	private ConversorDivisa conversor;
	private List<Transaccion> transacciones;
	
	public Balance(int valor) {
		this.valor = valor;
		divisa = "euros";
		conversor = new ConversorEuro();
		transacciones = new ArrayList<>();
	}
	
	public static Balance getInstance() {
		
		if(singleton == null)
			singleton = new Balance(0);
		
		return singleton;
	}
	
	public void addTransaccion(Transaccion t) {
		transacciones.add(t);
	}
	
	public void deleteTransaccion(Transaccion t) {
		transacciones.remove(t);
	}
	
	public List<Transaccion> getTransacciones() {
		return transacciones;
	}
	
	public void setConversor(ConversorDivisa conversor) {
		this.conversor = conversor;
	}
	
	public double divisa(double valor) {
		valor = this.conversor.valor(valor, divisa);
		divisa = this.conversor.divisa(divisa);
		return valor;
	}

	public void setValor(double valor) {
		this.valor = Math.round(valor * 100d) / 100d;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	
	public String getDivisa() {
		return divisa;
	}
	
}
