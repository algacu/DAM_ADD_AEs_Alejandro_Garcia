package es.add.ae5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class Biblioteca {

	// Método: mostrarBD
	// Descripción: establece conexión BD remota y la muestra por pantalla.
	// Parámetros de entrada: conexión con la BD.
	// Parámetros de salida: String con los datos (libros) de la BD.
	public static String mostrarBD(Session session) {

		String linea = "";

		try {

			List<Libro> libros = new ArrayList<>();
			Query libro = session.createQuery("from Libro");
			libros = libro.list();

			for (Libro elemento : libros) {
				linea += elemento.getId() + " - " + elemento.getTitulo() + "\n";
			}

			/*
			 * ArrayList<Libro> listaLibros = new ArrayList<Libro>(); listaLibros =
			 * session.createQuery(“FROM Libro”).list();
			 * 
			 * for (Libro libro:listaLibros) { linea += "\nID: " + libro.getId() +
			 * " - Título: " + libro.titulo + " - Autor: " + libro.autor +
			 * " - Año de nacimiento: " + libro.anyo_nac + " - Año de publicación: " +
			 * libro.anyo_pub + " - Editorial: " + libro.editorial + " - Nº de páginas: " +
			 * libro.pags + "\n"; }
			 */

		} catch (Exception e) {
			linea = "ERROR en la conexión con la BD.";
		}

		return linea;
	}

	// Método: consultarLibro
	// Descripción: establece conexión BD remota y obtiene información a partir de
	// una query con un parámetro (id) concreto.
	// Parámetros de entrada: conexión con la BD.
	// Parámetros de salida: String con los datos del dato (libro) consultado.
	public static String consultarLibro(Session session, int id) {

		String linea = "";

		// Recupero y leo un objeto a partir de su ID
		Libro libro = (Libro) session.get(Libro.class, id);

		if (libro == null) {
			System.err.println("\nERROR en la consulta. ¿ID no existe?");
		} else {
			linea += "ID: " + libro.getId() + " - Título: " + libro.getTitulo() + " - Autor: " + libro.getAutor()
					+ " - Año de nacimiento: " + libro.getAnyoNac() + " - Año de publicación: " + libro.getAnyoPub()
					+ " - Editorial: " + libro.getEditorial() + " - Nº de páginas: " + libro.getPags() + "\n";
		}

		return linea;
	}

	// Método: borrarLibro
	// Descripción: establece conexión BD remota y borra el dato (libro)
	// seleccionado por parámetro (id).
	// Parámetros de entrada: conexión con la BD.
	// Parámetros de salida: String que confirma el borrado.
	public static String borrarLibro(Session session, int id) {
		String linea = "";

		try {

			Libro libro = (Libro) session.get(Libro.class, id);

			if (libro != null) {
				libro.setId(id);
				session.delete(libro);
				linea += "\nLibro borrado correctamente. Fila: " + id;
			} else {
				linea += "\nNo existen registros con ID: " + id;
			}

		} catch (Exception e) {
			linea = "\nERROR en el borrado.";
		}

		return linea;
	}

	// Método: anyadirLibro
	// Descripción: establece conexión BD remota y añade datos (libro) pasados por
	// parámetro.
	// Parámetros de entrada: conexión con la BD y Strings.
	// Parámetros de salida: String que confirma el añadido, mostrando los datos
	// (libro) introducidos.
	public static String anyadirLibro(Session session, String titulo, String autor, String anyoNac, String anyoPub,
			String editorial, String pags) {

		String linea = "";

		try {

			// Crear nuevo objeto
			Libro libro = new Libro(titulo, autor, anyoNac, anyoPub, editorial, pags);
			Serializable id = session.save(libro);
			linea += "Libro añadido --> ID: " + libro.getId() + " - Título: " + libro.getTitulo() + " - Autor: "
					+ libro.getAutor() + " - Año de nacimiento: " + libro.getAnyoNac() + " - Año de publicación: "
					+ libro.getAnyoPub() + " - Editorial: " + libro.getEditorial() + " - Nº de páginas: "
					+ libro.getPags() + "\n";

		} catch (Exception e) {
			linea = "\nERROR en la inserción.";
		}

		return linea;

	}

	// Método: modificarLibro
	// Descripción: establece conexión BD remota y modifica datos (libro) pasados
	// por parámetro.
	// Parámetros de entrada: conexión con la BD y Strings.
	// Parámetros de salida: String que confirma el añadido, mostrando los datos
	// (libro) modificados.
	public static String modificarLibro(Session session, int id, String titulo, String autor, String anyoNac,
			String anyoPub, String editorial, String pags) {

		String linea = "";

		try {

			// Actualiza la información de un objeto dado su id
			Libro libro = (Libro) session.load(Libro.class, id);
			libro.setTitulo(titulo);
			libro.setAutor(autor);
			libro.setAnyoNac(anyoNac);
			libro.setAnyoPub(anyoPub);
			libro.setEditorial(editorial);
			libro.setPags(pags);
			session.update(libro);

			linea += "Libro modificado correctamente --> ID: " + libro.getId() + " - Título: " + libro.getTitulo()
					+ " - Autor: " + libro.getAutor() + " - Año de nacimiento: " + libro.getAnyoNac()
					+ " - Año de publicación: " + libro.getAnyoPub() + " - Editorial: " + libro.getEditorial()
					+ " - Nº de páginas: " + libro.getPags() + "\n";

		} catch (Exception e) {
			linea = "\nERROR en la modificación de datos.";
		}

		return linea;
	}

	// Método: modificarLibro
	// Descripción: establece conexión BD remota y modifica datos (libro) pasados
	// por parámetro.
	// Parámetros de entrada: conexión con la BD y Strings.
	// Parámetros de salida: String que confirma el añadido, mostrando los datos
	// (libro) modificados.
	public static String guardarBD(Session session) {

		String linea = "";

		try {
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			linea = "\nERROR en el guardado de la BD.";
		}

		return linea;
	}

}
