package com.TAP.vaadin_gestor;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
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
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
       
    	Grid<Producto> grid = new Grid<Producto>();
    	
    	HorizontalLayout horizontalLayout = new HorizontalLayout();
    	
    	/* VENTANA DETALLE */
    	
    	Window subWindow = new Window("Detalles del producto");
        VerticalLayout subContent1 = new VerticalLayout();
        VerticalLayout subContent2 = new VerticalLayout();
        VerticalLayout subContent3 = new VerticalLayout();
        HorizontalLayout horizontalLayoutWindow = new HorizontalLayout();
        
        Label labelNombre = new Label();
        Label labelCantidad = new Label();
        Label labelPrecio = new Label();
        TextField textFieldNombreVentana = new TextField("Nombre");
    	TextField textFieldCantidadVentana = new TextField("Cantidad");
    	TextField textFieldPrecioVentana = new TextField("Precio");
        Button buttonDelete = new Button("Eliminar producto");
        Button buttonModifyNombre = new Button("Modificar nombre");
        Button buttonModifyCantidad = new Button("Modificar cantidad");
        Button buttonModifyPrecio = new Button("Modificar precio");
        
        buttonDelete.addClickListener(e -> {
        	Inventario.getInstance().deleteProducto(productoSeleccionado);
        	grid.setItems(Inventario.getInstance().getProductos());
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
        	Producto p = new Producto(textFieldCantidadVentana.getValue());
    		productoSeleccionado.setCantidad(p.getNombre());
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldCantidadVentana.clear();
    		removeWindow(subWindow);
        });
        
        buttonModifyPrecio.addClickListener(e -> {
        	Producto p = new Producto(textFieldPrecioVentana.getValue());
    		productoSeleccionado.setPrecio(p.getNombre());
    		grid.setItems(Inventario.getInstance().getProductos());
    		textFieldPrecioVentana.clear();
    		removeWindow(subWindow);
        });
        
      
        subContent1.addComponents(new Label("Nombre:"), labelNombre, new Label("Cantidad:"),
        		labelCantidad, new Label("Precio:"), labelPrecio, buttonDelete);
        
        subContent2.addComponents(textFieldNombreVentana, textFieldCantidadVentana, textFieldPrecioVentana);
        
        subContent3.addComponents(buttonModifyNombre, new Label(), buttonModifyCantidad,
        		new Label(), buttonModifyPrecio);
        
        horizontalLayoutWindow.addComponents(subContent1, subContent2, subContent3);
        
        
        
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
        	labelCantidad.setValue(productoSeleccionado.getCantidad());
        	labelPrecio.setValue(productoSeleccionado.getPrecio());
        	
        	
        	removeWindow(subWindow);
        	addWindow(subWindow);
        	
    	});
    	
    	
    	/* FORM */
    	
    	
    	FormLayout formLayout = new FormLayout();
    	
    	TextField textFieldNombre = new TextField("Nombre");
    	TextField textFieldCantidad = new TextField("Cantidad");
    	TextField textFieldPrecio = new TextField("Precio");
    	Button buttonAdd = new Button("Añadir nuevo producto");
    			
    	buttonAdd.addClickListener(e -> {
    		
    		Producto p = new Producto(
    				textFieldNombre.getValue(),
    				textFieldCantidad.getValue(),
    				textFieldPrecio.getValue()
    				);
    		
    		Inventario.getInstance().addProducto(p);
    		
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
    	
    
    	horizontalLayout.addComponents(grid, formLayout);
    	
    	
    	
    	setContent(horizontalLayout);
    	
    	
    }
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
