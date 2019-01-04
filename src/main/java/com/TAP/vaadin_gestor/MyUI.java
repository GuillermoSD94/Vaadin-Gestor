package com.TAP.vaadin_gestor;

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
import com.vaadin.ui.Notification;
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

    /*@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        
        setContent(layout);
    }*/

private Producto productoSeleccionado;
private int inventario = 0;
private double balanceAux;
Balance balance = new Balance(0);
ConversorDolar conversorDolar = new ConversorDolar();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
       
    	Grid<Producto> grid = new Grid<Producto>();
    	
    	HorizontalLayout horizontalLayout = new HorizontalLayout();
    	
    	/* VENTANA DETALLE */
    	
    	Window subWindow = new Window("Detalles del producto");
        VerticalLayout subContent1 = new VerticalLayout();
        VerticalLayout subContent2 = new VerticalLayout();
        VerticalLayout subContent3 = new VerticalLayout();
        VerticalLayout subContent4 = new VerticalLayout();
        HorizontalLayout horizontalLayoutWindow = new HorizontalLayout();
        
        Label labelNombre = new Label();
        Label labelCantidad = new Label();
        Label labelPrecio = new Label();
    	Label labelInventario = new Label();
        Label labelBalance = new Label();
        Label labelDivisa = new Label();
        Label labelTransaccion = new Label();
        TextField textFieldNombreVentana = new TextField("Nombre");
    	TextField textFieldCantidadVentana = new TextField("Cantidad");
    	TextField textFieldPrecioVentana = new TextField("Precio");
    	TextField textFieldCantidadCVVentana = new TextField("Cantidad para comprar/vender");
        Button buttonDelete = new Button("Vender todo y eliminar");
        Button buttonComprar = new Button("Comprar");
        Button buttonVender = new Button("Vender");
        Button buttonModifyNombre = new Button("Modificar nombre");
        Button buttonModifyCantidad = new Button("Modificar cantidad (no afecta al balance)");
        Button buttonModifyPrecio = new Button("Fijar precio del producto");
        
        buttonDelete.addClickListener(e -> {
        	Inventario.getInstance().deleteProducto(productoSeleccionado);
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
        	inventario += p.getCantidad();
    		balanceAux = balance.getValor() - productoSeleccionado.getPrecio() * p.getCantidad();
    		balance.setValor(balanceAux);
    		labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() + p.getCantidad());
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldCantidadCVVentana.clear();
    		removeWindow(subWindow);
        });
        
        buttonVender.addClickListener(e -> {
        	Producto p = new Producto(Integer.parseInt(textFieldCantidadCVVentana.getValue()));
        	inventario -= p.getCantidad();
    		balanceAux = balance.getValor() + productoSeleccionado.getPrecio() * p.getCantidad();
    		balance.setValor(balanceAux);
    		labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() - p.getCantidad());
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
        
        
        
        // addWindow(subWindow);
    	
    	/* TABLE */
    	
    	
    	grid.addColumn(Producto::getNombre).setCaption("Nombre");
    	grid.addColumn(Producto::getCantidad).setCaption("Cantidad");
    	grid.addColumn(Producto::getPrecio).setCaption("Precio");
    	grid.setSelectionMode(SelectionMode.SINGLE);
    	
    	grid.addItemClickListener(event -> {
    		
    		productoSeleccionado = event.getItem();
    		
        	// Notification.show("Value: " + event.getItem());
        	labelNombre.setValue(productoSeleccionado.getNombre());
        	labelCantidad.setValue(Integer.toString(productoSeleccionado.getCantidad()));
        	labelPrecio.setValue(Double.toString(productoSeleccionado.getPrecio()));
        	
        	
        	removeWindow(subWindow);
        	addWindow(subWindow);
        	
    	});
    	
    	
    	/* FORM */
    	
    	
    	FormLayout formLayout = new FormLayout();
    	
    	TextField textFieldNombre = new TextField("Nombre");
    	TextField textFieldCantidad = new TextField("Cantidad");
    	TextField textFieldPrecio = new TextField("Precio");
    	Button buttonAdd = new Button("Comprar un nuevo producto");
    			
    	buttonAdd.addClickListener(e -> {
    		
    		Producto p = new Producto(
    				textFieldNombre.getValue(),
    				Integer.parseInt(textFieldCantidad.getValue()),
    				Double.parseDouble(textFieldPrecio.getValue())
    				);
    		
    		Inventario.getInstance().addProducto(p);
    		
    		inventario += p.getCantidad();
    		balanceAux = balance.getValor() - p.getPrecio() * p.getCantidad();
    		balance.setValor(balanceAux);
    		labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		
    		textFieldNombre.clear();
    		textFieldCantidad.clear();
    		textFieldPrecio.clear();
    		
    		grid.setItems(Inventario.getInstance().getProductos());
    		
    		
    		Notification.show("¡Producto añadido! Ya tenemos " + 
    				Inventario.getInstance().getProductos().size(),
    				Notification.TYPE_TRAY_NOTIFICATION);
    		
    	});
    	
    	
    	
    	formLayout.addComponents(
    			textFieldNombre, 
    			textFieldCantidad, 
    			textFieldPrecio, 
    			buttonAdd
    	);
    	
    	
    	/* FORM2 */
    	
    	
    	FormLayout formLayout2 = new FormLayout();
    	
    	
    	Button buttonDivisa = new Button("Cambiar divisa");
		
        
        labelInventario.setValue("Inventario: " + Integer.toString(inventario) + " productos");
    	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    	labelDivisa.setValue("Divisa actual: euros");
    	
    	
    	buttonDivisa.addClickListener(e -> {
    		balance.addObserver(conversorDolar);
    		balanceAux = balance.getValor();
    		balance.setValor(balanceAux);
    		labelBalance.setValue("Balance: " + Double.toString(conversorDolar.getValor()));
        	labelDivisa.setValue("Divisa actual: dólares");
    	});
    	
    	
    	formLayout2.addComponents(
    			labelInventario,
    			labelBalance,
    			labelDivisa,
    			buttonDivisa
    	);
    	
    	
    	/* FORM3 */
    	
    	
    	FormLayout formLayout3 = new FormLayout();
    	
    	
    	TextField textFieldIngGas = new TextField();
    	Button buttonIngreso = new Button("Realizar ingreso");
    	Button buttonGasto = new Button("Realizar gasto");
    	
    	
    	labelTransaccion.setValue("Introduzca transacción:");
    	
    	
    	buttonIngreso.addClickListener(e -> {
    		Producto p = new Producto(Double.parseDouble(textFieldIngGas.getValue()));
    		balanceAux = balance.getValor() + p.getPrecio();
    		balance.setValor(balanceAux);
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		textFieldIngGas.clear();
    	});
    	
    	buttonGasto.addClickListener(e -> {
    		Producto p = new Producto(Double.parseDouble(textFieldIngGas.getValue()));
    		balanceAux = balance.getValor() - p.getPrecio();
    		balance.setValor(balanceAux);
        	labelBalance.setValue("Balance: " + Double.toString(balance.getValor()));
    		textFieldIngGas.clear();
    	});
    	
    	
    	formLayout3.addComponents(
    			labelTransaccion,
    			textFieldIngGas,
    			buttonIngreso,
    			buttonGasto
    	);
    	
    
    	horizontalLayout.addComponents(grid, formLayout, formLayout2, formLayout3);
    	
    	
    	
    	setContent(horizontalLayout);
    	
    	
    }
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
