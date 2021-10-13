package es.ADD.AE2;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Controlador {

	private Vista vista;
	private Modelo modelo;
	private ActionListener actionListenerBuscar, actionListenerReemplazar;
	private String ficheroLectura, ficheroEscritura;
	
	//Constructor recibe instancia de la vista y del modelo.
	public Controlador(Vista vista, Modelo modelo){
		this.vista = vista;
		this.modelo = modelo;
		control();
	}
	
	//Método para añadir los listeners a los elementos que las generan (botones).
	public void control(){
		
		ficheroLectura = modelo.ficheroLectura();
		ficheroEscritura = modelo.ficheroEscritura();
		
		MostrarFichero(ficheroLectura, 1);
		
		actionListenerBuscar = new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				String palabraBuscar = vista.getTextFieldBuscar().getText();
				JOptionPane.showMessageDialog(null, modelo.Buscar(palabraBuscar));
			}
		};
		
		actionListenerReemplazar = new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				String palabraBuscar = vista.getTextFieldBuscar().getText();
				String palabraReemplazar = vista.getTextFieldReemplazar().getText();
				
				modelo.Reemplazar(palabraBuscar, palabraReemplazar);
				
				MostrarFichero(ficheroEscritura,2);
				
			}
		};
		
		vista.getBtnBuscar().addActionListener(actionListenerBuscar);
		vista.getBtnReemplazar().addActionListener(actionListenerReemplazar);
	}
	
		
	private void MostrarFichero(String fichero, int numeroTextArea) {
		ArrayList<String> arrayLineas = modelo.ContenidoFichero(fichero);
		
		for (String linea : arrayLineas) {
			if (numeroTextArea == 1) {
				vista.getTextAreaOriginal().append(linea+"\n");
			} else {
				vista.getTextAreaModificado().append(linea+"\n");
			}
		}
	}
		
}