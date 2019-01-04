package com.TAP.vaadin_gestor;

public class ConversorDolar implements ConversorDivisa{

	private double valor;
	
	public ConversorDolar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(double valor) {
		this.setValor(valor*1.14);
		//System.out.println("El valor de "+ valor +" euros en dólares es: " + new DecimalFormat("#.##").format(valor*1.14));
	}
	
	public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
	
}
