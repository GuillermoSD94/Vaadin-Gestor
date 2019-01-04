package com.TAP.vaadin_gestor;

import java.util.ArrayList;

public class Balance {
	
	private double valor;

	private ArrayList<ConversorDivisa> conversores;
	
	public Balance(int valor) {
		this.valor = valor;
		conversores = new ArrayList<ConversorDivisa>();
	}

	public void setValor(double valor) {
		this.valor = valor;
		notifyObservers();
	}
	
	public double getValor() {
		return valor;
	}
	
	public void notifyObservers() {
		for(ConversorDivisa o : conversores)
			o.update(this.valor);
	}
	
	public void addObserver(ConversorDivisa o) {
		conversores.add(o);
	}
	
	public void removeObserver(ConversorDivisa o) {
		conversores.remove(o);
	}
	
}
