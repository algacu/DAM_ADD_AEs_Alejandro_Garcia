package es.add.ae4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {

//	public static void main(String[] args) {
//		
//		String seguir = "si";
//		Scanner teclado1 = new Scanner(System.in);
//		Scanner teclado2 = new Scanner(System.in);
//		
//		System.out.println("Bienvenido a la biblioteca de Arrakeen");
//
//		while (seguir.equals("s") || seguir.equals("si")) {
//			System.out.println("\n¿Qué deseas hacer?");
//			System.out.println("\t1 - Cargar una nueva base de datos (.CSV)");
//			System.out.println("\t2 - Ver la base de datos entera");
//			System.out.println("\t3 - Consultar un libro de la base de datos");
//			System.out.println("\t4 - Añadir un libro a la base de datos");
//			System.out.println("\t5 - Modificar un libro de la base de datos");
//			System.out.println("\t6 - Borrar un libro de la base de datos");
//			System.out.println("\t7 - Consulta SQL personalizada");
//			System.out.println("\t8 - Salir");
//			System.out.print("\nIntroduce una opción (número): ");
//			int numero = teclado1.nextInt();
//			String id = "hola";
//			
//			try {
//				Connection conexion = conectar();
//				System.out.println("\nConexión realizada con la base de datos.\n");
//				ejecutar(numero, conexion, id);
//			} catch (ClassNotFoundException | SQLException e) {
//				System.err.println("ERROR al conectar con la base de datos.");
//			}
//			
//			System.out.print("\n¿Deseas realizar otra operación (s/n)? ");
//			seguir = teclado2.nextLine();
//		}
//		
//		System.out.println("Gracias por consultar la biblioteca de Arrakeen. Leer es bueno. ¡Larga vida a Arrakis!");
//	}
	
		
//	public static void ejecutar(int numero, Connection conexion, String id) {
//		try {
//			switch (numero) {
//				case 1: 
//					cargarBD(conexion);
//					break;
//				case 2:
//					mostrarBD(conexion);
//					break;
//				case 3:
//					consultarLibro(conexion, id);
//					break;
//				case 4:
//					anyadirLibro(conexion);
//					break;
//				case 5:
//					modificarLibro(conexion);
//					break;
//				case 6:
//					borrarLibro(conexion);
//					break;
//				case 7:
//					consultaSQL(conexion);
//					break;
//				case 8:
//					System.out.println("Gracias por consultar la biblioteca de Arrakeen. Leer es bueno. ¡Larga vida a Arrakis!");
//					System.exit(0);
//			}
//		} catch (Exception e) {
//			System.err.print("ERROR al ejectuar la orden introducida.");
//		}
//	}
	
	
	public static void cargarBD(Connection con) {
		 try {
			 
			 PreparedStatement psInsertar = con.prepareStatement("INSERT INTO libros (titulo, autor, anyo_nac, anyo_pub, editorial, pags) VALUES (?,?,?,?,?,?)");
			 PreparedStatement psBorrarTodo = con.prepareStatement("DELETE FROM libros");
			 PreparedStatement psResetId = con.prepareStatement("alter table libros AUTO_INCREMENT=1;");
			    
		     ArrayList<Libro> libros = new ArrayList<Libro>();
		     BufferedReader br = null;
				
		     psBorrarTodo.executeUpdate();
		     psResetId.executeUpdate();
				
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
	    				System.err.println("ERROR en la inserción.");
	    			}
	            }
	            contador++;
	            line = br.readLine();
	         }
	         br.close();
	         nacidosAntes1950(libros);
		     editorialesSXXI(libros);
	      } catch (SQLException | IOException e) {
	    	  System.err.print("ERROR al cargar la base de datos.");
	      } 
	}
	
	
	public static String mostrarBD(Connection con) {
		
		String linea = "";
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM libros");
//			System.out.format("\n%3s%32s%30s%18s%18s%22s%18s%2s", "id", "Título", "Autor", "Año nacimiento", "Año publicación", "Editorial", "Nº de páginas", "\n");
//			System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", "==", "========", "=====", "==============", "==============", "=========", "=============", "\n");
			while (rs.next()) {
//				System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), "\n");
				linea += "\nID: " + rs.getString(1) + " - Título: " +  rs.getString(2) + " - Autor: " +  rs.getString(3) + " - Año de nacimiento: " +  rs.getString(4) 
						+ " - Año de publicación: " + rs.getString(5) + " - Editorial: " + rs.getString(6) + " - Nº de páginas: " + rs.getString(7) + "\n";
			}
			rs.close();
		} catch (SQLException e){
			linea = "ERROR en la conexión con la BD.";
		}
		
		return linea;
	}
	
	
	public static String consultarLibro(Connection con, String id) {
		
		String linea = "";
		
		try {			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " + id);
						
			if (rs.next()) {
				linea += "ID: " + rs.getString(1) + " - Título: " +  rs.getString(2) + " - Autor: " +  rs.getString(3) + " - Año de nacimiento: " +  rs.getString(4) 
				+ " - Año de publicación: " + rs.getString(5) + " - Editorial: " + rs.getString(6) + " - Nº de páginas: " + rs.getString(7) + "\n";
			} else {
				System.err.println("\nERROR en la consulta. ¿ID no existe?");
			}
			rs.close();
		} catch (SQLException e) {
			linea = "\nERROR en la consulta.";
		}
		
		return linea;
	}
	
	
	public static String borrarLibro(Connection con, String id){
		String linea = "";
		try {
			System.out.print("Introducir ID para borrar entrada: ");

			PreparedStatement psBorrar = con.prepareStatement("DELETE FROM libros WHERE id = " + id);

			int resultadoBorrar = 0;
			resultadoBorrar = psBorrar.executeUpdate();
						
			if (resultadoBorrar > 0) {
				linea = "\nLibro borrado de la base de datos (fila " + id + ")";
			} else {
				linea = "\nERROR en el borrado.";
			}
			psBorrar.close();
		} catch (SQLException e) {
			linea = "\nERROR en el borrado.";
		}
		return linea;
	}
	
	
	public static String anyadirLibro(Connection con, String titulo, String autor, String anyoNac, String anyoPub, String editorial, String pags) {
		
		String linea = "";
		
		try {
			PreparedStatement psInsertar = con.prepareStatement("INSERT INTO libros (titulo,autor,anyo_nac,anyo_pub,editorial,pags) VALUES (?,?,?,?,?,?)");
	
			psInsertar.setString(1, titulo);
			psInsertar.setString(2, autor);
			psInsertar.setString(3, anyoNac);
			psInsertar.setString(4, anyoPub);
			psInsertar.setString(5, editorial);
			psInsertar.setString(6, pags);
				
			int resultadoInsertar = psInsertar.executeUpdate();
			Libro nuevoLibro = new Libro(titulo, autor, anyoNac, anyoPub, editorial, pags);
			
			if (resultadoInsertar > 0) {
				linea = "\nPelícula guardada en base de datos: " + nuevoLibro.toString();
			} else {
				linea = "\nERROR en la inserción.";
			}
			
		} catch (SQLException e) {
			linea = "\nERROR en la inserción.";
		}
		
		return linea;

	}
	
	
	public static String modificarLibro(Connection con, String id, String titulo, String autor, String anyoNac, String anyoPub, String editorial, String pags){
		String linea;	
		try {
			PreparedStatement psActualizar = con.prepareStatement("UPDATE libros SET titulo = '" + titulo + "', autor = '" + autor
				+ "', anyo_nac = '" + anyoNac + "', anyo_pub = '" + anyoPub+ "', editorial = '" + editorial
				+ "', pags = '" + pags + "' WHERE id = " + id);

			int resultadoActualizar = 0;
			resultadoActualizar = psActualizar.executeUpdate();
						
			if (resultadoActualizar > 0) {
				Libro nuevoLibro = new Libro(titulo, autor, anyoNac, anyoPub, editorial, pags);
				linea = "\nLibro modificado en base de datos (fila " + id + "): " + nuevoLibro.toString();
			} else {
				linea = "\nERROR en la inserción.";
			}
			
			psActualizar.close();
		} catch (SQLException e) {
			linea = "\nERROR en la modificación de datos.";
		}
		return linea;
	}
	
	
	public static String consultaSQL(Connection con, String consulta){
	
		String sql = consulta.toUpperCase();
		
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData rsmd;
		
		String linea = "";
		
		try {
			stmt = con.createStatement();
			
			if (sql.contains("SELECT")) {
				rs = stmt.executeQuery(sql);
				rsmd = rs.getMetaData();
				ArrayList<String> parametros = new ArrayList<>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					parametros.add(rsmd.getColumnName(i+1).toUpperCase());
				}
				int numParametros = parametros.size();
				int contador = 1;
				linea += "\n";
				
				while(rs.next()) {
					for (String parametro : parametros) {
						linea += parametro + ": " + rs.getString(parametro) + " -- ";
						if (contador == numParametros) {linea += "\n"; contador=0;}
						contador++;
					}
				}
			} else {
				stmt.executeUpdate(sql);
				linea = "\nComando ejecutado correctamente.";
			}
			stmt.close();
		} catch (SQLException e) {
			linea = "\nERROR en la ejecución del comando SQL";
		}
		return linea;
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


	public static Connection conectar() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca","root","");
		return con;
	}
	
	
}



