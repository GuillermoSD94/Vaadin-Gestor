package com.TAP.vaadin_gestor;

public class ConversorEuro implements ConversorDivisa{

	@Override
	public double valor(double valor, String divisa) {
		switch(divisa) {
		   case "euros" :
			   return(valor);
			  
		   case "dólares" :
			   return(valor * 0.88);
		   
		   case "libras" :
			   return(valor * 1.12);
			   
		   case "yenes" :
			   return(valor * 0.0081);
		}
		return 0;
	}
	
	@Override
	public String divisa(String divisa) {
		return(divisa = "euros");
	}

}
