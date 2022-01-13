package es.add.ae6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class Controlador {

	private Vista vista;
	private ActionListener actionListenerMostrar, actionListenerConsultaLibro, actionListenerAnyadir, 
	actionListenerModificar, actionListenerBorrar;
	String linea;
	
	//Método: controlador
		//Descripción: carga la interfaz e instancia los métodos initEventHandlers() y control()
		//Parámetros de entrada: no.
		//Parámetros de salida: no.
	public Controlador(Vista vista, Biblioteca biblioteca, MongoCollection<Document> coleccion) {
		this.vista = vista;
		initEventHandlers();
		control(coleccion);
		vista.gettextArea_Texto().append("Leer es bueno. ¿En qué te podemos ayudar?");
	}


	//Método: control.
		//Descripción: desarrolla actionListeners
		//Parámetros de entrada: no.
		//Parámetros de salida: no.
	public void control(MongoCollection<Document> coleccion) {
		
		
		actionListenerMostrar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				linea = Biblioteca.mostrarBD(coleccion);
				vista.gettextArea_Texto().append("\n" + "\nMostrando base de datos completa:");
				vista.gettextArea_Texto().append(linea);
			}
		};
		
		vista.getbtnMostrar().addActionListener(actionListenerMostrar);

		
		actionListenerConsultaLibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String id = JOptionPane.showInputDialog(jFrame, "Introduce la ID del libro que deseas consultar");
		        
		        if (id != null) {
		        	linea = Biblioteca.consultarLibro(coleccion, id);
					vista.gettextArea_Texto().append("\n" + "\nLibro consultado:");
					vista.gettextArea_Texto().append(linea);
		        } 
			}
		};
		
		vista.getbtnConsultar().addActionListener(actionListenerConsultaLibro);
		
		
		actionListenerAnyadir = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String titulo = JOptionPane.showInputDialog(jFrame, "Título: ");
		        String autor = JOptionPane.showInputDialog(jFrame, "Autor: ");
		        String anyoNac = JOptionPane.showInputDialog(jFrame, "Fecha de nacimiento: ");
		        String anyoPub = JOptionPane.showInputDialog(jFrame, "Fecha de publicación: ");
		        String editorial = JOptionPane.showInputDialog(jFrame, "Editorial: ");
		        String pags = JOptionPane.showInputDialog(jFrame, "Páginas: ");

		        if (titulo == null || autor == null || anyoNac == null || anyoPub == null || editorial == null || pags == null) {
					vista.gettextArea_Texto().append("\n" + "\nOperación cancelada o campos vacíos. Si no quieres rellenar algún campo, introduce '-' en vez de dejarlo vacío.");
		        }	 
				else if (titulo.isBlank() || autor.isBlank() || anyoNac.isBlank() || anyoPub.isBlank() || editorial.isBlank() || pags.isBlank()) {
					vista.gettextArea_Texto().append("\n" + "\nOperación cancelada o campos vacíos. Si no quieres rellenar algún campo, introduce '-' en vez de dejarlo vacío.");
				} else { 
					linea = Biblioteca.anyadirLibro(coleccion, titulo, autor, anyoNac, anyoPub, editorial, pags);
					vista.gettextArea_Texto().append("\n" + "\nLibro añadido:");
					vista.gettextArea_Texto().append(linea);
		        }
				
			}
		};
		
		vista.getbtnAnyadir().addActionListener(actionListenerAnyadir);
		
		
		actionListenerBorrar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame jFrame = new JFrame();
		        String id = JOptionPane.showInputDialog(jFrame, "Introduce la ID del libro que deseas borrar");
		        
		        if (id == null) {
					vista.gettextArea_Texto().append("\n" + "\nOperación cancelada o campo vacío. Introduce la ID del libro que deseas borrar.");
		        }	 
				else if (id.isBlank()) {
					vista.gettextArea_Texto().append("\n" + "\nOperación cancelada o campo vacío. Introduce la ID del libro que deseas borrar.");
				} else { 
					linea = Biblioteca.borrarLibro(coleccion, id);
		        	vista.gettextArea_Texto().append("\n" + "\nLibro borrado:");
					vista.gettextArea_Texto().append(linea);
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
		        String anyoNac = JOptionPane.showInputDialog(jFrame, "Nuevo Año de nacimiento: ");
		        String anyoPub = JOptionPane.showInputDialog(jFrame, "Nuevo Año de publicación: ");
		        String editorial = JOptionPane.showInputDialog(jFrame, "Nueva Editorial: ");
		        String pags = JOptionPane.showInputDialog(jFrame, "Nuevo Nº de páginas: ");
		        
		        if (id == null || titulo == null || autor == null || anyoNac == null || anyoPub == null || editorial == null || pags == null) {
					vista.gettextArea_Texto().append("\n" + "\nOperación cancelada o campos vacíos. Si no quieres rellenar algún campo, introduce '-' en vez de dejarlo vacío.");
		        }	 
				else if (id.isBlank() || titulo.isBlank() || autor.isBlank() || anyoNac.isBlank() || anyoPub.isBlank() || editorial.isBlank() || pags.isBlank()) {
					vista.gettextArea_Texto().append("\n" + "\nOperación cancelada o campos vacíos. Si no quieres rellenar algún campo, introduce '-' en vez de dejarlo vacío.");
				} else { 
		        	linea = Biblioteca.modificarLibro(coleccion, id, titulo, autor, anyoNac, anyoPub, editorial, pags);
					vista.gettextArea_Texto().append("\n" + "\nLibro modificado:");
					vista.gettextArea_Texto().append(linea);
		        }
		        
			}
		};
		
		vista.getbtnModificar().addActionListener(actionListenerModificar);
	}

	
	//Método: initEventHandlers.
		//Descripción: Controlador de eventos.
		//Parámetros de entrada: no.
		//Parámetros de salida: no.
	public void initEventHandlers() {

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
		
	}

}