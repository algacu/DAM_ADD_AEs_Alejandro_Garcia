package es.add.ae6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Biblioteca {
	

	// Método: mostrarBD
	// Descripción: recibe las session, instancia un objeto modelo de tipo libro y va recuper objetos 
	//	del mismo tipo de la BD. Los guarda en una lista y, a continuación, los encadena en un string 
	//	para mostrarlos posteriormente por pantalla.
	// Parámetros de entrada: session (creada a través de session factory en principal).
	// Parámetros de salida: string con los datos (libros) de la BD.
	public static String mostrarBD(MongoCollection<Document> coleccion) {

		String linea = "";

		try {

			MongoCursor<Document> cursor = coleccion.find().iterator();
			
			while(cursor.hasNext()) {
				JSONObject obj = new JSONObject(cursor.next().toJson());
				linea += obj.getString("Id") + " - " + obj.getString("Titol") + "\n";
			}

		} catch (Exception e) {
			linea = "ERROR en la conexión con la BD.";
		}

		return linea;
	}

	// Método: consultarLibro
	// Descripción: recupera un objeto de tipo Libro de la BD a partir de una id.
	// Parámetros de entrada: session (creada a través de session factory en principal) e id de tipo int.
	// Parámetros de salida: string con los datos del dato (libro) consultado.
	public static String consultarLibro(MongoCollection<Document> coleccion, int id) {

		String linea = "";

		Bson query = eq("Id", id);
		MongoCursor<Document> cursor = coleccion.find(query).iterator();
		while(cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			linea += obj.getString("Id") + " - " + obj.getString("Titol") + "\n";
		}

		return linea;
	}

	// Método: anyadirLibro
	// Descripción: crea un objeto de tipo libro y lo guarda en la BD con una nueva id.
	// Parámetros de entrada: session (creada a través de session factory en principal) y strings.
	// Parámetros de salida: string que confirma el añadido, mostrando los datos (libro) introducidos.
	public static String anyadirLibro(MongoCollection<Document> coleccion, String titulo, String autor, String anyoNac, String anyoPub,
			String editorial, String pags) {

		String linea = "";

		try {
			
			Document doc = new Document();
			doc.append("Titol", titulo);
			doc.append("Autor", autor);
			doc.append("Any_naixement", anyoNac);
			doc.append("Any_publicacio", anyoPub);
			doc.append("Editorial", editorial);
			doc.append("Nombre_pagines", pags);

			coleccion.insertOne(doc);
			
			linea += "Libro añadido correctamente.";

		} catch (Exception e) {
			linea = "\nERROR en la inserción.";
		}

		return linea;

	}

	// Método: modificarLibro
	// Descripción: recupera un objeto de tipo Libro de la BD a partir de una id y modifica sus parámetros.
	// Parámetros de entrada: session (creada a través de session factory en principal) y strings.
	// Parámetros de salida: String que confirma la modificación, mostrando los datos (libro) modificados.
	public static String modificarLibro(MongoCollection<Document> coleccion, int id, String titulo, String autor, String anyoNac,
			String anyoPub, String editorial, String pags) {

		String linea = "";

		try {
			
			coleccion.updateOne(eq("Id", id), new Document("$set", new Document("formato", "OGG")));
			
			coleccion.replaceOne(eq("Id", id), new Document("Titol", titulo),
			         new Document()
			         .append("Autor", autor)
			         .append("Anyo_naixement", anyoNac)
			         .append("Anyo_publicacio", anyoPub)
			         .append("Editorial", editorial)
			         .append("Nombre_pagines", pags));

			linea += "Libro modificado correctamente";

		} catch (Exception e) {
			linea = "\nERROR en la modificación de datos.";
		}

		return linea;
	}

	
	// Método: borrarLibro
		// Descripción: recupera un objeto de tipo Libro de la BD a partir de una id y lo borra.
		// Parámetros de entrada: session (creada a través de session factory en principal) e id de tipo int.
		// Parámetros de salida: string que confirma el borrado.
		public static String borrarLibro(MongoCollection<Document> coleccion, int id) {
			String linea = "";

			try {

				coleccion.deleteOne(eq("Id", id));

				linea += "\nLibro borrado correctamente. Id: " + id;

			} catch (Exception e) {
				linea = "\nERROR en el borrado.";
			}

			return linea;
		}
	
	
	
	
//	// Método: guardarBD
//	// Descripción: hace commit de la sesión actual con la BD remota y cierra la sesión.
//	// Parámetros de entrada: session (creada a través de session factory en principal).
//	// Parámetros de salida: string.
//	public static String guardarBD(Session session) {
//
//		String linea = "";
//
//		try {
//			session.getTransaction().commit();
//			session.close();
//
//		} catch (Exception e) {
//			linea = "\nERROR en el guardado de la BD.";
//		}
//
//		return linea;
//	}

}
