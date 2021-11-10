package es.add.ae4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controlador {

	private Vista vista;
	private ActionListener actionListenerCargarCSV, actionListenerMostrar, actionListenerConsultaLibro, actionListenerAnyadir, 
	actionListenerModificar, actionListenerBorrar, actionListenerConsultaSQL, actionListenerAutores, actionListenerEditoriales;
	String linea;
	
	//Método: controlador
		//Descripción: carga la interfaz e instancia los métodos initEventHandlers() y control()
		//Parámetros de entrada: no.
		//Parámetros de salida: no.
	public Controlador(Vista vista, Biblioteca biblioteca, Connection con) {
		this.vista = vista;
		initEventHandlers();
		control(con);
		vista.gettextArea_Texto().append("Leer es bueno. ¿En qué te podemos ayudar?");
	}


	//Método: control.
		//Descripción: desarrolla actionListeners
		//Parámetros de entrada: no.
		//Parámetros de salida: no.
	public void control(Connection con) {

		actionListenerCargarCSV = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String confirmacion = JOptionPane.showInputDialog(jFrame, "¿Deseas cargar la base de datos"
		        		+ "a partir del fichero .csv?" + "\n" + "(Reempleazará a la base de datos existente) (s/n)");
		        confirmacion = confirmacion.toLowerCase();
		        String mensaje;
		        if (confirmacion.equals("s") || confirmacion.equals("si")) {
		        	linea = Biblioteca.cargarBD(con);
					vista.gettextArea_Texto().append("\n" + linea);
		        } else {
		        	mensaje = "\nCarga cancelada.";
		        	vista.gettextArea_Texto().append("\n" + mensaje);
		        }
			}
		};
		
		vista.getbtnCargarCSV().addActionListener(actionListenerCargarCSV);
		
		
		actionListenerMostrar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				linea = Biblioteca.mostrarBD(con);
				vista.gettextArea_Texto().append("\n" + "\nMostrando base de datos completa:");
				vista.gettextArea_Texto().append("\n" + linea);
			}
		};
		
		vista.getbtnMostrar().addActionListener(actionListenerMostrar);

		
		actionListenerConsultaLibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String id = JOptionPane.showInputDialog(jFrame, "Introduce la ID del libro que deseas consultar");
				
		        if (id != null) {
		        	linea = Biblioteca.consultarLibro(con, id);
					vista.gettextArea_Texto().append("\n" + "\nLibro consultado:");
					vista.gettextArea_Texto().append("\n" + linea);
		        } 
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
				vista.gettextArea_Texto().append("\n" + linea);
			}
		};
		
		vista.getbtnAnyadir().addActionListener(actionListenerAnyadir);
		
		
		actionListenerBorrar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String id = JOptionPane.showInputDialog(jFrame, "Introduce la ID del libro que deseas borrar");
		        if (id != null) {
		        	linea = Biblioteca.borrarLibro(con, id);
					vista.gettextArea_Texto().append("\n" + linea);
		        }
			}
		};
		
		vista.getbtnBorrar().addActionListener(actionListenerBorrar);
		
		
		actionListenerModificar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
				String id = JOptionPane.showInputDialog(jFrame, "Introduce la ID del libro que deseas modificar");
		        String titulo = JOptionPane.showInputDialog(jFrame, "Nuevo título: ");
		        String autor = JOptionPane.showInputDialog(jFrame, "Nuevo autor: ");
		        String fechaNac = JOptionPane.showInputDialog(jFrame, "Nuevo Año de nacimiento: ");
		        String fechaPub = JOptionPane.showInputDialog(jFrame, "Nuevo Año de publicación: ");
		        String editorial = JOptionPane.showInputDialog(jFrame, "Nueva Editorial: ");
		        String pags = JOptionPane.showInputDialog(jFrame, "Nuevo Nº de páginas: ");
				linea = Biblioteca.modificarLibro(con, id, titulo, autor, fechaNac, fechaPub, editorial, pags);
				vista.gettextArea_Texto().append("\n" + linea);
			}
		};
		
		vista.getbtnModificar().addActionListener(actionListenerModificar);
		
		
		actionListenerConsultaSQL = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String consulta = JOptionPane.showInputDialog(jFrame, "Consulta SQL: ");
				linea = Biblioteca.consultaSQL(con, consulta);
				vista.gettextArea_Texto().append("\n" + linea);
			}
		};
		
		vista.getbtnSQL().addActionListener(actionListenerConsultaSQL);
		
		
		actionListenerAutores = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				linea = Biblioteca.nacidosAntes1950(con);
				vista.gettextArea_Texto().append("\n" + linea);
			}
		};
		
		vista.getbtnAutores().addActionListener(actionListenerAutores);
		
		
		actionListenerEditoriales = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				linea = Biblioteca.editorialesSXXI(con);
				vista.gettextArea_Texto().append("\n" + linea);
			}
		};
		
		vista.getbtnEditoriales().addActionListener(actionListenerEditoriales);
		
	}

	
	//Método: initEventHandlers.
		//Descripción: Controlador de eventos.
		//Parámetros de entrada: no.
		//Parámetros de salida: no.
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
		
		vista.getbtnAutores().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		vista.getbtnEditoriales().addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}

}