package conversor_monedas;

import java.awt.Color;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.text.PlainDocument;

import conversor_monedas.ComponenteVisual.Cantidad;

import javax.print.attribute.AttributeSet;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

public class Ventana {

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					System.out.println(Backend.recuperarMonedas());
					Ventana window = new Ventana();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	//Centrar el divisor del splitPane al iniciar la app, al mostrar el splitPane
	//Centrar el divisor al redimensionar la ventana 
	
	//public class anonima extends ComponentAdapter{}
	private ComponentAdapter centrarDivisorDelSplitPane = new ComponentAdapter() {
		
		private void centrarDivisor(ComponentEvent e) {
			JSplitPane panel = (JSplitPane)e.getComponent();
			int localizacion = panel.getWidth() /2;
			panel.setDividerLocation(localizacion);
		}
		
		@Override
		public void componentShown(ComponentEvent e) {
			this.centrarDivisor(e);
		}
		
		@Override
		public void componentResized(ComponentEvent e) {
			this.centrarDivisor(e);
		}
	};
	
	private Color color_primario = new Color(51, 204, 153);
	private Color color_secundario = new Color (153, 255, 204);

	private JFrame frame = new JFrame();
	private JPanel contenedorPrincipal = new JPanel();
	private JComboBox<String> selectorConversor = new JComboBox<String>(); 
	private JSplitPane contenedorSistema = new JSplitPane();
	private JPanel panelIzquierdo = new JPanel();
	private JPanel panelDerecho = new JPanel();
	private JComboBox<String> listaIzquierda = new JComboBox<String>();
	private JComboBox<String> listaDerecha = new JComboBox<String>();
	private Cantidad entrada = new Cantidad(); //1 Entrada del texto: validación de JFormattedTextField a cantidad
	private JLabel salida = new JLabel("Salida");
	
	public Ventana() {
		initialize();
		colorearInterfaz();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Container contenedor;
		contenedor = frame.getContentPane();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor.setLayout(new SpringLayout());
		this.inicializarContenedorPrincipal();
		this.inicializarSelectorConversor();
		this.inicializarContenedorSistema();
		this.inicializarLista(listaIzquierda);
		this.inicializarLista(listaDerecha);
		this.inicializarPanelIzquierdoDelSistema();
		this.inicializarPanelDerechoDelSistema();
		
		
		//3
		panelIzquierdo.add(listaIzquierda);
		panelIzquierdo.add(entrada);
		

		panelDerecho.add(listaDerecha);
		panelDerecho.add(salida);
		
		contenedorSistema.setLeftComponent(panelIzquierdo);
		contenedorSistema.setRightComponent(panelDerecho);
		
		JButton boton = new JButton ("Convertir");
		boton.addActionListener(this::convertir); //Añadir acción al botón con escuchador
		contenedorPrincipal.add(boton);
		boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		
		salida.setHorizontalAlignment(JLabel.CENTER);
		entrada.setHorizontalAlignment(JLabel.CENTER);

		}
	private void inicializarContenedorPrincipal() {
		Container contenedor = frame.getContentPane();
		SpringLayout contenedorDiseño = (SpringLayout)contenedor.getLayout();
		
		
		contenedorDiseño.putConstraint(SpringLayout.WEST, contenedorPrincipal, 10, SpringLayout.WEST, contenedor);
		contenedorDiseño.putConstraint(SpringLayout.NORTH, contenedorPrincipal, 10, SpringLayout.NORTH, contenedor);
		contenedorDiseño.putConstraint(SpringLayout.EAST, contenedorPrincipal, -10, SpringLayout.EAST, contenedor);
		contenedorDiseño.putConstraint(SpringLayout.SOUTH, contenedorPrincipal, -10, SpringLayout.SOUTH, contenedor);
	
	contenedorPrincipal.setBackground(new Color(200,10,100));
	contenedorPrincipal.setLayout(new BoxLayout(contenedorPrincipal, BoxLayout.Y_AXIS));
	
	
	contenedor.add(contenedorPrincipal);
	}
	
	private void inicializarSelectorConversor() {
		selectorConversor.addItem("Conversor de Monedas");
		// selectorConversor.addItem("Conversor de Temperatura");
		contenedorPrincipal.add(selectorConversor);
	}
	
	private void inicializarContenedorSistema(){
		contenedorSistema.setAlignmentX(Component.CENTER_ALIGNMENT);
		contenedorSistema.setDividerSize(1);
		contenedorSistema.setEnabled(false);
		contenedorSistema.addComponentListener(centrarDivisorDelSplitPane);
		contenedorPrincipal.add(contenedorSistema);
		}
	
	private void organizarComponentesDelPanel(JPanel panel, JComboBox<?> lista, JComponent componente){ //2
		SpringLayout diseño = (SpringLayout)panel.getLayout();
		
		//Disposicion de la lista
		diseño.putConstraint(SpringLayout.WEST, lista, 10, SpringLayout.WEST, panel);
		diseño.putConstraint(SpringLayout.NORTH, lista, 10, SpringLayout.NORTH, panel);
		diseño.putConstraint(SpringLayout.EAST, lista, -10, SpringLayout.EAST, panel);
		
		//Disposicion del componente
		diseño.putConstraint(SpringLayout.WEST, componente, 10, SpringLayout.WEST, panel);
		diseño.putConstraint(SpringLayout.NORTH, componente, 10, SpringLayout.SOUTH, lista);
		diseño.putConstraint(SpringLayout.EAST, componente, -10, SpringLayout.EAST, panel);
		diseño.putConstraint(SpringLayout.SOUTH, componente, -10, SpringLayout.EAST, panel);
		
	}
	
	private void InicializarPanelDelSistema(JPanel panel, JComboBox<?> lista, JComponent componente) {
		panel.setBackground(new Color(200, 30, 0));
		panel.setLayout(new SpringLayout());
		this.organizarComponentesDelPanel(panel, lista, componente);
	}
	
	private void inicializarLista(JComboBox<String> lista) {
		String[] nombres = Backend.getNombres().toArray(String[]::new);
		lista.setModel(new DefaultComboBoxModel<String>(nombres));
		
	}
	
	
	private void inicializarPanelIzquierdoDelSistema() {
		InicializarPanelDelSistema(panelIzquierdo, listaIzquierda, entrada);
	}
	
	private void inicializarPanelDerechoDelSistema() {
		InicializarPanelDelSistema(panelDerecho, listaDerecha, salida);
	}
	
	private String recuperarNombreDeMoneda(JComboBox<String> lista) {
		return (String)lista.getSelectedItem();
	}
	
	private void convertir(ActionEvent e) { //Darle funcionalidad al botón
		String moneda1 = recuperarNombreDeMoneda(listaIzquierda);
		String moneda2 = recuperarNombreDeMoneda(listaDerecha);
		Double tasaDeCambio = Backend.recuperarTasaDeCambio(moneda1, moneda2);
		Double cantidad = entrada.obtenerValor(); //Me muestra el valor en la ventana
		Double conversion = cantidad * tasaDeCambio;
		salida.setText(conversion.toString());//De double a String
		//String.format("%2f", conversion) -> hace la funcion toString pero convierte el numero a 2 decimales
		
		//Eliminar las ",", porque dan NumberFormatException;
		
	}
	
	private void colorearInterfaz() {   //Crear metodo para modificar color de la interfaz
		frame.getContentPane().setBackground(color_primario); //Recuperar el contenedor principal
		contenedorPrincipal.setBackground(color_primario);
		panelIzquierdo.setBackground(color_secundario);
		panelDerecho.setBackground(color_secundario);
	}
	
}

	
	

