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

public class Biblioteca2 {

	public static void main(String[] args) {
		
		String seguir = "si";
		Scanner teclado1 = new Scanner(System.in);
		Scanner teclado2 = new Scanner(System.in);
		
		System.out.println("Bienvenido a la biblioteca de Arrakeen");

		while (seguir.equals("s") || seguir.equals("si")) {
			System.out.println("\n¿Qué deseas hacer?");
			System.out.println("\t1 - Cargar una nueva base de datos (.CSV)");
			System.out.println("\t2 - Ver la base de datos entera");
			System.out.println("\t3 - Consultar un libro de la base de datos");
			System.out.println("\t4 - Añadir un libro a la base de datos");
			System.out.println("\t5 - Modificar un libro de la base de datos");
			System.out.println("\t6 - Borrar un libro de la base de datos");
			System.out.println("\t7 - Consulta SQL personalizada");
			System.out.println("\t8 - Salir");
			System.out.print("\nIntroduce una opción (número): ");
			int numero = teclado1.nextInt();
			
			try {
				Connection conexion = conectar();
				System.out.println("\nConexión realizada con la base de datos.\n");
				ejecutar(numero, conexion);
			} catch (ClassNotFoundException | SQLException e) {
				System.err.println("ERROR al conectar con la base de datos.");
			}
			
			System.out.print("\n¿Deseas realizar otra operación (s/n)? ");
			seguir = teclado2.nextLine();
		}
		
		System.out.println("Gracias por consultar la biblioteca de Arrakeen. Leer es bueno. ¡Larga vida a Arrakis!");
	}
	
		
	public static void ejecutar(int numero, Connection conexion) {
		try {
			switch (numero) {
				case 1: 
					cargarBD(conexion);
					break;
				case 2:
					mostrarBD(conexion);
					break;
				case 3:
					consultarLibro(conexion);
					break;
				case 4:
					anyadirLibro(conexion);
					break;
				case 5:
					modificarLibro(conexion);
					break;
				case 6:
					borrarLibro(conexion);
					break;
				case 7:
					consultaSQL(conexion);
					break;
				case 8:
					System.out.println("Gracias por consultar la biblioteca de Arrakeen. Leer es bueno. ¡Larga vida a Arrakis!");
					System.exit(0);
			}
		} catch (Exception e) {
			System.err.print("ERROR al ejectuar la orden introducida.");
		}
	}
	
	
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
	         con.close();
	         nacidosAntes1950(libros);
		     editorialesSXXI(libros);
	      } catch (SQLException | IOException e) {
	    	  System.err.print("ERROR al cargar la base de datos.");
	      } 
	}
	
	
	public static void mostrarBD(Connection con) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM libros");
			System.out.format("\n%3s%32s%30s%18s%18s%22s%18s%2s", "id", "Título", "Autor", "Año nacimiento", "Año publicación", "Editorial", "Nº de páginas", "\n");
			System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", "==", "========", "=====", "==============", "==============", "=========", "=============", "\n");
			while (rs.next()) {
				System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), "\n");
			}
			System.out.print("\n");
			rs.close();
			con.close();
		} catch (SQLException e){
			System.err.println("ERROR en la conexión con la BD.");
		}
	}
	
	
	public static void consultarLibro(Connection con) {
		try {
			System.out.print("Introduce el ID del libro que deseas consultar: ");
			Scanner teclado3 = new Scanner(System.in);
			String idConsultar = teclado3.nextLine();
				
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " +idConsultar);
						
			if (rs.next()) {
				System.out.format("\n%3s%32s%30s%18s%18s%22s%18s%2s", "id", "Título", "Autor", "Año nacimiento", "Año publicación", "Editorial", "Nº de páginas", "\n");
				System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", "==", "========", "=====", "==============", "==============", "=========", "=============", "\n");
				System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), "\n");
			} else {
				System.err.println("Error en la consulta. ¿ID no existe?");
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("ERROR en la consulta.");
		}
	}
	
	
	public static void borrarLibro(Connection con){
		try {
			System.out.print("Introducir ID para borrar entrada: ");
			Scanner teclado3 = new Scanner(System.in);
			String idBorrar = teclado3.nextLine();
		
			PreparedStatement psBorrar = con.prepareStatement("DELETE FROM libros WHERE id = " + idBorrar);

			int resultadoBorrar = 0;
			resultadoBorrar = psBorrar.executeUpdate();
						
			if (resultadoBorrar > 0) {
				System.out.println("Libro borrado de la base de datos (fila " + idBorrar + ")");
			} else {
				System.err.println("Error en el borrado.");
			}
			psBorrar.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("ERROR en el borrado.");
		}
	}
	
	
	public static void anyadirLibro(Connection con) {
		try {
			PreparedStatement psInsertar = con.prepareStatement("INSERT INTO libros (titulo,autor,anyo_nac,anyo_pub,editorial,pags) VALUES (?,?,?,?,?,?)");
			Scanner teclado4 = new Scanner(System.in);
			
			System.out.print("\nTítulo: "); String titulo = teclado4.nextLine();
			System.out.print("Autor: "); String autor = teclado4.nextLine();
			System.out.print("Año de nacimiento: "); String anyoNacimiento = teclado4.nextLine();
			System.out.print("Año de publicación: "); String anyoPublicacion = teclado4.nextLine();
			System.out.print("Editorial: "); String editorial = teclado4.nextLine();
			System.out.print("Nº páginas: "); String paginas = teclado4.nextLine();
	
			psInsertar.setString(1, titulo);
			psInsertar.setString(2, autor);
			psInsertar.setString(3, anyoNacimiento);
			psInsertar.setString(4, anyoPublicacion);
			psInsertar.setString(5, editorial);
			psInsertar.setString(6, paginas);
				
			int resultadoInsertar = psInsertar.executeUpdate();
			Libro nuevoLibro = new Libro(titulo, autor, anyoNacimiento, anyoPublicacion, editorial, paginas);
				
			if (resultadoInsertar > 0) {
				System.out.println("Película guardada en base de datos (fila " + resultadoInsertar + "): " + nuevoLibro.toString());
			} else {
				System.err.println("Error en la inserción.");
			}
			
		con.close();
		} catch (SQLException e) {
			System.err.println("ERROR en la inserción.");
		}
	}
	
	
	public static void modificarLibro(Connection con){
			
		try {
			System.out.print("Introducir ID para modificar datos: ");
			Scanner teclado5 = new Scanner(System.in);
			String idModificar = teclado5.nextLine();
			
			System.out.print("\nTítulo: "); String titulo = teclado5.nextLine();
			System.out.print("Autor: "); String autor = teclado5.nextLine();
			System.out.print("Año de nacimiento: "); String anyoNacimiento = teclado5.nextLine();
			System.out.print("Año de publicación: "); String anyoPublicacion = teclado5.nextLine();
			System.out.print("Editorial: "); String editorial = teclado5.nextLine();
			System.out.print("Nº páginas: "); String paginas = teclado5.nextLine();
				
			PreparedStatement psActualizar = con.prepareStatement("UPDATE libros SET titulo = '" + titulo + "', autor = '" + autor
				+ "', anyo_nac = '" + anyoNacimiento + "', anyo_pub = '" + anyoPublicacion + "', editorial = '" + editorial
				+ "', pags = '" + paginas + "' WHERE id = " + idModificar);

			int resultadoActualizar = 0;
			resultadoActualizar = psActualizar.executeUpdate();
						
			if (resultadoActualizar > 0) {
				Libro nuevoLibro = new Libro(titulo, autor, anyoNacimiento, anyoPublicacion, editorial, paginas);
				System.out.println("Película guardada en base de datos (fila " + idModificar + "): " + nuevoLibro.toString());
			} else {
				System.err.println("Error en la inserción.");
			}
			
			psActualizar.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("ERROR en la modificación de datos.");
		}
	}
	
	
	public static void consultaSQL(Connection con){
	
		System.out.print("Introduce la consulta SQL: ");
		Scanner teclado6 = new Scanner(System.in);
		String sql = teclado6.nextLine();
		
		sql = sql.toUpperCase();
		
		Statement stmt;
		ResultSet rs;
		ResultSetMetaData rsmd;
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			
			if (sql.contains("SELECT")) {
				ArrayList<String> parametros = new ArrayList<>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					parametros.add(rsmd.getColumnName(i+1).toUpperCase());
				}
				int numParametros = parametros.size();
				int contador = 1;
				System.out.println("\n");
				
				while(rs.next()) {
					for (String parametro : parametros) {
						System.out.println(parametro + ": " + rs.getString(parametro));
						if (contador == numParametros) {System.out.println("--\n"); contador=0;}
						contador++;
					}
				}
			} else {
				stmt.executeUpdate(sql);
				System.out.println("Comando ejectuado correctamente.");
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			System.err.println("Error en la ejecución del comando SQL");
		}
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



