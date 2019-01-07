package com.TAP.vaadin_gestor;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import org.junit.Test;

public class GestorTest {
	
	private Inventario inventarioTest = Inventario.getInstance();
	private List<Producto> productoTest = inventarioTest.getProductos();
	private Balance balanceTest = Balance.getInstance();
	private List<Transaccion> transaccionTest = balanceTest.getTransacciones();
	
	@Test
	public void testAniadir() {
		Date ahora = new Date();
		Producto p = new Producto("MesaMadera", 2, 40.5);
		Transaccion t = new Transaccion(ahora, "Gasto", p.getPrecio() * p.getCantidad(), balanceTest.getDivisa());
		inventarioTest.addProducto(p);
		balanceTest.addTransaccion(t);
		assertEquals(productoTest.get(0).getTodo(), "MesaMadera240.5");
		assertEquals(transaccionTest.get(0).getTodo(), ahora + "Gasto81.0euros");
	}
	
	@Test
	public void testModificar() {
		Producto p = new Producto("MesaCaoba", 1, 81.0);
		productoTest.get(0).setNombre(p.getNombre());
		productoTest.get(0).setCantidad(p.getCantidad());
		productoTest.get(0).setPrecio(p.getPrecio());
		assertEquals(productoTest.get(0).getTodo(), "MesaCaoba181.0");
	}
	
	@Test
	public void testEliminar() {
		Date ahora = new Date();
		Transaccion t = new Transaccion(ahora, "Ingreso",
				productoTest.get(0).getPrecio() * productoTest.get(0).getCantidad(), balanceTest.getDivisa());
		balanceTest.addTransaccion(t);
		inventarioTest.deleteProducto(productoTest.get(0));
		assertEquals(productoTest.size(), 0);
		assertEquals(transaccionTest.get(1).getTodo(), ahora + "Ingreso81.0euros");
	}
	
}
