package es.add.ae4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controlador {

	private Vista vista;
	private ActionListener actionListenerCargarCSV, actionListenerMostrar, actionListenerConsultaLibro, actionListenerAnyadir, 
	actionListenerModificar, actionListenerBorrar, actionListenerConsultaSQL;
	
//	actionListenerMostrar_Consulta, actionListenerSubir_CSV, actionListenerMostrar_Consulta1,
//			actionListenerMostrar_Consulta2;
	
	String linea;

	// Metodo controlador
	// Descipcion: metodo llama a las funciones control y iniEventHandlers y muestra
	// por la pantalla superor un texto
	// Parametros de entrada: vista y modelo (pasados desde principal)
	// Parametros salida: no

	public Controlador(Vista vista, Biblioteca biblioteca, Connection con) {
		this.vista = vista;
		initEventHandlers();
		control(con);
		vista.gettextArea_Texto().append("Leer es bueno. ¿En qué te podemos ayudar?\n");
	}

	// Metodo control
	// Descipcion: Meotodo con los actionLisntener
	// Parametros entrada: no
	// Parametros salida: no

	public void control(Connection con) {

		actionListenerCargarCSV = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Biblioteca.cargarBD(con);
				vista.gettextArea_Texto().append("\nBase de datos cargada.\n");
				;
			}
		};
		
		vista.getbtnCargarCSV().addActionListener(actionListenerCargarCSV);
		
		
		actionListenerMostrar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				linea = Biblioteca.mostrarBD(con);
				vista.gettextArea_Texto().append("\nMostrando base de datos completa:");
				vista.gettextArea_Texto().append("\n" + linea);
				;
			}
		};
		
		vista.getbtnMostrar().addActionListener(actionListenerMostrar);

		
		actionListenerConsultaLibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String id = vista.gettextField_Consulta().getText();
				linea = Biblioteca.consultarLibro(con, id);
				vista.gettextArea_Texto().append("\nLibro consultado:");
				vista.gettextArea_Texto().append("\n" + linea);
				;
			}
		};
		
		vista.getbtnConsultar().addActionListener(actionListenerConsultaLibro);
		
		
		actionListenerAnyadir = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String titulo = JOptionPane.showInputDialog(jFrame, "Título: ");
		        String autor = JOptionPane.showInputDialog(jFrame, "Autor: ");
		        String fechaNac = JOptionPane.showInputDialog(jFrame, "Fecha de nacimiento: ");
		        String fechaPub = JOptionPane.showInputDialog(jFrame, "Fecha de publicación: ");
		        String editorial = JOptionPane.showInputDialog(jFrame, "Editorial: ");
		        String pags = JOptionPane.showInputDialog(jFrame, "Páginas: ");
				linea = Biblioteca.anyadirLibro(con, titulo, autor, fechaNac, fechaPub, editorial, pags);
				vista.gettextArea_Texto().append(linea);
				;
			}
		};
		
		vista.getbtnAnyadir().addActionListener(actionListenerAnyadir);
		
		
		actionListenerBorrar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String id = vista.gettextField_Consulta().getText();
				linea = Biblioteca.borrarLibro(con, id);
				vista.gettextArea_Texto().append(linea);
				;
			}
		};
		
		vista.getbtnBorrar().addActionListener(actionListenerBorrar);
		
		
		actionListenerModificar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String titulo = JOptionPane.showInputDialog(jFrame, "Título: ");
		        String autor = JOptionPane.showInputDialog(jFrame, "Autor: ");
		        String fechaNac = JOptionPane.showInputDialog(jFrame, "Fecha de nacimiento: ");
		        String fechaPub = JOptionPane.showInputDialog(jFrame, "Fecha de publicación: ");
		        String editorial = JOptionPane.showInputDialog(jFrame, "Editorial: ");
		        String pags = JOptionPane.showInputDialog(jFrame, "Páginas: ");
		        String id = vista.gettextField_Consulta().getText();
				linea = Biblioteca.modificarLibro(con, id, titulo, autor, fechaNac, fechaPub, editorial, pags);
				vista.gettextArea_Texto().append(linea);
				;
			}
		};
		
		vista.getbtnModificar().addActionListener(actionListenerModificar);
		
		
		actionListenerConsultaSQL = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String consulta = JOptionPane.showInputDialog(jFrame, "Consulta SQL: ");
				linea = Biblioteca.consultaSQL(con, consulta);
				vista.gettextArea_Texto().append(linea);
				;
			}
		};
		
		vista.getbtnSQL().addActionListener(actionListenerConsultaSQL);
		
		
//		actionListenerMostrar_Consulta2 = new ActionListener() {
//			public void actionPerformed(ActionEvent actionEvent) {
//				linea = Modelo.consulta2();
//				vista.gettextArea_Consultas().append(linea);
//				;
//			}
//		};
//		vista.getbtnMostrar_Consulta2().addActionListener(actionListenerMostrar_Consulta2);

		
	}

	// Metodo initEventHandlers
	// Descripcion: Controlador de eventos
	// Parametros entrada: no
	// Parametros salida: no

	public void initEventHandlers() {

		vista.getbtnCargarCSV().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		vista.getbtnMostrar().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		vista.getbtnConsultar().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		vista.getbtnAnyadir().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		vista.getbtnBorrar().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		vista.getbtnModificar().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		vista.getbtnSQL().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

//		vista.getbtnMostrar_Consulta1().addActionListener((ActionListener) new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//
//			}
//		});
//
//		vista.getbtnMostrar_Consulta2().addActionListener((ActionListener) new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//
//			}
//		});
	}
}