package es.ADD.AE2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Modelo {

	private String fichero_lectura;
	private String fichero_escritura;
	
	public Modelo() {
		fichero_lectura = "AE02_T1_2_Streams_Groucho.txt";
		fichero_escritura = "AE02_T1_2_Streams_Groucho_2.txt";
	}
	
	public ArrayList<String> ContenidoFichero(String f){
		ArrayList<String> contenidoFichero = new ArrayList<String>();
		File fichero = new File(f);
		
		try {
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while (linea != null) {
				contenidoFichero.add(linea);
				linea = br.readLine();
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return contenidoFichero;
	}
	
	public Integer Buscar(String palabraBuscar) {
		ArrayList<String> arrayLineas = ContenidoFichero(fichero_lectura);
		
		int contador = 0;
		
		String palabraBuscarPrimeraLetraMayus = palabraBuscar.substring(0, 1).toUpperCase() + palabraBuscar.substring(1);
		String palabraBuscarMayus = palabraBuscar.toUpperCase();
		String palabraBuscarMinus = palabraBuscar.toLowerCase();
		
		for (String linea : arrayLineas) {
			
			String[]palabras = linea.replaceAll("\\p{Punct}", "").split(" ");
			
			for (String palabra : palabras) {
				if(palabra.equals(palabraBuscarMinus) || palabra.equals(palabraBuscarMayus) || palabra.equals(palabraBuscarPrimeraLetraMayus)) contador++;
			}
		}
		
		return contador;
	}
	
	public void Reemplazar(String palabraBuscar, String palabraReemplazar) throws IOException {
		
		ArrayList<String> arrayLineas = ContenidoFichero(fichero_lectura);
		
		FileWriter fw = new FileWriter(fichero_escritura);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (String linea : arrayLineas) {
			
			String[]palabras = linea.split(" ");
			
			for (String palabra : palabras) {
				
				if(palabra.equals(palabraBuscar) || palabra.matches(palabraBuscar + "\\p{Punct}")) {
					palabra = palabraReemplazar;
				}
				
				bw.write(palabra + " ");
			}
			
			bw.write("\n");
		}
		
		bw.close();
		fw.close();
	}
	
	public String ficheroLectura() {
		return fichero_lectura;
	}
	
	public String ficheroEscritura() {
		return fichero_escritura;
	}

}
