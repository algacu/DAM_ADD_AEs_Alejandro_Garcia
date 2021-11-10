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
	
	//M�todo: cargarBD
	//Descripci�n: establece conexi�n BD remota, lee un fichero .CSV local y lo carga a remoto.
		//Primero borra la base de datos en remoto (para hacer una carga limpia) y reseta el auto-increment de la primary-key (id) a 1.
	//Par�metros de entrada: conexi�n con la BD.
	//Par�metros de salida: String con los datos (libros) cargados en la BD.
	public static String cargarBD(Connection con) {
		
		String linea = "";
		
		try {
			 PreparedStatement psInsertar = con.prepareStatement("INSERT INTO libros (titulo, autor, anyo_nac, anyo_pub, editorial, pags) VALUES (?,?,?,?,?,?)");
			 PreparedStatement psBorrarTodo = con.prepareStatement("DELETE FROM libros");
			 PreparedStatement psResetId = con.prepareStatement("alter table libros AUTO_INCREMENT=1;");
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
	    			
	    			if (resultadoInsertar > 0) {
	    				linea += "\nLibro guardado en la base de datos (fila "+ contador +"): " + "\n" + nuevoLibro.toString() + "\n";
	    			} else {
	    				linea = "ERROR en la inserci�n.";
	    			}
	            }
	            contador++;
	            line = br.readLine();
	         }
	         br.close();
	      } catch (SQLException | IOException e) {
	    	  linea = "ERROR al cargar la base de datos.";
	      }
		return linea;
	}
	
	
	//M�todo: mostrarBD
		//Descripci�n: establece conexi�n BD remota y la muestra por pantalla.
		//Par�metros de entrada: conexi�n con la BD.
		//Par�metros de salida: String con los datos (libros) de la BD.
	public static String mostrarBD(Connection con) {
		
		String linea = "";
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM libros");
//			System.out.format("\n%3s%32s%30s%18s%18s%22s%18s%2s", "id", "T�tulo", "Autor", "A�o nacimiento", "A�o publicaci�n", "Editorial", "N� de p�ginas", "\n");
//			System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", "==", "========", "=====", "==============", "==============", "=========", "=============", "\n");
			while (rs.next()) {
//				System.out.format("%3s%32s%30s%18s%18s%22s%18s%2s", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), "\n");
				linea += "\nID: " + rs.getString(1) + " - T�tulo: " +  rs.getString(2) + " - Autor: " +  rs.getString(3) + " - A�o de nacimiento: " +  rs.getString(4) 
						+ " - A�o de publicaci�n: " + rs.getString(5) + " - Editorial: " + rs.getString(6) + " - N� de p�ginas: " + rs.getString(7) + "\n";
			}
			rs.close();
		} catch (SQLException e){
			linea = "ERROR en la conexi�n con la BD.";
		}
		
		return linea;
	}
	
	
	//M�todo: mostrarBD
		//Descripci�n: establece conexi�n BD remota y obtiene informaci�n a partir de una query con un par�metro (id) concreto.
		//Par�metros de entrada: conexi�n con la BD.
		//Par�metros de salida: String con los datos del dato (libro) consultado.
	public static String consultarLibro(Connection con, String id) {
		
		String linea = "";
		
		try {			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " + id);
						
			if (rs.next()) {
				linea += "ID: " + rs.getString(1) + " - T�tulo: " +  rs.getString(2) + " - Autor: " +  rs.getString(3) + " - A�o de nacimiento: " +  rs.getString(4) 
				+ " - A�o de publicaci�n: " + rs.getString(5) + " - Editorial: " + rs.getString(6) + " - N� de p�ginas: " + rs.getString(7) + "\n";
			} else {
				System.err.println("\nERROR en la consulta. �ID no existe?");
			}
			rs.close();
		} catch (SQLException e) {
			linea = "\nERROR en la consulta.";
		}
		
		return linea;
	}
	
	
	//M�todo: mostrarBD
		//Descripci�n: establece conexi�n BD remota y borra el dato (libro) seleccionado por par�metro (id).
		//Par�metros de entrada: conexi�n con la BD.
		//Par�metros de salida: String que confirma el borrado.
	public static String borrarLibro(Connection con, String id){
		String linea = "";
		try {

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
	
	
	//M�todo: anyadirLibro
		//Descripci�n: establece conexi�n BD remota y a�ade datos (libro) pasados por par�metro.
		//Par�metros de entrada: conexi�n con la BD y Strings.
		//Par�metros de salida: String que confirma el a�adido, mostrando los datos (libro) introducidos.
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
				linea = "\nPel�cula guardada en base de datos: " + nuevoLibro.toString();
			} else {
				linea = "\nERROR en la inserci�n.";
			}
			
		} catch (SQLException e) {
			linea = "\nERROR en la inserci�n.";
		}
		
		return linea;

	}
	
	
	//M�todo: modificarLibro
			//Descripci�n: establece conexi�n BD remota y modifica datos (libro) pasados por par�metro.
			//Par�metros de entrada: conexi�n con la BD y Strings.
			//Par�metros de salida: String que confirma el a�adido, mostrando los datos (libro) modificados.
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
				linea = "\nERROR en la inserci�n.";
			}
			
			psActualizar.close();
		} catch (SQLException e) {
			linea = "\nERROR en la modificaci�n de datos.";
		}
		return linea;
	}
	
	
	//M�todo: modificarLibro
		//Descripci�n: establece conexi�n BD remota y ejectua una consulta SQL (A�ADIDO: tambi�n puede ejecutar comandos SQL como DELETE, ALTER, etc...)
		//Par�metros de entrada: conexi�n con la BD y String.
		//Par�metros de salida: String con el resultado de la consulta o confirmaci�n del comando ejecutado.
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
			linea = "\nERROR en la ejecuci�n del comando SQL";
		}
		return linea;
	}
	
	
	//M�todo: nacidosAntes1950
		//Descripci�n: establece conexi�n BD remota y obtiene datos seleccionados por consulta query.
		//Par�metros de entrada: conexi�n con la BD.
		//Par�metros de salida: String con el resultado de la consulta.
	public static String nacidosAntes1950(Connection con) {
		
		String linea = "\nLibros de autores nacidos antes de 1950:";		
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT titulo, autor, anyo_pub FROM libros WHERE anyo_nac < 1950");
			
			while (rs.next()) {
				linea += "\nT�tulo: " + rs.getString(1) + " - Autor: " + rs.getString(2) + " - A�o publicaci�n: " + rs.getString(3);
			}
			
			rs.close();
		} catch (SQLException e){
			linea = "ERROR en la conexi�n con la BD.";
		}
		
		return linea;
	}
	
	
	//M�todo: editorialesSXXI
		//Descripci�n: establece conexi�n BD remota y obtiene datos seleccionados por consulta query.
		//Par�metros de entrada: conexi�n con la BD.
		//Par�metros de salida: String con el resultado de la consulta.
	public static String editorialesSXXI(Connection con) {
				
		String linea = "\nEditoriales que hayan publicado al menos 1 libro en el Siglo XXI:";
		ArrayList<String> editoriales = new ArrayList<String>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT editorial FROM libros WHERE anyo_pub > 2000");
			
			while (rs.next()) {
				editoriales.add(rs.getString(1));
			}
			
			for (String editorial : editoriales) {
				linea += "\n" + editorial;
			}
			
			rs.close();
			
		} catch (SQLException e){
			linea = "ERROR en la conexi�n con la BD.";
		}
		
		return linea;
	}

	
	//M�todo: conectar()
			//Descripci�n: conecta con BD remota.
			//Par�metros de entrada: no.
			//Par�metros de salida: conexi�n.
	public static Connection conectar() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca","root","");
		return con;
	}
	
}



