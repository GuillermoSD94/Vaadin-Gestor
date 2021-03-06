package com.TAP.vaadin_gestor;

public class ConversorDolar implements ConversorDivisa{

	@Override
	public double valor(double valor, String divisa) {
		switch(divisa) {
		   case "euros" :
			   return(valor * 1.14);
			  
		   case "dólares" :
			   return(valor);
		   
		   case "libras" :
			   return(valor * 1.27);
			   
		   case "yenes" :
			   return(valor * 0.0092);
		}
		return 0;
	}
	
	@Override
	public String divisa(String divisa) {
		return(divisa = "dólares");
	}
	
}
