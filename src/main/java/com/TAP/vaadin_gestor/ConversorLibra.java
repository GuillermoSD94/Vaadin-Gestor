package com.TAP.vaadin_gestor;

public class ConversorLibra implements ConversorDivisa{

	@Override
	public double valor(double valor, String divisa) {
		switch(divisa) {
		   case "euros" :
			   return(valor * 0.90);
			  
		   case "dólares" :
			   return(valor * 0.79);
		   
		   case "libras" :
			   return(valor);
			   
		   case "yenes" :
			   return(valor * 0.0072);
		}
		return 0;
	}
	
	@Override
	public String divisa(String divisa) {
		return(divisa = "libras");
	}

}
