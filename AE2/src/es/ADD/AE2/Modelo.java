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
	
	
	// Método: ContenidoFichero
	// Descripción: método que lee el fichero introducido y lo vuelca, línea a línea, en un ArrayList de tipo String.
	// Parámetros de entrada: fichero
	// Parámetros de salida: ArrayList de tipo String con el contenido del fichero de lectura.
	public ArrayList<String> ContenidoFichero(String f){
		
		ArrayList<String> contenidoFichero = new ArrayList<String>();
		
		try {
			File fichero = new File(f);
			FileReader fr = new FileReader(fichero);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while (linea != null) {
				contenidoFichero.add(linea);
				linea = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(new JFrame(), ioe.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		return contenidoFichero;
	}
	
	
	// Método: Buscar
	// Descripción: método que busca y cuenta el número de veces que aparece una palabra indicada en el texto.
	// Parámetros de entrada: palabra que se quiere buscar (String).
	// Parámetros de salida: número de veces que aparece la palabra en el texto (Integer).
	public Integer Buscar(String palabraBuscar) {
		
		int contador = 0;
		
		//Recorremos el array de líneas resultante del paso anterior, limpiándolas de signos de puntuación y generando, a su vez, un array con las palabras de cada línea.
		for (String linea : ContenidoFichero(fichero_lectura)) {
			
			String[]palabras = linea.replaceAll("\\p{Punct}", "").split(" ");
			
			//Recorremos el array de palabras, comprobando que cada una coincida con la palabra buscada (ignorando mayúsculas y minúsculas). Si coinciden, sumamos 1 al contador.
			for (String palabra : palabras) {
				if(palabra.equalsIgnoreCase(palabraBuscar)) contador++;
			}
		}
		return contador;
	}
	
	
	// Método: Reemplazar
	// Descripción: método que busca una palabra en el texto y la(s) reemplaza por otra. Funciona de manera
	//	similar a la función anterior, cuando encuentra la palabra deseada, la modifica y la escribe en el fichero de escritura.
	// Parámetros de entrada: palabras a buscar y reemplazar (String).
	// Parámetros de salida: .
	public void Reemplazar(String palabraBuscar, String palabraReemplazar) {
				
		try {
			FileWriter fw = new FileWriter(fichero_escritura);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String linea : ContenidoFichero(fichero_lectura)) {
				
				String[]palabras = linea.split(" ");
				
				for (String palabra : palabras) {
					
					if(palabra.equalsIgnoreCase(palabraBuscar)) {
						palabra = palabraReemplazar;
					} else if (palabra.matches("(?i).*" + palabraBuscar + "\\p{Punct}")) {
						palabra = palabraReemplazar + palabra.substring(palabra.length() - 1);
					}
					
					bw.write(palabra + " ");
				}
				
				bw.write("\n");
			}
			
			bw.close();
			fw.close();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(new JFrame(), ioe.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//Funciones que devuelven el nombre de los ficheros de lectura y escritura.
	public String ficheroLectura() {
		return fichero_lectura;
	}
	
	public String ficheroEscritura() {
		return fichero_escritura;
	}

}
