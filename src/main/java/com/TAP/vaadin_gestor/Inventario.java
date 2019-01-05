package com.TAP.vaadin_gestor;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
	
	private static Inventario singleton;
	private List<Producto> productos;
	
	private Inventario() {
		super();
		productos = new ArrayList<>();
	}
	
	public static Inventario getInstance() {
		
		if(singleton == null)
			singleton = new Inventario();
		
		return singleton;
	}
	
	public void addProducto(Producto p) {
		productos.add(p);
	}
	
	public void deleteProducto(Producto p) {
		productos.remove(p);
	}
	
	public List<Producto> getProductos() {
		return productos;
	}
	
}
