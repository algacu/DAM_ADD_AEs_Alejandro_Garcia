package es.add.ae4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Biblioteca {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca","root","");
		PreparedStatement psInsertar = con.prepareStatement("INSERT INTO libros (titulo, autor, anyo_nac, anyo_pub, editorial, pags) VALUES (?,?,?,?,?,?)");
	    
        ArrayList<Libro> libros = new ArrayList<Libro>();
		
		BufferedReader br = null;
		
	      try {
	         br = new BufferedReader(new FileReader("AE04_T1_4_JDBC_Datos.csv"));
	         String line = br.readLine();
	         int contador = 0;

	         while (line != null) {
	            String [] datosLibro = line.split(";");
	            
	            if (contador > 0) {
	            	
	            	for (int i = 0; i < datosLibro.length; i++ ) {
	            		if (datosLibro[i].equals("")) {
	            			datosLibro[i] = "N.C.";
	    				}
	            	}

	            	psInsertar.setString(1, datosLibro[0]);
	    			psInsertar.setString(2, datosLibro[1]);
	    			psInsertar.setString(3, datosLibro[2]);
	    			psInsertar.setString(4, datosLibro[3]);
	    			psInsertar.setString(5, datosLibro[4]);
	    			psInsertar.setString(6, datosLibro[5]);
	    			
	    			int resultadoInsertar = psInsertar.executeUpdate();
	    			
	    			Libro nuevoLibro = new Libro(datosLibro[0], datosLibro[1], datosLibro[2], datosLibro[3], datosLibro[4], datosLibro[5]);
	    			libros.add(nuevoLibro);
	    			
	    			if (resultadoInsertar > 0) {
	    				System.out.println("Libro guardado en la base de datos (fila "+ contador +"): " + nuevoLibro.toString());
	    			} else {
	    				System.err.println("Error en la inserción.");
	    			}
	            }
	            contador++;
	            line = br.readLine();
	         }
	         br.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      nacidosAntes1950(libros);
	      editorialesSXXI(libros);
	}
	
	
	public static void nacidosAntes1950(ArrayList<Libro> libros) {
		
		System.out.println("\nLibros de autores nacidos antes de 1950:");
		
		for (Libro libro : libros) {
			
			if (!libro.getAnyoNac().equals("N.C.")){
				int anyo = Integer.parseInt(libro.getAnyoNac());
				if (anyo < 1950) {
					System.out.println("Título: " + libro.getTitulo() + " - Autor: " + libro.getAutor() + " - Año publicación: " + libro.getAnyoPub());
				}
			}
		}
	}
	
	
	public static void editorialesSXXI(ArrayList<Libro> libros) {
		
		ArrayList<String> editoriales = new ArrayList<String>();
		
		System.out.println("\nEditoriales que hayan publicado al menos 1 libro en el Siglo XXI:");
		
		for (Libro libro : libros) {
			int anyoPub = Integer.parseInt(libro.getAnyoPub());
			
			if (anyoPub > 2000) {
				if (!editoriales.contains(libro.getEditorial())) {
					editoriales.add(libro.getEditorial());
				}
			}
		}
		
		for (String editorial : editoriales) {
			System.out.println(editorial);
		}
	}
	
	
	
}



