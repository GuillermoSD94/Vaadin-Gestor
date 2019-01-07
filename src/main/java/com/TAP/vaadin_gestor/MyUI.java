package com.TAP.vaadin_gestor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("Gestor de inventario")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;
	private Producto productoSeleccionado;
	private int inventario = 0;
	private int tamanio = 0;
	private double balanceAux;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	// Inicializaciones
    	Grid<Producto> grid = new Grid<Producto>();
    	Balance balance = new Balance(0);
    	
    	// Conversores
    	ConversorEuro conversorEuro = new ConversorEuro();
    	ConversorDolar conversorDolar = new ConversorDolar();
    	ConversorLibra conversorLibra = new ConversorLibra();
    	ConversorYen conversorYen = new ConversorYen();
    	
    	// Forms
    	FormLayout formLayout = new FormLayout();
    	FormLayout formLayout2 = new FormLayout();
    	FormLayout formLayout3 = new FormLayout();
    	
    	// Ventanas
    	Window subWindow = new Window("Detalles del producto");
    	Window subWindowDivisa = new Window("Cambio de divisa");
    	Window subWindowTransaccion = new Window("Transacciones realizadas");
    	
    	// Layouts horizontales
    	HorizontalLayout horizontalLayout = new HorizontalLayout();
    	HorizontalLayout horizontalLayoutWindow = new HorizontalLayout();
    	HorizontalLayout horizontalLayoutWindowDivisa = new HorizontalLayout();
    	HorizontalLayout horizontalLayoutWindowTransaccion = new HorizontalLayout();
    	
    	// Layouts verticales
        VerticalLayout subContent1 = new VerticalLayout();
        VerticalLayout subContent2 = new VerticalLayout();
        VerticalLayout subContent3 = new VerticalLayout();
        VerticalLayout subContent4 = new VerticalLayout();
        VerticalLayout subContentDivisa1 = new VerticalLayout();
        VerticalLayout subContentDivisa2 = new VerticalLayout();
        VerticalLayout subContentTransaccion1 = new VerticalLayout();
        VerticalLayout subContentTransaccion2 = new VerticalLayout();
        VerticalLayout subContentTransaccion3 = new VerticalLayout();
        VerticalLayout subContentTransaccion4 = new VerticalLayout();
        
        // Labels
        Label labelNombre = new Label();
        Label labelCantidad = new Label();
        Label labelPrecio = new Label();
    	Label labelInventario = new Label();
        Label labelBalance = new Label();
        Label labelDivisa = new Label();
        Label labelTransaccion = new Label();
        Label labelTransaccionFecha = new Label();
        Label labelTransaccionTransaccion = new Label();
        Label labelTransaccionValor = new Label();
    	Label labelTransaccionDivisa = new Label();
    	
        // TextFields
    	TextField textFieldNombre = new TextField("Nombre");
    	TextField textFieldCantidad = new TextField("Cantidad");
    	TextField textFieldPrecio = new TextField("Precio");
        TextField textFieldNombreVentana = new TextField("Nombre");
    	TextField textFieldCantidadVentana = new TextField("Cantidad");
    	TextField textFieldPrecioVentana = new TextField("Precio");
    	TextField textFieldCantidadCVVentana = new TextField("Cantidad para comprar/vender");
    	TextField textFieldIngGas = new TextField();
    	
    	// Botones
    	Button buttonAdd = new Button("Comprar un nuevo producto");
        Button buttonDelete = new Button("Vender todo y eliminar");
        Button buttonComprar = new Button("Comprar");
        Button buttonVender = new Button("Vender");
        Button buttonModifyNombre = new Button("Cambiar nombre al producto");
        Button buttonModifyCantidad = new Button("Modificar cantidad (no afecta al balance)");
        Button buttonModifyPrecio = new Button("Fijar precio del producto");
        Button buttonDivisa = new Button("Cambiar divisa");
        Button buttonEuro = new Button("Euro");
        Button buttonDolar = new Button("Dólar");
        Button buttonLibra = new Button("Libra");
        Button buttonYen = new Button("Yen");
    	Button buttonIngreso = new Button("Realizar ingreso");
    	Button buttonGasto = new Button("Realizar gasto");
    	Button buttonTransaccion = new Button("Ver transacciones");
        
    	
        //////////////////// VENTANA DETALLE ////////////////////
        
        buttonDelete.addClickListener(e -> {
        	Inventario.getInstance().deleteProducto(productoSeleccionado);
        	Date ahora = new Date();
    		Transaccion t = new Transaccion(ahora, "Ingreso",
    				productoSeleccionado.getPrecio() * productoSeleccionado.getCantidad(), balance.getDivisa());
    		Balance.getInstance().addTransaccion(t);
        	inventario -= productoSeleccionado.getCantidad();
    		balanceAux = balance.getValor() + productoSeleccionado.getPrecio() * productoSeleccionado.getCantidad();
    		balance.setValor(balanceAux);
    		labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
        	grid.setItems(Inventario.getInstance().getProductos());
        	removeWindow(subWindow);
        });
    	
        buttonComprar.addClickListener(e -> {
        	Producto p = new Producto(Integer.parseInt(textFieldCantidadCVVentana.getValue()));
        	Date ahora = new Date();
    		Transaccion t = new Transaccion(ahora, "Gasto",
    				productoSeleccionado.getPrecio() * p.getCantidad(), balance.getDivisa());
    		Balance.getInstance().addTransaccion(t);
        	inventario += p.getCantidad();
    		balanceAux = balance.getValor() - productoSeleccionado.getPrecio() * p.getCantidad();
    		balance.setValor(balanceAux);
    		productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() + p.getCantidad());
    		labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldCantidadCVVentana.clear();
    		removeWindow(subWindow);
        });
        
        buttonVender.addClickListener(e -> {
        	Producto p = new Producto(Integer.parseInt(textFieldCantidadCVVentana.getValue()));
        	Date ahora = new Date();
    		Transaccion t = new Transaccion(ahora, "Ingreso",
    				productoSeleccionado.getPrecio() * p.getCantidad(), balance.getDivisa());
    		Balance.getInstance().addTransaccion(t);
        	inventario -= p.getCantidad();
    		balanceAux = balance.getValor() + productoSeleccionado.getPrecio() * p.getCantidad();
    		balance.setValor(balanceAux);
    		productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() - p.getCantidad());
    		labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldCantidadCVVentana.clear();
    		removeWindow(subWindow);
        });
        
        buttonModifyNombre.addClickListener(e -> {
        	Producto p = new Producto(textFieldNombreVentana.getValue());
    		productoSeleccionado.setNombre(p.getNombre());
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldNombreVentana.clear();
    		removeWindow(subWindow);
        });
        
        buttonModifyCantidad.addClickListener(e -> {
        	Producto p = new Producto(Integer.parseInt(textFieldCantidadVentana.getValue()));
        	inventario -= productoSeleccionado.getCantidad() - p.getCantidad();
        	labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
    		productoSeleccionado.setCantidad(p.getCantidad());
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldCantidadVentana.clear();
    		removeWindow(subWindow);
        });
        
        buttonModifyPrecio.addClickListener(e -> {
        	Producto p = new Producto(Double.parseDouble(textFieldPrecioVentana.getValue()));
    		productoSeleccionado.setPrecio(p.getPrecio());
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldPrecioVentana.clear();
    		removeWindow(subWindow);
        });
        
      
        subContent1.addComponents(new Label("Nombre:"), labelNombre, new Label("Cantidad:"),
        		labelCantidad, new Label("Precio:"), labelPrecio);
        
        subContent2.addComponents(textFieldNombreVentana, textFieldCantidadVentana, textFieldPrecioVentana);
        
        subContent3.addComponents(buttonModifyNombre, new Label(), buttonModifyCantidad,
        		new Label(), buttonModifyPrecio);
        
        subContent4.addComponents(textFieldCantidadCVVentana, buttonComprar, buttonVender, buttonDelete);
        
        horizontalLayoutWindow.addComponents(subContent1, subContent2, subContent3, subContent4);     
        
        subWindow.center();
        subWindow.setContent(horizontalLayoutWindow);
        
        
        //////////////////// VENTANA DIVISA ////////////////////
        
        buttonEuro.addClickListener(e -> {
    		balance.setConversor(conversorEuro);
    		balanceAux = balance.divisa(balance.getValor());
    		balance.setValor(balanceAux);
    		labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
        	labelDivisa.setValue("Divisa actual: euros");
        	removeWindow(subWindowDivisa);
        });
        
        buttonDolar.addClickListener(e -> {
    		balance.setConversor(conversorDolar);
    		balanceAux = balance.divisa(balance.getValor());
    		balance.setValor(balanceAux);
    		labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
        	labelDivisa.setValue("Divisa actual: dólares");
        	removeWindow(subWindowDivisa);
        });
        
        buttonLibra.addClickListener(e -> {
    		balance.setConversor(conversorLibra);
    		balanceAux = balance.divisa(balance.getValor());
    		balance.setValor(balanceAux);
    		labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
        	labelDivisa.setValue("Divisa actual: libras");
        	removeWindow(subWindowDivisa);
        });
        
        buttonYen.addClickListener(e -> {
    		balance.setConversor(conversorYen);
    		balanceAux = balance.divisa(balance.getValor());
    		balance.setValor(balanceAux);
    		labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
        	labelDivisa.setValue("Divisa actual: yenes");
        	removeWindow(subWindowDivisa);
        });
        
        
        subContentDivisa1.addComponents(buttonEuro, buttonLibra);
        
        subContentDivisa2.addComponents(buttonDolar, buttonYen);
        
        horizontalLayoutWindowDivisa.addComponents(subContentDivisa1, subContentDivisa2);
        
        subWindowDivisa.center();
        subWindowDivisa.setContent(horizontalLayoutWindowDivisa);
        
        
        //////////////////// VENTANA TRANSACCION ////////////////////
        
    	labelTransaccionFecha.setWidth("150px");
		labelTransaccionTransaccion.setWidth("10px");
		labelTransaccionValor.setWidth("10px");
		labelTransaccionDivisa.setWidth("50px");
    	
        subContentTransaccion1.addComponents(labelTransaccionFecha);
        
        subContentTransaccion2.addComponents(labelTransaccionTransaccion);
        
        subContentTransaccion3.addComponents(labelTransaccionValor);
        
        subContentTransaccion4.addComponents(labelTransaccionDivisa);
        
        horizontalLayoutWindowTransaccion.addComponents(subContentTransaccion1, subContentTransaccion2,
        		subContentTransaccion3, subContentTransaccion4);
        
        subWindowTransaccion.center();
        subWindowTransaccion.setContent(horizontalLayoutWindowTransaccion);
        
        
        //////////////////// TABLA ////////////////////
    	
    	grid.addColumn(Producto::getNombre).setCaption("Nombre");
    	grid.addColumn(Producto::getCantidad).setCaption("Cantidad");
    	grid.addColumn(Producto::getPrecio).setCaption("Precio");
    	grid.setSelectionMode(SelectionMode.SINGLE);
    	
    	grid.addItemClickListener(event -> {
    		productoSeleccionado = event.getItem();
        	labelNombre.setValue(productoSeleccionado.getNombre());
        	labelCantidad.setValue(Integer.toString(productoSeleccionado.getCantidad()));
        	labelPrecio.setValue(Double.toString(productoSeleccionado.getPrecio()));
        	removeWindow(subWindow);
        	addWindow(subWindow);
    	});
    	
    	
    	//////////////////// FORM ////////////////////
    	
    	buttonAdd.addClickListener(e -> {
    		Date ahora = new Date();
    		Producto p = new Producto(textFieldNombre.getValue(), Integer.parseInt(textFieldCantidad.getValue()),
    				Double.parseDouble(textFieldPrecio.getValue()));
    		Inventario.getInstance().addProducto(p);
    		Transaccion t = new Transaccion(ahora, "Gasto", p.getPrecio() * p.getCantidad(), balance.getDivisa());
    		Balance.getInstance().addTransaccion(t);
    		inventario += p.getCantidad();
    		balanceAux = balance.getValor() - p.getPrecio() * p.getCantidad();
    		balance.setValor(balanceAux);
    		labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		textFieldNombre.clear();
    		textFieldCantidad.clear();
    		textFieldPrecio.clear();
    		grid.setItems(Inventario.getInstance().getProductos());
    	});
    	
    	formLayout.addComponents(textFieldNombre, textFieldCantidad, textFieldPrecio, buttonAdd);
    	
    	
    	//////////////////// FORM 2 ////////////////////
		
        labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
    	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    	labelDivisa.setValue("Divisa actual: euros");
    	
    	buttonDivisa.addClickListener(e -> {
        	removeWindow(subWindowDivisa);
        	addWindow(subWindowDivisa);
    	});
    	
    	formLayout2.addComponents(labelInventario, labelBalance, labelDivisa, buttonDivisa);
    	
    	
    	//////////////////// FORM 3 ////////////////////
    	
    	labelTransaccion.setValue("Introduzca transacción:");
    	
    	buttonIngreso.addClickListener(e -> {
    		Producto p = new Producto(Double.parseDouble(textFieldIngGas.getValue()));
    		Date ahora = new Date();
    		Transaccion t = new Transaccion(ahora, "Ingreso", p.getPrecio(), balance.getDivisa());
    		Balance.getInstance().addTransaccion(t);
    		balanceAux = balance.getValor() + p.getPrecio();
    		balance.setValor(balanceAux);
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		textFieldIngGas.clear();
    	});
    	
    	buttonGasto.addClickListener(e -> {
    		Producto p = new Producto(Double.parseDouble(textFieldIngGas.getValue()));
    		Date ahora = new Date();
    		Transaccion t = new Transaccion(ahora, "Gasto", p.getPrecio(), balance.getDivisa());
    		Balance.getInstance().addTransaccion(t);
    		balanceAux = balance.getValor() - p.getPrecio();
    		balance.setValor(balanceAux);
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		textFieldIngGas.clear();
    	});
    	
    	buttonTransaccion.addClickListener(e -> {
    		for (int i = tamanio; i != Balance.getInstance().getTransacciones().size(); i++) {
    			SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss ");
    			labelTransaccionFecha.setValue(labelTransaccionFecha.getValue() +
    					formateador.format(Balance.getInstance().getTransacciones().get(i).getFecha()));
    			labelTransaccionTransaccion.setValue(labelTransaccionTransaccion.getValue() +
    					Balance.getInstance().getTransacciones().get(i).getTransaccion() + " ");
    			labelTransaccionValor.setValue(labelTransaccionValor.getValue() +
    					Balance.getInstance().getTransacciones().get(i).getValor() + " ");
    			labelTransaccionDivisa.setValue(labelTransaccionDivisa.getValue() +
    					Balance.getInstance().getTransacciones().get(i).getDivisa() + " ");
    			tamanio = i+1;
        	}
    		removeWindow(subWindowTransaccion);
        	addWindow(subWindowTransaccion);
    	});
    	
    	formLayout3.addComponents(labelTransaccion, textFieldIngGas, buttonIngreso, buttonGasto, buttonTransaccion);
    
    	horizontalLayout.addComponents(grid, formLayout, formLayout2, formLayout3);
    	
    	setContent(horizontalLayout);
    	
    }
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
}
