package com.TAP.vaadin_gestor;

public class ConversorYen implements ConversorDivisa{

	@Override
	public double valor(double valor, String divisa) {
		switch(divisa) {
		   case "euros" :
			   return(valor * 123.68);
			  
		   case "dólares" :
			   return(valor * 108.53);
		   
		   case "libras" :
			   return(valor * 138.06);
			   
		   case "yenes" :
			   return(valor);
		}
		return 0;
	}
	
	@Override
	public String divisa(String divisa) {
		return(divisa = "yenes");
	}

}
